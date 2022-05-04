package com.dmitrymilya.visa.shared.entity;

import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.shared.entity.ExternalInquiryResponseEntity;
import com.dmitrymilya.visa.shared.entity.VisaInfoEntity;
import com.dmitrymilya.visa.shared.entity.VisitAddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisaCaseEntity {

    private Long id;

    private Long applicantInfoId;

    private ApplicantInfoEntity applicantInfo;

    private List<VisitAddressEntity> visitPoints;

    private Long visaInfoId;

    private VisaInfoEntity visaInfo;

    private byte[] attachedPhoto;

    private List<ExternalInquiryResponseEntity> externalInquiryResponses;

    private OffsetDateTime createDttm;

}
