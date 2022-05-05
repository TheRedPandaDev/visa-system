package com.dmitrymilya.visa.incomingapplicationservice.consumer;

import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaApplicationUnmarshaller;
import com.dmitrymilya.visa.incomingapplicationservice.dto.KafkaSendMessageDto;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaApplication;
import com.dmitrymilya.visa.incomingapplicationservice.service.ApplicationSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EpguVisaApplicationSmevAdapter {

    private final VisaApplicationUnmarshaller visaApplicationUnmarshaller;

    private final ApplicationSender applicationSender;

    @KafkaListener(topics = "${application.kafka.incoming-application-topic-name:incoming_application}")
    public void consume(@Payload KafkaSendMessageDto kafkaSendMessageDto) {
        try {
            VisaApplication visaApplication = visaApplicationUnmarshaller
                    .unmarshallApplication(kafkaSendMessageDto.getMessage());

            applicationSender.sendToProcessing(visaApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
