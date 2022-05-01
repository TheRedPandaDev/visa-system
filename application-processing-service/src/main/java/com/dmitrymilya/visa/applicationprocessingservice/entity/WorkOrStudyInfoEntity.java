package com.dmitrymilya.visa.applicationprocessingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrStudyInfoEntity {

    private Long id;

    private String organization;

    private Long addressId;

    private AddressEntity address;

    private String jobTitle;

    private Long contactInfoId;

    private ContactInfoEntity contactInfo;

}
