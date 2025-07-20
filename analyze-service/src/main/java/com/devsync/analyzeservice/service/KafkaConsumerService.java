package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.dto.event.git.PullRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void listen(PullRequestDto model) throws JsonProcessingException;
}
