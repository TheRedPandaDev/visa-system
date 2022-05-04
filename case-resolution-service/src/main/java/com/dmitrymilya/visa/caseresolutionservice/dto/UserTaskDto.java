package com.dmitrymilya.visa.caseresolutionservice.dto;

import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonInclude(Include.NON_NULL)
public class UserTaskDto {

    private Long id;

    private VisaCaseDto visaCase;

    private OffsetDateTime createDttm;

    private Boolean isResolved;

    private OffsetDateTime resolvedDttm;

    private String comment;

}
