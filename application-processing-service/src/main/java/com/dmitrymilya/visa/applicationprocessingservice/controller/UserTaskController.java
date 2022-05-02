package com.dmitrymilya.visa.applicationprocessingservice.controller;

import com.dmitrymilya.visa.applicationprocessingservice.dto.UserTaskDto;
import com.dmitrymilya.visa.applicationprocessingservice.service.UserTaskService;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/application-processing")
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    @GetMapping("/user-tasks")
    public ResponseEntity<Page<List<UserTaskDto>>> getUserTasksForDecision(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getUserTasksForDecision(page, size));
    }

    @PostMapping("/{userTaskId}/accept")
    public ResponseEntity<Void> acceptApplication(@PathVariable Long userTaskId) {
        userTaskService.acceptApplication(userTaskId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userTaskId}/decline")
    public ResponseEntity<Void> declineApplication(@PathVariable Long userTaskId) {
        userTaskService.declineApplication(userTaskId);

        return ResponseEntity.ok().build();
    }

}
