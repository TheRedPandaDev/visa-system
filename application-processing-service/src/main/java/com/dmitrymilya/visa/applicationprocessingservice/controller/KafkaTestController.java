package com.dmitrymilya.visa.applicationprocessingservice.controller;

import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {

    private final KafkaTemplate<String, VisaApplicationDto> kafkaTemplate;

    @PostMapping("/application-processing/kafka-test")
    public void sendMessage(@RequestBody VisaApplicationDto visaApplicationDto) {
        kafkaTemplate.send("application_processing", visaApplicationDto);
    }

}
