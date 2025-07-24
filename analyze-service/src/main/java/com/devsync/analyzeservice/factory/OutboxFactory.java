package com.devsync.analyzeservice.factory;

import com.devsync.analyzeservice.entity.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OutboxFactory {

    private final ObjectMapper objectMapper;

    public OutboxFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> Outbox create(T model, Class<?> aggregateType, Class<?> eventType, String aggregateId) {
        try {
            String payload = objectMapper.writeValueAsString(model);
            Outbox outbox = new Outbox();
            outbox.setPayload(payload);
            outbox.setPublished(false);
            outbox.setAggregateType(aggregateType.getTypeName());
            outbox.setType(eventType.getTypeName());
            outbox.setAggregateId(aggregateId);
            return outbox;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox JSON serialization failed", e);
        }
    }
}

