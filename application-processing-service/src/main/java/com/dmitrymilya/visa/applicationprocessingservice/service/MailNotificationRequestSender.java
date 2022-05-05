package com.dmitrymilya.visa.applicationprocessingservice.service;

import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.shared.dto.mail.ApplicantNameDto;
import com.dmitrymilya.visa.shared.dto.mail.MailNotificationDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailNotificationRequestSender {

    private final KafkaTemplate<String, MailNotificationDto> kafkaTemplate;

    @Value("${application.kafka.mail-notification-topic-name:mail_notification}")
    private String mailNotificationTopicName;

    public void sendApplicationAcceptedNotificationRequest(ApplicantInfoEntity applicantInfoEntity) {
        sendRequest(applicantInfoEntity, NotificationReasonEnum.APPLICATION_ACCEPTED);
    }

    public void sendApplicationDeclinedNotificationRequest(ApplicantInfoEntity applicantInfoEntity) {
        sendRequest(applicantInfoEntity, NotificationReasonEnum.APPLICATION_DECLINED);
    }

    private void sendRequest(ApplicantInfoEntity applicantInfoEntity, NotificationReasonEnum notificationReason) {
        ApplicantNameDto applicantNameDto = new ApplicantNameDto();
        applicantNameDto.setLastName(applicantInfoEntity.getLastName());
        applicantNameDto.setFirstName(applicantInfoEntity.getFirstName());
        applicantNameDto.setMiddleName(applicantInfoEntity.getMiddleName());

        MailNotificationDto mailNotificationDto = new MailNotificationDto();
        mailNotificationDto.setApplicantName(applicantNameDto);
        mailNotificationDto.setEmail(applicantInfoEntity.getContactInfo().getEmail());
        mailNotificationDto.setNotificationReason(notificationReason);

        kafkaTemplate.send(mailNotificationTopicName, mailNotificationDto);
    }

}
