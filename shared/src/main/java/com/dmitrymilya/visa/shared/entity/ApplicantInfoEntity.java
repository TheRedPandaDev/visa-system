package com.dmitrymilya.visa.shared.entity;

import com.dmitrymilya.visa.shared.entity.AddressEntity;
import com.dmitrymilya.visa.shared.entity.ContactInfoEntity;
import com.dmitrymilya.visa.shared.entity.PersonDocumentEntity;
import com.dmitrymilya.visa.shared.entity.WorkOrStudyInfoEntity;
import com.dmitrymilya.visa.shared.model.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantInfoEntity {

    private Long id;

    private SexEnum sex;

    private String birthCountry;

    private String citizenship;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthDate;

    private Long contactInfoId;

    private ContactInfoEntity contactInfo;

    private Long addressId;

    private AddressEntity address;

    private Long workOrStudyInfoId;

    private WorkOrStudyInfoEntity workOrStudyInfo;

    private Long personDocumentId;

    private PersonDocumentEntity personDocument;

}
