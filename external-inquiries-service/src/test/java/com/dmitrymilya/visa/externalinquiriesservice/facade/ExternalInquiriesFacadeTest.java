package com.dmitrymilya.visa.externalinquiriesservice.facade;

import com.dmitrymilya.visa.externalinquiriesservice.sender.FsbExternalInquirySender;
import com.dmitrymilya.visa.externalinquiriesservice.sender.InterpolExternalInquirySender;
import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.inquiries.ExternalInquiriesRequestDto;
import com.dmitrymilya.visa.shared.dto.visacase.ExternalInquiryResponseDto;
import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalInquiriesFacadeTest {

    private final FsbExternalInquirySender fsbExternalInquirySender = new FsbExternalInquirySender();

    private final InterpolExternalInquirySender interpolExternalInquirySender = new InterpolExternalInquirySender();

    private final ExternalInquiriesFacade externalInquiriesFacade =
            new ExternalInquiriesFacade(List.of(fsbExternalInquirySender, interpolExternalInquirySender));

    @Test
    void testMakeExternalInquiries() {
        ExternalInquiriesRequestDto externalInquiriesRequestDto = getExternalInquiriesRequestDto();
        List<ExternalInquiryResponseDto> externalInquiryResponseDtos =
                externalInquiriesFacade.makeExternalInquiries(externalInquiriesRequestDto);

        assertThat(externalInquiryResponseDtos)
                .hasSize(externalInquiriesRequestDto.getExternalInquiryOrganizations().size());
    }

    private ExternalInquiriesRequestDto getExternalInquiriesRequestDto() {
        ExternalInquiriesRequestDto externalInquiriesRequestDto = new ExternalInquiriesRequestDto();
        externalInquiriesRequestDto.setExternalInquiryOrganizations(List.of(ExternalInquiryOrganizationEnum.FSB,
                ExternalInquiryOrganizationEnum.INTERPOL));
        externalInquiriesRequestDto.setApplicantInfoDto(new ApplicantInfoDto());

        return externalInquiriesRequestDto;
    }
}