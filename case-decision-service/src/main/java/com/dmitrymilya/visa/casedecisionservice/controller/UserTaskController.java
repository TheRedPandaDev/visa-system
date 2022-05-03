package com.dmitrymilya.visa.casedecisionservice.controller;

import com.dmitrymilya.visa.casedecisionservice.dto.UserTaskDto;
import com.dmitrymilya.visa.casedecisionservice.dto.VisaCaseExternalInquiriesRequestDto;
import com.dmitrymilya.visa.casedecisionservice.facade.VisaCaseFacade;
import com.dmitrymilya.visa.casedecisionservice.service.UserTaskService;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/case-decision")
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    private final VisaCaseFacade visaCaseFacade;

    @GetMapping("/user-tasks-for-inquiries")
    public ResponseEntity<Page<List<UserTaskDto>>> getUserTasksForInquiries(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getUserTasksForInquiries(page, size));
    }

    @GetMapping("/user-tasks-for-decision")
    public ResponseEntity<Page<List<UserTaskDto>>> getUserTasksForDecision(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getUserTasksForDecision(page, size));
    }

    @PostMapping("/make-inquiries")
    public ResponseEntity<List<ExternalInquiryResponseDto>> makeExternalInquiries(
            @RequestBody VisaCaseExternalInquiriesRequestDto externalInquiriesRequest
    ) {
        return ResponseEntity.ok(visaCaseFacade.makeExternalInquiries(externalInquiriesRequest));
    }

    @PostMapping("/{userTaskId}/accept")
    public ResponseEntity<Void> acceptVisa(@PathVariable Long userTaskId) {
        visaCaseFacade.acceptVisa(userTaskId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userTaskId}/decline")
    public ResponseEntity<Void> declineVisa(@PathVariable Long userTaskId) {
        visaCaseFacade.declineVisa(userTaskId);

        return ResponseEntity.ok().build();
    }

}
