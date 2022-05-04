package com.dmitrymilya.visa.casedecisionservice.service;

import com.dmitrymilya.visa.shared.dao.ExternalInquiryResponseMapper;
import com.dmitrymilya.visa.casedecisionservice.dao.UserTaskMapper;
import com.dmitrymilya.visa.casedecisionservice.dto.UserTaskDto;
import com.dmitrymilya.visa.shared.entity.ExternalInquiryResponseEntity;
import com.dmitrymilya.visa.casedecisionservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.model.DecisionEnum;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskMapper userTaskMapper;

    private final ModelMapper modelMapper;

    private final ExternalInquiryResponseMapper externalInquiryResponseMapper;

    public void createUserTask(VisaCaseEntity visaCaseEntity) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setVisaCaseId(visaCaseEntity.getId());

        userTaskMapper.insert(userTaskEntity);
    }

    public UserTaskEntity getUserTaskForInquiries(Long userTaskId) {
        return userTaskMapper.getUserTaskForInquiriesById(userTaskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No user task up for inquiries with id: %s", userTaskId)));
    }

    public Page<List<UserTaskDto>> getUserTasksForInquiries(Integer page, Integer size) {
        List<UserTaskEntity> userTasksForInquiries =
                userTaskMapper.getUserTasksForInquiries(size, Page.getOffset(page, size));
        List<UserTaskDto> userTaskDtos = userTasksForInquiries.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskDto.class))
                .toList();

        return Page.createPage(userTaskDtos, userTaskDtos.size())
                .setTotal(userTaskMapper.getUserTasksForInquiriesCount());
    }

    public Page<List<UserTaskDto>> getUserTasksForDecision(Integer page, Integer size) {
        List<UserTaskEntity> userTasksForDecision =
                userTaskMapper.getUserTasksForDecision(size, Page.getOffset(page, size));
        List<UserTaskDto> userTaskDtos = userTasksForDecision.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskDto.class))
                .toList();

        return Page.createPage(userTaskDtos, userTaskDtos.size())
                .setTotal(userTaskMapper.getUserTasksForDecisionCount());
    }

    @Transactional
    public void saveInquiriesForUserTask(UserTaskEntity userTaskEntity,
                                         List<ExternalInquiryResponseDto> externalInquiryResponseDtos) {
        externalInquiryResponseDtos.forEach(externalInquiryResponseDto ->
                        {
                            ExternalInquiryResponseEntity externalInquiryResponseEntity =
                                    modelMapper.map(externalInquiryResponseDto, ExternalInquiryResponseEntity.class);
                            externalInquiryResponseEntity.setVisaCaseId(userTaskEntity.getVisaCaseId());

                            externalInquiryResponseMapper.insert(externalInquiryResponseEntity);
                        }
                );

        userTaskMapper.makeReadyDecision(userTaskEntity.getId());
    }

    public UserTaskEntity acceptVisa(Long userTaskId) {
        return updateUserTaskDecision(userTaskId, DecisionEnum.ACCEPT);
    }

    public UserTaskEntity declineVisa(Long userTaskId) {
        return updateUserTaskDecision(userTaskId, DecisionEnum.DECLINE);
    }

    private UserTaskEntity updateUserTaskDecision(Long userTaskId, DecisionEnum decision) {
        UserTaskEntity userTaskEntity = userTaskMapper.getUserTaskForDecisionById(userTaskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No user task up for decision with id: %s", userTaskId)));

        userTaskMapper.updateDecision(userTaskId, decision);
        return userTaskEntity;
    }

}
