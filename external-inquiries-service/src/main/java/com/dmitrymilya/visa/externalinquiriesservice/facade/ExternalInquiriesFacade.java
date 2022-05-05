package com.dmitrymilya.visa.externalinquiriesservice.facade;

import com.dmitrymilya.visa.externalinquiriesservice.sender.ExternalInquirySender;
import com.dmitrymilya.visa.shared.dto.inquiries.ExternalInquiriesRequestDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExternalInquiriesFacade {

    private final Map<ExternalInquiryOrganizationEnum, ExternalInquirySender> externalInquirySenders = new HashMap<>();

    public ExternalInquiriesFacade(List<ExternalInquirySender> externalInquirySenderList) {
        externalInquirySenderList.forEach(externalInquirySender ->
                externalInquirySenders.put(externalInquirySender.getOrganization(), externalInquirySender));
    }

    public List<ExternalInquiryResponseDto> makeExternalInquiries(ExternalInquiriesRequestDto externalInquiriesRequestDto) {
        List<ExternalInquiryResponseDto> externalInquiryResponses = new ArrayList<>();
        externalInquiriesRequestDto.getExternalInquiryOrganizations().forEach(externalInquiryOrganization -> {
            ExternalInquirySender externalInquirySender = externalInquirySenders.get(externalInquiryOrganization);

            ExternalInquiryResponseDto externalInquiryResponse =
                    externalInquirySender.makeInquiry(externalInquiriesRequestDto.getApplicantInfoDto());
            externalInquiryResponses.add(externalInquiryResponse);
        });

        return externalInquiryResponses;
    }

}
