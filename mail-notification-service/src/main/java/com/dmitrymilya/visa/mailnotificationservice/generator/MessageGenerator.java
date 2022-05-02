package com.dmitrymilya.visa.mailnotificationservice.generator;

import com.dmitrymilya.visa.shared.dto.mail.ApplicantNameDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;

public abstract class MessageGenerator {

    public abstract String getMessage(ApplicantNameDto applicantName);

    public abstract NotificationReasonEnum getNotificationReason();

}
