package com.devsync.gitservice.service.job;

import com.devsync.gitservice.service.impl.OutboxPublisherServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OutboxScheduler {
    private final OutboxPublisherServiceImpl outboxPublisherServiceImpl;

    public OutboxScheduler(OutboxPublisherServiceImpl outboxPublisherServiceImpl) {
        this.outboxPublisherServiceImpl = outboxPublisherServiceImpl;
    }

    @Scheduled(fixedRate = 5000)
    public void publishPullRequestCreatedEvent() {
        outboxPublisherServiceImpl.publishEvent();
    }
}
