package com.dmitrymilya.visa.applicationprocessingservice.service;

import com.dmitrymilya.visa.applicationprocessingservice.dao.AddressMapper;
import com.dmitrymilya.visa.applicationprocessingservice.dao.ContactInfoMapper;
import com.dmitrymilya.visa.applicationprocessingservice.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.ContactInfoEntity;
import com.dmitrymilya.visa.applicationprocessingservice.entity.VisaApplicationEntity;
import com.dmitrymilya.visa.shared.dto.VisaApplicationDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisaApplicationService {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    private final ContactInfoMapper contactInfoMapper;

    public VisaApplicationEntity saveVisaApplication(VisaApplicationDto visaApplicationDto) {
        VisaApplicationEntity visaApplicationEntity = modelMapper.map(visaApplicationDto, VisaApplicationEntity.class);

        ApplicantInfoEntity applicantInfo = visaApplicationEntity.getApplicantInfo();
        applicantInfo.setAddressId(addressMapper.insert(applicantInfo.getAddress()).getId());
        applicantInfo.setContactInfoId(contactInfoMapper.insert(applicantInfo.getContactInfo()).getId());

        return visaApplicationEntity; //todo return saved
    }

}
