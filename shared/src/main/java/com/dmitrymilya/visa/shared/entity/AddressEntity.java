package com.dmitrymilya.visa.shared.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    private Long id;

    private String region;

    private String city;

    private String street;

    private String house;

    private String section;

    private String building;

    private String apartment;

}
