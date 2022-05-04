package com.dmitrymilya.visa.visaissueservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.visa-issue-topic-name:visa_issue}")
    private String visaIssueTopicName;

    @Bean
    public NewTopic visaIssueTopic() {
        return TopicBuilder.name(visaIssueTopicName)
                .build();
    }

}
