package com.linkedin.connection_service.service;

import com.linkedin.connection_service.config.KafkaTopics;
import com.linkedin.connection_service.entity.Person;
import com.linkedin.connection_service.event.AcceptConnectionRequest;
import com.linkedin.connection_service.event.SendConnectionRequest;
import com.linkedin.connection_service.exception.BadRequestException;
import com.linkedin.connection_service.exception.ConflictException;
import com.linkedin.connection_service.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.linkedin.connection_service.repository.PersonRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConnectionService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, Object> kafka;

    public List<Person> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections for user with id: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }

    @Transactional
    public Boolean sendConnectionRequest(Long senderId, Long receiverId) {
        log.info("Trying to send connection request, sender: {}, receiver: {}", senderId, receiverId);

        if(senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same.");
        }

        boolean alreadySent = personRepository.connectionRequestExists(senderId, receiverId);
        if (alreadySent) {
            throw new ConflictException("Connection request already sent.");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if(alreadyConnected) {
            throw new ConflictException("Already connected.");
        }

        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Successfully sent the connection request.");

        SendConnectionRequest evt = SendConnectionRequest.of(senderId, receiverId);
        kafka.send(KafkaTopics.SEND_CONNECTION_REQUEST, evt);
        return true;
    }

    @Transactional
    public Boolean acceptConnectionRequest(Long senderId, Long receiverId) {
        boolean requestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!requestExists) {
            throw new NotFoundException("No connection request exists to accept.");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted connection request, sender: {}, receiver: {}", senderId, receiverId);

        AcceptConnectionRequest evt = AcceptConnectionRequest.of(senderId, receiverId);
        kafka.send(KafkaTopics.ACCEPT_CONNECTION_REQUEST, evt);
        return true;
    }

    @Transactional
    public Boolean rejectConnectionRequest(Long senderId, Long receiverId) {
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!connectionRequestExists) {
            throw new NotFoundException("No connection request exists.");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }

}