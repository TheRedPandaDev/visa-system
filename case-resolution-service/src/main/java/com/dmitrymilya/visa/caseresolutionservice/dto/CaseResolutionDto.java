package com.dmitrymilya.visa.caseresolutionservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CaseResolutionDto {

    @NotNull
    private Long userTaskId;

    @NotNull
    private String comment;

}
