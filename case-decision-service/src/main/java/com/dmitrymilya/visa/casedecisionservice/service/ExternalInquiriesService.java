package com.dmitrymilya.visa.casedecisionservice.service;

import com.dmitrymilya.visa.casedecisionservice.feign.ExternalInquiriesServiceFeignClient;
import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.inquiries.ExternalInquiriesRequestDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalInquiriesService {

    private final ExternalInquiriesServiceFeignClient externalInquiriesServiceFeignClient;

    public List<ExternalInquiryResponseDto> makeExternalInquiries(ApplicantInfoDto applicantInfoDto,
                                                                  List<ExternalInquiryOrganizationEnum> organizations) {
        ExternalInquiriesRequestDto externalInquiriesRequestDto = new ExternalInquiriesRequestDto();
        externalInquiriesRequestDto.setApplicantInfoDto(applicantInfoDto);
        externalInquiriesRequestDto.setExternalInquiryOrganizations(organizations);

        return externalInquiriesServiceFeignClient.makeExternalInquiries(externalInquiriesRequestDto);
    }

}
