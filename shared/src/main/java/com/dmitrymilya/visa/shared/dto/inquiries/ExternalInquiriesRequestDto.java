package com.dmitrymilya.visa.shared.dto.inquiries;

import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import lombok.Data;

import java.util.List;

@Data
public class ExternalInquiriesRequestDto {

    private ApplicantInfoDto applicantInfoDto;

    private List<ExternalInquiryOrganizationEnum> externalInquiryOrganizations;

}
