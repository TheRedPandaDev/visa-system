package com.dmitrymilya.visa.incomingapplicationservice.service;

import com.dmitrymilya.visa.incomingapplicationservice.model.VisaApplication;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationSender {

    private final ModelMapper modelMapper;

    private final KafkaTemplate<String, VisaApplicationDto> kafkaTemplate;

    @Value("${application.kafka.application-processing-topic-name:application_processing}")
    private String applicationProcessingTopicName;

    public void sendToProcessing(VisaApplication visaApplication) {
        VisaApplicationDto visaApplicationDto = modelMapper.map(visaApplication, VisaApplicationDto.class);

        kafkaTemplate.send(applicationProcessingTopicName, visaApplicationDto);
    }

}
