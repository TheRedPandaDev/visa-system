package com.dmitrymilya.visa.shared.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDocumentEntity {

    private Long id;

    private String seriesCode;

    private String docNo;

    private LocalDate issueDate;

    private LocalDate validFrom;

    private LocalDate validTo;

    private String issuer;

}
