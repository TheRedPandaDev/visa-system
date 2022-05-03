package com.dmitrymilya.visa.shared.dto.visacase;

import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import com.dmitrymilya.visa.shared.model.InquiryResponseStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExternalInquiryResponseDto {

    @NotNull
    private ExternalInquiryOrganizationEnum from;

    @NotNull
    private InquiryResponseStatusEnum inquiryResponseStatus;

    private String answer;

}
