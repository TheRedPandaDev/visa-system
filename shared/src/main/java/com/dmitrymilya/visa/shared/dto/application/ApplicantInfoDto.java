package com.dmitrymilya.visa.shared.dto.application;

import com.dmitrymilya.visa.shared.model.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ApplicantInfoDto {

    @NotNull
    private SexEnum sex;

    @NotNull
    private String birthCountry;

    @NotNull
    private String citizenship;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private ContactInfoDto contactInfo;

    @NotNull
    private AddressDto address;

    private WorkOrStudyInfoDto workOrStudyInfo;

    @NotNull
    private PersonDocumentDto personDocument;

}
