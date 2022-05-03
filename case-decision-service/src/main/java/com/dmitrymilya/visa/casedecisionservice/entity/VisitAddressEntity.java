package com.dmitrymilya.visa.casedecisionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitAddressEntity {

    private Long id;

    private String region;

    private String city;

    private Long visaCaseId;

}
