package com.dmitrymilya.visa.applicationprocessingservice.facade;

import com.dmitrymilya.visa.applicationprocessingservice.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import com.dmitrymilya.visa.applicationprocessingservice.service.MailNotificationRequestSender;
import com.dmitrymilya.visa.applicationprocessingservice.service.UserTaskService;
import com.dmitrymilya.visa.applicationprocessingservice.service.VisaApplicationService;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationProcessingFacade {

    private final VisaApplicationService visaApplicationService;

    private final UserTaskService userTaskService;

    private final MailNotificationRequestSender mailNotificationRequestSender;

    @Transactional
    public void prepareApplicationForProcessing(VisaApplicationDto visaApplicationDto) {
        VisaApplicationEntity visaApplicationEntity = visaApplicationService.saveVisaApplication(visaApplicationDto);
        userTaskService.createUserTask(visaApplicationEntity);
    }

    @Transactional
    public void acceptApplication(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskService.acceptApplication(userTaskId);
        ApplicantInfoEntity applicantInfo = userTaskEntity.getVisaApplication().getApplicantInfo();
        mailNotificationRequestSender.sendApplicationAcceptedNotificationRequest(applicantInfo);
    }

    @Transactional
    public void declineApplication(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskService.declineApplication(userTaskId);
        ApplicantInfoEntity applicantInfo = userTaskEntity.getVisaApplication().getApplicantInfo();
        mailNotificationRequestSender.sendApplicationDeclinedNotificationRequest(applicantInfo);
    }

}
