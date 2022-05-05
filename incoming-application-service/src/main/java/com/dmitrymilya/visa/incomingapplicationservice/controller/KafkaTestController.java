package com.dmitrymilya.visa.incomingapplicationservice.controller;

import com.dmitrymilya.visa.incomingapplicationservice.dto.KafkaSendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {

    private final KafkaTemplate<String, KafkaSendMessageDto> kafkaTemplate;

    @PostMapping("/kafka-test")
    public void sendMessage(@RequestBody KafkaSendMessageDto kafkaSendMessageDto) {
        kafkaTemplate.send(kafkaSendMessageDto.getTopic(), kafkaSendMessageDto);
    }

}
