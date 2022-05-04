package com.dmitrymilya.visa.caseresolutionservice.service;

import com.dmitrymilya.visa.caseresolutionservice.dao.UserTaskMapper;
import com.dmitrymilya.visa.caseresolutionservice.dto.CaseResolutionDto;
import com.dmitrymilya.visa.caseresolutionservice.dto.UserTaskDto;
import com.dmitrymilya.visa.caseresolutionservice.entity.UserTaskEntity;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public Page<List<UserTaskDto>> getUserTasksForResolution(Integer page, Integer size) {
        List<UserTaskEntity> userTasksForInquiries =
                userTaskMapper.getUserTasksForResolution(size, Page.getOffset(page, size));
        List<UserTaskDto> userTaskDtos = userTasksForInquiries.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskDto.class))
                .toList();

        return Page.createPage(userTaskDtos, userTaskDtos.size())
                .setTotal(userTaskMapper.getUserTasksForResolutionCount());
    }

    public Page<List<UserTaskDto>> getResolvedUserTasks(Integer page, Integer size) {
        List<UserTaskEntity> userTasksForInquiries =
                userTaskMapper.getResolvedUserTasks(size, Page.getOffset(page, size));
        List<UserTaskDto> userTaskDtos = userTasksForInquiries.stream()
                .map(userTaskEntity -> modelMapper.map(userTaskEntity, UserTaskDto.class))
                .toList();

        return Page.createPage(userTaskDtos, userTaskDtos.size())
                .setTotal(userTaskMapper.getResolvedUserTasksCount());
    }

    public void updateUserTaskResolved(CaseResolutionDto caseResolutionDto) {
        userTaskMapper.updateUserTaskResolved(caseResolutionDto.getUserTaskId(), caseResolutionDto.getComment());
    }

}
