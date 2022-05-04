package com.dmitrymilya.visa.visaissueservice.consumer;

import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.dmitrymilya.visa.visaissueservice.facade.VisaCaseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisaCaseConsumer {

    private final VisaCaseFacade visaCaseFacade;

    @KafkaListener(topics = "${application.kafka.visa-issue-topic-name:visa_issue}")
    public void consumeApplication(VisaCaseDto visaCase) {
        visaCaseFacade.createVisaCase(visaCase);
    }
}
