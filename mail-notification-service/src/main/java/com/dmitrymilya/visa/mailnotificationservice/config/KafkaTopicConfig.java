package com.dmitrymilya.visa.mailnotificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.mail-notification-topic-name:mail_notification}")
    private String mailNotificationTopicName;

    @Bean
    public NewTopic mailNotificationTopic() {
        return TopicBuilder.name(mailNotificationTopicName)
                .build();
    }

}
