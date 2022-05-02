package com.dmitrymilya.visa.shared.dto.application;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PersonDocumentDto {

    private String seriesCode;

    @NotNull
    private String docNo;

    @NotNull
    private LocalDate issueDate;

    private LocalDate validFrom;

    private LocalDate validTo;

    @NotNull
    private String issuer;

}
