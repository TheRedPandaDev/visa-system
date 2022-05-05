package com.dmitrymilya.visa.caseresolutionservice.facade;

import com.dmitrymilya.visa.caseresolutionservice.service.UserTaskService;
import com.dmitrymilya.visa.caseresolutionservice.service.VisaCaseService;
import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VisaCaseFacade {

    private final VisaCaseService visaCaseService;

    private final UserTaskService userTaskService;

    @Transactional
    public void createVisaCase(VisaCaseDto visaCase) {
        VisaCaseEntity visaCaseEntity = visaCaseService.saveVisaCase(visaCase);
        userTaskService.createUserTask(visaCaseEntity);
    }

}
