package com.dmitrymilya.visa.mailnotificationservice.generator;

import com.dmitrymilya.visa.shared.dto.mail.ApplicantNameDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDeclinedMessageGenerator extends MessageGenerator {

    @Override
    public String getMessage(ApplicantNameDto applicantName) {
        return String.format("Dear %s,\nYour visa application has been declined for further processing.",
                applicantName.getFio());
    }

    @Override
    public NotificationReasonEnum getNotificationReason() {
        return NotificationReasonEnum.APPLICATION_DECLINED;
    }
}
