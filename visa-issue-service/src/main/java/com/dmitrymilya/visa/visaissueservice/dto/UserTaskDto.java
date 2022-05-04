package com.dmitrymilya.visa.visaissueservice.dto;

import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskDto {

    private Long id;

    private VisaCaseDto visaCase;

    private OffsetDateTime createDttm;

    private Boolean isIssued;

}
