package com.dmitrymilya.visa.applicationprocessingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.application-processing-topic-name:application_processing}")
    private String applicationProcessingTopicName;

    @Bean
    public NewTopic applicationProcessingTopic() {
        return TopicBuilder.name(applicationProcessingTopicName)
                .build();
    }

}
