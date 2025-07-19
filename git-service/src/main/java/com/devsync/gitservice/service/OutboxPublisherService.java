package com.devsync.gitservice.service;

public interface OutboxPublisherService {
    void publishOrderCreatedEvent();
}
