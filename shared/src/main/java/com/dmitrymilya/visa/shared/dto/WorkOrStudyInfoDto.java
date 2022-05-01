package com.dmitrymilya.visa.shared.dto;

import lombok.Data;

@Data
public class WorkOrStudyInfoDto {

    private String organization;

    private AddressDto address;

    private String jobTitle;

    private ContactInfoDto contactInfo;

}
