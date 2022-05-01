package com.dmitrymilya.visa.shared.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class VisaApplicationDto {

    @NotNull
    private ApplicantInfoDto applicantInfo;

    @NotNull
    private VisaInfoDto visaInfoDto;

    @NotNull
    private List<AddressDto> visitPoints;

    @NotNull
    private byte[] attachedPhoto;

}
