package com.dmitrymilya.visa.shared.dto.application;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContactInfoDto {

    @NotNull
    private String phoneNumber;

    @NotNull
    private String email;

}
