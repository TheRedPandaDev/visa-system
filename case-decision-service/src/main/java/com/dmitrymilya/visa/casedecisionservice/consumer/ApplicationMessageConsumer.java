package com.dmitrymilya.visa.casedecisionservice.consumer;

import com.dmitrymilya.visa.casedecisionservice.facade.VisaCaseFacade;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMessageConsumer {

    private final VisaCaseFacade visaCaseFacade;

    @KafkaListener(topics = "${application.kafka.case-decision-topic-name:case_decision}")
    public void consumeApplication(@Payload VisaApplicationDto visaApplication) {
        visaCaseFacade.createVisaCase(visaApplication);
    }
}
