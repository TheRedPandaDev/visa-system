package com.dmitrymilya.visa.mailnotificationservice.facade;

import com.dmitrymilya.visa.mailnotificationservice.generator.MessageGenerator;
import com.dmitrymilya.visa.mailnotificationservice.service.MailSenderService;
import com.dmitrymilya.visa.shared.dto.mail.MailNotificationDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MailNotificationFacade {

    private final Map<NotificationReasonEnum, MessageGenerator> messageGenerators = new HashMap<>();

    private final MailSenderService mailSenderService;

    public MailNotificationFacade(List<MessageGenerator> messageGeneratorList, MailSenderService mailSenderService) {
        messageGeneratorList.forEach(messageGenerator ->
                messageGenerators.put(messageGenerator.getNotificationReason(), messageGenerator));
        this.mailSenderService = mailSenderService;
    }

    public void sendMailNotification(MailNotificationDto mailNotificationDto) {
        MessageGenerator messageGenerator = messageGenerators.get(mailNotificationDto.getNotificationReason());
        if (messageGenerator == null) {
            throw new RuntimeException(String.format("No message generator for reason: %s",
                    mailNotificationDto.getNotificationReason()));
        }

        String message = messageGenerator.getMessage(mailNotificationDto.getApplicantName());
        mailSenderService.sendEmail(message, mailNotificationDto.getEmail());
    }

}
