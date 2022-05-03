package com.dmitrymilya.visa.externalinquiriesservice.sender;

import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import com.dmitrymilya.visa.shared.model.InquiryResponseStatusEnum;

public abstract class ExternalInquirySender {

    public ExternalInquiryResponseDto makeInquiry(ApplicantInfoDto applicantInfoDto) {
        ExternalInquiryResponseDto externalInquiryResponseDto = new ExternalInquiryResponseDto();
        externalInquiryResponseDto.setFrom(getOrganization());
        externalInquiryResponseDto.setInquiryResponseStatus(InquiryResponseStatusEnum.OK);
        externalInquiryResponseDto.setAnswer(InquiryResponseStatusEnum.OK.name());

        return externalInquiryResponseDto;
    }

    public abstract ExternalInquiryOrganizationEnum getOrganization();

}
