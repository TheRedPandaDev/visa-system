package com.dmitrymilya.visa.casedecisionservice.entity;

import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import com.dmitrymilya.visa.shared.model.InquiryResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalInquiryResponseEntity {

    private Long id;

    private ExternalInquiryOrganizationEnum from;

    private InquiryResponseStatusEnum inquiryResponseStatus;

    private String answer;

    private Long visaCaseId;

}
