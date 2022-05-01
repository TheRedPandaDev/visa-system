package com.dmitrymilya.visa.shared.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDocumentDto {

    private String seriesCode;

    private String docNo;

    private LocalDate issueDate;

    private LocalDate validFrom;

    private LocalDate validTo;

    private String issuer;

}
