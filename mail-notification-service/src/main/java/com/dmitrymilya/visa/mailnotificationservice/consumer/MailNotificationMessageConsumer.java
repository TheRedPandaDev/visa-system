package com.dmitrymilya.visa.mailnotificationservice.consumer;

import com.dmitrymilya.visa.mailnotificationservice.facade.MailNotificationFacade;
import com.dmitrymilya.visa.shared.dto.mail.MailNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailNotificationMessageConsumer {

    private final MailNotificationFacade mailNotificationFacade;

    @KafkaListener(topics = "${application.kafka.mail-notification-topic-name:mail_notification}")
    public void consume(MailNotificationDto mailNotificationDto) {
        mailNotificationFacade.sendMailNotification(mailNotificationDto);
    }

}
