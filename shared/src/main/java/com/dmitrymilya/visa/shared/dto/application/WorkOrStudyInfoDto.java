package com.dmitrymilya.visa.shared.dto.application;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkOrStudyInfoDto {

    @NotNull
    private String organization;

    @NotNull
    private AddressDto address;

    @NotNull
    private String jobTitle;

    @NotNull
    private ContactInfoDto contactInfo;

}
