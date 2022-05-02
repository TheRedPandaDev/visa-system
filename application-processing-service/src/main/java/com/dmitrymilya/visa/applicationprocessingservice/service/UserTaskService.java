package com.dmitrymilya.visa.applicationprocessingservice.service;

import com.dmitrymilya.visa.applicationprocessingservice.dao.UserTaskMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dto.UserTaskDto;
import com.dmitrymilya.visa.applicationprocessingservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import com.dmitrymilya.visa.shared.model.DecisionEnum;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskMapper userTaskMapper;

    private final ModelMapper modelMapper;

    public void createUserTask(VisaApplicationEntity visaApplicationEntity) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setVisaApplicationId(visaApplicationEntity.getId());

        userTaskMapper.insert(userTaskEntity);
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

    public UserTaskEntity acceptApplication(Long userTaskId) {
        return updateUserTaskDecision(userTaskId, DecisionEnum.ACCEPT);
    }

    public UserTaskEntity declineApplication(Long userTaskId) {
        return updateUserTaskDecision(userTaskId, DecisionEnum.DECLINE);
    }

    private UserTaskEntity updateUserTaskDecision(Long userTaskId, DecisionEnum decision) {
        UserTaskEntity userTaskEntity = userTaskMapper.getUserTaskById(userTaskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No user task up for decision with id: %s", userTaskId)));

        userTaskMapper.updateDecision(userTaskId, decision);
        return userTaskEntity;
    }

}
