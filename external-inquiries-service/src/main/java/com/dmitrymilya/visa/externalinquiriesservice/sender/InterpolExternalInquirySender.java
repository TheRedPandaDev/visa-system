package com.dmitrymilya.visa.externalinquiriesservice.sender;

import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import org.springframework.stereotype.Component;

@Component
public class InterpolExternalInquirySender extends ExternalInquirySender {

    @Override
    public ExternalInquiryOrganizationEnum getOrganization() {
        return ExternalInquiryOrganizationEnum.INTERPOL;
    }
}
