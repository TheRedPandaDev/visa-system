package com.dmitrymilya.visa.applicationprocessingservice.consumer;

import com.dmitrymilya.visa.applicationprocessingservice.facade.ApplicationProcessingFacade;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMessageConsumer {

    private final ApplicationProcessingFacade applicationProcessingFacade;

    @KafkaListener(topics = "${application.kafka.application-processing-topic-name:application_processing}")
    public void consumeApplication(@Payload VisaApplicationDto visaApplication) {
        try {
            applicationProcessingFacade.prepareApplicationForProcessing(visaApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
