package com.dmitrymilya.visa.incomingapplicationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.incoming-application-topic-name:incoming_application}")
    private String incomingApplicationTopicName;

    @Value("${application.kafka.application-processing-topic-name:application_processing}")
    private String applicationProcessingTopicName;

    @Bean
    public NewTopic incomingApplicationTopic() {
        return TopicBuilder.name(incomingApplicationTopicName)
                .build();
    }

    @Bean
    public NewTopic applicationProcessingTopic() {
        return TopicBuilder.name(applicationProcessingTopicName)
                .build();
    }

}
