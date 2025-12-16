package com.linkedin.notification_service.consumer;

import com.linkedin.notification_service.event.AcceptConnectionRequest;
import com.linkedin.notification_service.event.SendConnectionRequest;
import com.linkedin.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "send-connection-request")
    public void handleSendConnectionRequest(SendConnectionRequest evt) {
        log.info("[Notification] handleSendConnectionRequest: {}", evt);
        String message = "You have receiver a connection request from user with id: " + evt.getSenderId();
        notificationService.send(evt.getReceiverId(), message);
    }

    @KafkaListener(topics = "accept-connection-request")
    public void handleAcceptConnectionRequest(AcceptConnectionRequest evt) {
        log.info("[Notification] handleAcceptConnectionRequest: {}", evt);
        String message = "Your connection request has been accepted by user with id: " + evt.getReceiverId();
        notificationService.send(evt.getSenderId(), message);
    }

}
