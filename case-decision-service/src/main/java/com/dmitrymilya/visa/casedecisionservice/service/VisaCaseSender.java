package com.dmitrymilya.visa.casedecisionservice.service;

import com.dmitrymilya.visa.casedecisionservice.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisaCaseSender {

    private final ModelMapper modelMapper;

    private final KafkaTemplate<String, VisaCaseDto> kafkaTemplate;

    @Value("${application.kafka.visa-issue-topic-name:visa_issue}")
    private String visaIssueTopicName;

    @Value("${application.kafka.case-resolution-topic-name:case_resolution}")
    private String caseResolutionTopicName;

    public void sendToIssue(VisaCaseEntity visaCase) {
        send(visaCase, visaIssueTopicName);
    }

    public void sendToResolution(VisaCaseEntity visaCase) {
        send(visaCase, caseResolutionTopicName);
    }

    private void send(VisaCaseEntity visaCase, String visaIssueTopicName) {
        VisaCaseDto visaCaseDto = modelMapper.map(visaCase, VisaCaseDto.class);

        kafkaTemplate.send(visaIssueTopicName, visaCaseDto);
    }

}
