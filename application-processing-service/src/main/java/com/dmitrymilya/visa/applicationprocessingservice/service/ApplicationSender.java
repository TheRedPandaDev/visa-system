package com.dmitrymilya.visa.applicationprocessingservice.service;

import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
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

    @Value("${application.kafka.case-decision-topic-name:case_decision}")
    private String caseDecisionTopicName;

    public void sendToCaseDecision(VisaApplicationEntity visaApplication) {
        VisaApplicationDto visaApplicationDto = modelMapper.map(visaApplication, VisaApplicationDto.class);

        kafkaTemplate.send(caseDecisionTopicName, visaApplicationDto);
    }

}
