package com.devsync.analyzeservice;

import com.devsync.analyzeservice.configuration.OpenAIConfigurations;
import com.devsync.analyzeservice.configuration.OpenRouterConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({ OpenRouterConfigurations.class, OpenAIConfigurations.class })
@EnableScheduling
public class AnalyzeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzeServiceApplication.class, args);
	}

}
