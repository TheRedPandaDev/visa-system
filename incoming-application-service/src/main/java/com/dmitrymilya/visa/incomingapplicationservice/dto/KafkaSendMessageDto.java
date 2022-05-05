package com.dmitrymilya.visa.incomingapplicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaSendMessageDto {

    private String topic;

    private String message;

}
