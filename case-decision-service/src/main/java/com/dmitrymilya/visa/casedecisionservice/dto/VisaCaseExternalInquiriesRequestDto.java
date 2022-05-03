package com.dmitrymilya.visa.casedecisionservice.dto;

import com.dmitrymilya.visa.shared.model.ExternalInquiryOrganizationEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class VisaCaseExternalInquiriesRequestDto {

    @NotNull
    private Long userTaskId;

    @NotNull
    private List<ExternalInquiryOrganizationEnum> organizations;

}
