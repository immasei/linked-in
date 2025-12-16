package com.linkedin.post_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.post_service.entity.Outbox;
import com.linkedin.post_service.event.Event;
import com.linkedin.post_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void save(Event evt) {
        Outbox e = Outbox.builder()
                .aggregateType(evt.getAggregateType())
                .aggregateId(evt.getAggregateId())
                .eventType(toKebabCase(evt.getClass().getSimpleName()))
                .payload(objectMapper.valueToTree(evt))
                .build();

        outboxRepository.save(e);
    }

    private static String toKebabCase(String input) {
        return input
                .replaceAll("([a-z0-9])([A-Z])", "$1-$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2")
                .toLowerCase();
    }

}
