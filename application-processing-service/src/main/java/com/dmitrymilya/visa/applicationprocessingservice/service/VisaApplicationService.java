package com.dmitrymilya.visa.applicationprocessingservice.service;

import com.dmitrymilya.visa.applicationprocessingservice.dao.AddressMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.ApplicantInfoMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.ContactInfoMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.PersonDocumentMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.VisaApplicationMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.VisaInfoMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.VisitAddressMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.WorkOrStudyInfoMapper;
import com.dmitrymilya.visa.applicationprocessingservice.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.WorkOrStudyInfoEntity;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VisaApplicationService {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    private final ContactInfoMapper contactInfoMapper;

    private final WorkOrStudyInfoMapper workOrStudyInfoMapper;

    private final PersonDocumentMapper personDocumentMapper;

    private final ApplicantInfoMapper applicantInfoMapper;

    private final VisaInfoMapper visaInfoMapper;

    private final VisaApplicationMapper visaApplicationMapper;

    private final VisitAddressMapper visitAddressMapper;

    @Transactional
    public VisaApplicationEntity saveVisaApplication(VisaApplicationDto visaApplicationDto) {
        VisaApplicationEntity visaApplicationEntity = modelMapper.map(visaApplicationDto, VisaApplicationEntity.class);

        ApplicantInfoEntity applicantInfo = visaApplicationEntity.getApplicantInfo();

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
        visaApplicationEntity.setApplicantInfoId(applicantInfo.getId());
        visaInfoMapper.insert(visaApplicationEntity.getVisaInfo());
        visaApplicationEntity.setVisaInfoId(visaApplicationEntity.getVisaInfo().getId());

        visaApplicationMapper.insert(visaApplicationEntity);

        visaApplicationEntity.getVisitPoints().forEach(visitAddressEntity -> {
            visitAddressEntity.setVisaApplicationId(visaApplicationEntity.getId());
            visitAddressMapper.insert(visitAddressEntity);
        });

        return visaApplicationEntity;
    }

}
