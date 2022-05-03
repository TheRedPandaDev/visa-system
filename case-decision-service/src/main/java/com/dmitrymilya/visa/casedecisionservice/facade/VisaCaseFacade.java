package com.dmitrymilya.visa.casedecisionservice.facade;

import com.dmitrymilya.visa.casedecisionservice.dto.UserTaskDto;
import com.dmitrymilya.visa.casedecisionservice.dto.VisaCaseExternalInquiriesRequestDto;
import com.dmitrymilya.visa.casedecisionservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.casedecisionservice.entity.VisaCaseEntity;
import com.dmitrymilya.visa.casedecisionservice.service.ExternalInquiriesService;
import com.dmitrymilya.visa.casedecisionservice.service.MailNotificationRequestSender;
import com.dmitrymilya.visa.casedecisionservice.service.UserTaskService;
import com.dmitrymilya.visa.casedecisionservice.service.VisaCaseSender;
import com.dmitrymilya.visa.casedecisionservice.service.VisaCaseService;
import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisaCaseFacade {

    private final VisaCaseService visaCaseService;

    private final UserTaskService userTaskService;

    private final ModelMapper modelMapper;

    private final ExternalInquiriesService externalInquiriesService;

    private final MailNotificationRequestSender mailNotificationRequestSender;

    private final VisaCaseSender visaCaseSender;

    @Transactional
    public void createVisaCase(VisaApplicationDto visaApplication) {
        VisaCaseEntity visaCaseEntity = visaCaseService.saveVisaCase(visaApplication);
        userTaskService.createUserTask(visaCaseEntity);
    }

    public List<ExternalInquiryResponseDto> makeExternalInquiries(VisaCaseExternalInquiriesRequestDto externalInquiriesRequest) {
        UserTaskEntity userTask = userTaskService.getUserTaskForInquiries(externalInquiriesRequest.getUserTaskId());
        ApplicantInfoDto applicantInfoDto = modelMapper.map(userTask.getVisaCase().getApplicantInfo(),
                ApplicantInfoDto.class);
        List<ExternalInquiryResponseDto> externalInquiryResponses =
                externalInquiriesService.makeExternalInquiries(applicantInfoDto,
                        externalInquiriesRequest.getOrganizations());
        userTaskService.saveInquiriesForUserTask(userTask, externalInquiryResponses);

        return externalInquiryResponses;
    }

    @Transactional
    public void acceptVisa(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskService.acceptVisa(userTaskId);
        visaCaseSender.sendToIssue(userTaskEntity.getVisaCase());
    }

    @Transactional
    public void declineVisa(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskService.declineVisa(userTaskId);
        VisaCaseEntity visaCase = userTaskEntity.getVisaCase();
        visaCaseSender.sendToResolution(visaCase);
        mailNotificationRequestSender.sendVisaIssueDeclinedNotificationRequest(visaCase.getApplicantInfo());
    }

}
