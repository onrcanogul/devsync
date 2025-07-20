package com.devsync.analyzeservice.service.job;

import com.devsync.analyzeservice.service.impl.OutboxPublisherServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OutboxScheduler {
    private final OutboxPublisherServiceImpl outboxPublisherService;

    public OutboxScheduler(OutboxPublisherServiceImpl outboxPublisherService) {
        this.outboxPublisherService = outboxPublisherService;
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvent() { outboxPublisherService.publishEvent(); }
}
