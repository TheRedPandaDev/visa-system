package com.dmitrymilya.visa.visaissueservice.facade;

import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.visaissueservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.visaissueservice.service.MailNotificationRequestSender;
import com.dmitrymilya.visa.visaissueservice.service.UserTaskService;
import com.dmitrymilya.visa.visaissueservice.service.VisaCaseSender;
import com.dmitrymilya.visa.visaissueservice.service.VisaCaseService;
import com.dmitrymilya.visa.visaissueservice.service.VisaPrintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VisaCaseFacade {

    private final VisaCaseService visaCaseService;

    private final UserTaskService userTaskService;

    private final VisaPrintService visaPrintService;

    private final VisaCaseSender visaCaseSender;

    private final MailNotificationRequestSender mailNotificationRequestSender;

    @Transactional
    public void createVisaCase(VisaCaseDto visaCase) {
        VisaCaseEntity visaCaseEntity = visaCaseService.saveVisaCase(visaCase);
        userTaskService.createUserTask(visaCaseEntity);
    }

    public byte[] printVisa(Long userTaskId) {
        UserTaskEntity userTask = userTaskService.getUserTaskForIssuing(userTaskId);

        return visaPrintService.printVisa(userTask.getVisaCase());
    }

    @Transactional
    public void issueVisa(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskService.updateUserTaskIssued(userTaskId);
        VisaCaseEntity visaCase = userTaskEntity.getVisaCase();

        visaCaseSender.sendToResolution(visaCase);
        mailNotificationRequestSender.sendVisaIssuedNotificationRequest(visaCase.getApplicantInfo());
    }

}
