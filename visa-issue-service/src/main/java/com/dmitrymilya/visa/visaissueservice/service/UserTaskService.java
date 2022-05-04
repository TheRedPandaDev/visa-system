package com.dmitrymilya.visa.visaissueservice.service;

import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.util.Page;
import com.dmitrymilya.visa.visaissueservice.dao.UserTaskMapper;
import com.dmitrymilya.visa.visaissueservice.dto.UserTaskDto;
import com.dmitrymilya.visa.visaissueservice.entity.UserTaskEntity;
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

    public void createUserTask(VisaCaseEntity visaCaseEntity) {
        UserTaskEntity userTaskEntity = new UserTaskEntity();
        userTaskEntity.setVisaCaseId(visaCaseEntity.getId());

        userTaskMapper.insert(userTaskEntity);
    }

    public UserTaskEntity getUserTaskForIssuing(Long userTaskId) {
        return userTaskMapper.getUserTaskForIssuingById(userTaskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No user task up for issuing with id: %s", userTaskId)));
    }

    public Page<List<UserTaskDto>> getUserTasksForIssuing(Integer page, Integer size) {
        List<UserTaskEntity> userTasksForInquiries =
                userTaskMapper.getUserTasksForIssuing(size, Page.getOffset(page, size));
        List<UserTaskDto> userTaskDtos = userTasksForInquiries.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskDto.class))
                .toList();

        return Page.createPage(userTaskDtos, userTaskDtos.size())
                .setTotal(userTaskMapper.getUserTasksForIssuingCount());
    }

    public UserTaskEntity updateUserTaskIssued(Long userTaskId) {
        UserTaskEntity userTaskEntity = userTaskMapper.getUserTaskForIssuingById(userTaskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No user task up for issuing with id: %s", userTaskId)));

        userTaskMapper.updateUserTaskIssued(userTaskId);
        return userTaskEntity;
    }

}
