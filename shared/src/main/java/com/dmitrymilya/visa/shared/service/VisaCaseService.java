package com.dmitrymilya.visa.shared.service;

import com.dmitrymilya.visa.shared.dao.AddressMapper;
import com.dmitrymilya.visa.shared.dao.ApplicantInfoMapper;
import com.dmitrymilya.visa.shared.dao.ContactInfoMapper;
import com.dmitrymilya.visa.shared.dao.ExternalInquiryResponseMapper;
import com.dmitrymilya.visa.shared.dao.PersonDocumentMapper;
import com.dmitrymilya.visa.shared.dao.VisaCaseMapper;
import com.dmitrymilya.visa.shared.dao.VisaInfoMapper;
import com.dmitrymilya.visa.shared.dao.VisitAddressMapper;
import com.dmitrymilya.visa.shared.dao.WorkOrStudyInfoMapper;
import com.dmitrymilya.visa.shared.dto.visacase.VisaCaseDto;
import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.entity.WorkOrStudyInfoEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public abstract class VisaCaseService {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    private final ContactInfoMapper contactInfoMapper;

    private final WorkOrStudyInfoMapper workOrStudyInfoMapper;

    private final PersonDocumentMapper personDocumentMapper;

    private final ApplicantInfoMapper applicantInfoMapper;

    private final VisaInfoMapper visaInfoMapper;

    private final VisaCaseMapper visaCaseMapper;

    private final VisitAddressMapper visitAddressMapper;

    private final ExternalInquiryResponseMapper externalInquiryResponseMapper;

    @Transactional
    public VisaCaseEntity saveVisaCase(VisaCaseDto visaCaseDto) {
        VisaCaseEntity visaCaseEntity = modelMapper.map(visaCaseDto, VisaCaseEntity.class);

        ApplicantInfoEntity applicantInfo = visaCaseEntity.getApplicantInfo();

        WorkOrStudyInfoEntity workOrStudyInfo = applicantInfo.getWorkOrStudyInfo();

        if (workOrStudyInfo != null) {
            contactInfoMapper.insert(workOrStudyInfo.getContactInfo());
            workOrStudyInfo.setContactInfoId(workOrStudyInfo.getContactInfo().getId());
            addressMapper.insert(workOrStudyInfo.getAddress());
            workOrStudyInfo.setAddressId(workOrStudyInfo.getAddress().getId());
            workOrStudyInfoMapper.insert(workOrStudyInfo);
            applicantInfo.setWorkOrStudyInfoId(workOrStudyInfo.getId());
        }

        addressMapper.insert(applicantInfo.getAddress());
        applicantInfo.setAddressId(applicantInfo.getAddress().getId());
        contactInfoMapper.insert(applicantInfo.getContactInfo());
        applicantInfo.setContactInfoId(applicantInfo.getContactInfo().getId());
        personDocumentMapper.insert(applicantInfo.getPersonDocument());
        applicantInfo.setPersonDocumentId(applicantInfo.getPersonDocument().getId());

        applicantInfoMapper.insert(applicantInfo);
        visaCaseEntity.setApplicantInfoId(applicantInfo.getId());
        visaInfoMapper.insert(visaCaseEntity.getVisaInfo());
        visaCaseEntity.setVisaInfoId(visaCaseEntity.getVisaInfo().getId());

        visaCaseMapper.insert(visaCaseEntity);

        visaCaseEntity.getVisitPoints().forEach(visitAddressEntity -> {
            visitAddressEntity.setVisaCaseId(visaCaseEntity.getId());
            visitAddressMapper.insert(visitAddressEntity);
        });

        visaCaseEntity.getExternalInquiryResponses().forEach(externalInquiryResponseEntity -> {
            externalInquiryResponseEntity.setVisaCaseId(visaCaseEntity.getId());
            externalInquiryResponseMapper.insert(externalInquiryResponseEntity);
        });

        return visaCaseEntity;
    }
}
