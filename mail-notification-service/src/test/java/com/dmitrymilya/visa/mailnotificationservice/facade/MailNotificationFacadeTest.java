package com.dmitrymilya.visa.mailnotificationservice.facade;

import com.dmitrymilya.visa.mailnotificationservice.generator.ApplicationAcceptedMessageGenerator;
import com.dmitrymilya.visa.mailnotificationservice.generator.ApplicationDeclinedMessageGenerator;
import com.dmitrymilya.visa.mailnotificationservice.generator.VisaIssueDeclinedMessageGenerator;
import com.dmitrymilya.visa.mailnotificationservice.generator.VisaIssuedMessageGenerator;
import com.dmitrymilya.visa.mailnotificationservice.service.MailSenderService;
import com.dmitrymilya.visa.shared.dto.mail.ApplicantNameDto;
import com.dmitrymilya.visa.shared.dto.mail.MailNotificationDto;
import com.dmitrymilya.visa.shared.model.NotificationReasonEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class MailNotificationFacadeTest {

    private final ApplicationAcceptedMessageGenerator applicationAcceptedMessageGenerator =
            new ApplicationAcceptedMessageGenerator();

    private final ApplicationDeclinedMessageGenerator applicationDeclinedMessageGenerator =
            new ApplicationDeclinedMessageGenerator();

    private final VisaIssueDeclinedMessageGenerator visaIssueDeclinedMessageGenerator = new VisaIssueDeclinedMessageGenerator();

    private final VisaIssuedMessageGenerator visaIssuedMessageGenerator = new VisaIssuedMessageGenerator();

    private final MailSenderService mailSenderService = Mockito.mock(MailSenderService.class);

    private final MailNotificationFacade mailNotificationFacade = new MailNotificationFacade(
            List.of(applicationAcceptedMessageGenerator, applicationDeclinedMessageGenerator,
                    visaIssueDeclinedMessageGenerator, visaIssuedMessageGenerator),
            mailSenderService);

    @Test
    void testSendMailNotificationApplicationAccepted() {
        MailNotificationDto mailNotificationDto = getMailNotificationDto();
        mailNotificationDto.setNotificationReason(NotificationReasonEnum.APPLICATION_ACCEPTED);

        mailNotificationFacade.sendMailNotification(mailNotificationDto);

        Mockito.verify(mailSenderService)
                .sendEmail(applicationAcceptedMessageGenerator.getMessage(mailNotificationDto.getApplicantName()),
                        mailNotificationDto.getEmail());
    }

    @Test
    void testSendMailNotificationApplicationDeclined() {
        MailNotificationDto mailNotificationDto = getMailNotificationDto();
        mailNotificationDto.setNotificationReason(NotificationReasonEnum.APPLICATION_DECLINED);

        mailNotificationFacade.sendMailNotification(mailNotificationDto);

        Mockito.verify(mailSenderService)
                .sendEmail(applicationDeclinedMessageGenerator.getMessage(mailNotificationDto.getApplicantName()),
                        mailNotificationDto.getEmail());
    }

    @Test
    void testSendMailNotificationVisaIssueDeclined() {
        MailNotificationDto mailNotificationDto = getMailNotificationDto();
        mailNotificationDto.setNotificationReason(NotificationReasonEnum.VISA_ISSUE_DECLINED);

        mailNotificationFacade.sendMailNotification(mailNotificationDto);

        Mockito.verify(mailSenderService)
                .sendEmail(visaIssueDeclinedMessageGenerator.getMessage(mailNotificationDto.getApplicantName()),
                        mailNotificationDto.getEmail());
    }

    @Test
    void testSendMailNotificationVisaIssued() {
        MailNotificationDto mailNotificationDto = getMailNotificationDto();
        mailNotificationDto.setNotificationReason(NotificationReasonEnum.VISA_ISSUED);

        mailNotificationFacade.sendMailNotification(mailNotificationDto);

        Mockito.verify(mailSenderService)
                .sendEmail(visaIssuedMessageGenerator.getMessage(mailNotificationDto.getApplicantName()),
                        mailNotificationDto.getEmail());
    }

    private MailNotificationDto getMailNotificationDto() {
        MailNotificationDto mailNotificationDto = new MailNotificationDto();
        ApplicantNameDto applicantNameDto = new ApplicantNameDto();
        applicantNameDto.setLastName("Ljeri");
        applicantNameDto.setFirstName("Tuomas");
        mailNotificationDto.setApplicantName(applicantNameDto);
        mailNotificationDto.setEmail("Dmilya2000@gmail.com");

        return mailNotificationDto;
    }
}