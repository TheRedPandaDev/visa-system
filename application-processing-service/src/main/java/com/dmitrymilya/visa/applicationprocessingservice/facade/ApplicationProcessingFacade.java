package com.dmitrymilya.visa.applicationprocessingservice.facade;

import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import com.dmitrymilya.visa.applicationprocessingservice.service.UserTaskService;
import com.dmitrymilya.visa.applicationprocessingservice.service.VisaApplicationService;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationProcessingFacade {

    private final VisaApplicationService visaApplicationService;

    private final UserTaskService userTaskService;

    public void prepareApplicationForProcessing(VisaApplicationDto visaApplicationDto) {
        VisaApplicationEntity visaApplicationEntity = visaApplicationService.saveVisaApplication(visaApplicationDto);
        userTaskService.createUserTask(visaApplicationEntity);
    }

    public void acceptApplication(Long userTaskId) {
        userTaskService.acceptApplication(userTaskId);
    }

    public void declineApplication(Long userTaskId) {
        userTaskService.declineApplication(userTaskId);
    }

}
