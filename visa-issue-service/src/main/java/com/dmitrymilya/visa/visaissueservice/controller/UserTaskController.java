package com.dmitrymilya.visa.visaissueservice.controller;

import com.dmitrymilya.visa.shared.util.Page;
import com.dmitrymilya.visa.visaissueservice.dto.UserTaskDto;
import com.dmitrymilya.visa.visaissueservice.facade.VisaCaseFacade;
import com.dmitrymilya.visa.visaissueservice.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/visa-issue")
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    private final VisaCaseFacade visaCaseFacade;

    @GetMapping("/user-tasks-for-issuing")
    public ResponseEntity<Page<List<UserTaskDto>>> getUserTasksForInquiries(
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userTaskService.getUserTasksForIssuing(page, size));
    }

    @PostMapping(value = "/{userTaskId}/print", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] printVisa(@PathVariable Long userTaskId) {

        return visaCaseFacade.printVisa(userTaskId);
    }

    @PostMapping("/{userTaskId}/issue")
    public ResponseEntity<Void> issueVisa(@PathVariable Long userTaskId) {
        visaCaseFacade.issueVisa(userTaskId);

        return ResponseEntity.ok().build();
    }

}
