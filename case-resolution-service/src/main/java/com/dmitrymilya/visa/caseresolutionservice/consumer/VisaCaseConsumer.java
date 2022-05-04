package com.dmitrymilya.visa.caseresolutionservice.consumer;

import com.dmitrymilya.visa.caseresolutionservice.facade.VisaCaseFacade;
import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisaCaseConsumer {

    private final VisaCaseFacade visaCaseFacade;

    @KafkaListener(topics = "${application.kafka.case-resolution-topic-name:case_resolution}")
    public void consumeApplication(VisaCaseDto visaCase) {
        visaCaseFacade.createVisaCase(visaCase);
    }
}
