package com.dmitrymilya.visa.caseresolutionservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.case-resolution-topic-name:case_resolution}")
    private String caseResolutionTopicName;

    @Bean
    public NewTopic caseResolutionTopic() {
        return TopicBuilder.name(caseResolutionTopicName)
                .build();
    }

}
