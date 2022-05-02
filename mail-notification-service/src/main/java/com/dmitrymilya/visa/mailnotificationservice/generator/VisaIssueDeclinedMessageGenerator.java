package com.dmitrymilya.visa.mailnotificationservice.generator;

import com.dmitrymilya.visa.shared.dto.mail.ApplicantNameDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import org.springframework.stereotype.Component;

@Component
public class VisaIssueDeclinedMessageGenerator extends MessageGenerator {

    @Override
    public String getMessage(ApplicantNameDto applicantName) {
        return String.format("Dear %s,\nYour visa has been declined for issuing.",
                applicantName.getFio());
    }

    @Override
    public NotificationReasonEnum getNotificationReason() {
        return NotificationReasonEnum.VISA_ISSUE_DECLINED;
    }
}
