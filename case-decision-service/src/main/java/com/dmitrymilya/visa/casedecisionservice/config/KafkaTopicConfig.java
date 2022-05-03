package com.dmitrymilya.visa.casedecisionservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.case-decision-topic-name:case_decision}")
    private String caseDecisionTopicName;

    @Bean
    public NewTopic caseDecisionTopic() {
        return TopicBuilder.name(caseDecisionTopicName)
                .build();
    }

}
