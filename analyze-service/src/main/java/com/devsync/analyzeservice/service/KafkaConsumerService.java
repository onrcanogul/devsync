package com.devsync.analyzeservice.service;

import com.devsync.analyzeservice.model.viewmodel.GithubWebhookModel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void listen(GithubWebhookModel model) throws JsonProcessingException;
}
