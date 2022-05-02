package com.dmitrymilya.visa.applicationprocessingservice.dto;

import com.dmitrymilya.visa.shared.dto.VisaApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskDto {

    private Long id;

    private VisaApplicationDto visaApplication;

    private OffsetDateTime createDttm;

}
