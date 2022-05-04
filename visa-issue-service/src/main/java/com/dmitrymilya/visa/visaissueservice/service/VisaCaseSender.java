package com.dmitrymilya.visa.visaissueservice.service;

import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
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

    @Value("${application.kafka.case-resolution-topic-name:case_resolution}")
    private String caseResolutionTopicName;

    public void sendToResolution(VisaCaseEntity visaCase) {
        VisaCaseDto visaCaseDto = modelMapper.map(visaCase, VisaCaseDto.class);

        kafkaTemplate.send(caseResolutionTopicName, visaCaseDto);
    }

}
