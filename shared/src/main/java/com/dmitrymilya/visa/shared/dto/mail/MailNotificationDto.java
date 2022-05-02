package com.dmitrymilya.visa.shared.dto.mail;

import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import lombok.Data;

@Data
public class MailNotificationDto {

    private String email;

    private ApplicantNameDto applicantName;

    private NotificationReasonEnum notificationReason;

}
