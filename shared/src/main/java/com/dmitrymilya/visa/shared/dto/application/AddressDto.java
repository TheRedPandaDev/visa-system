package com.dmitrymilya.visa.shared.dto.application;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {

    @NotNull
    private String region;

    @NotNull
    private String city;

    private String street;

    private String house;

    private String section;

    private String building;

    private String apartment;

}
