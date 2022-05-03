package com.dmitrymilya.visa.shared.dto.visacase;

import com.dmitrymilya.visa.shared.dto.application.AddressDto;
import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.application.VisaInfoDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class VisaCaseDto {

    @NotNull
    private ApplicantInfoDto applicantInfo;

    @NotNull
    private VisaInfoDto visaInfo;

    @NotNull
    private List<AddressDto> visitPoints;

    @NotNull
    private byte[] attachedPhoto;

    @NotNull
    private List<ExternalInquiryResponseDto> externalInquiryResponses;

}
