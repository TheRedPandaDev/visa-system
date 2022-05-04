package com.dmitrymilya.visa.caseresolutionservice.controller;

import com.dmitrymilya.visa.caseresolutionservice.dto.CaseResolutionDto;
import com.dmitrymilya.visa.caseresolutionservice.dto.UserTaskDto;
import com.dmitrymilya.visa.caseresolutionservice.facade.VisaCaseFacade;
import com.dmitrymilya.visa.caseresolutionservice.service.UserTaskService;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/case-resolution")
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    @GetMapping("/user-tasks-for-resolution")
    public ResponseEntity<Page<List<UserTaskDto>>> getUserTasksForResolution(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getUserTasksForResolution(page, size));
    }

    @GetMapping("/resolved-user-tasks")
    public ResponseEntity<Page<List<UserTaskDto>>> getResolvedUserTasks(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getResolvedUserTasks(page, size));
    }

    @PostMapping("/resolve")
    public ResponseEntity<Void> acceptVisa(@RequestBody CaseResolutionDto caseResolutionDto) {
        userTaskService.updateUserTaskResolved(caseResolutionDto);

        return ResponseEntity.ok().build();
    }

}
