package com.dmitrymilya.visa.incomingapplicationservice.converter;

import com.dmitrymilya.visa.incomingapplicationservice.model.ApplicantInfo;
import com.dmitrymilya.visa.incomingapplicationservice.model.FIO;
import com.dmitrymilya.visa.incomingapplicationservice.model.FullPersonData;
import com.dmitrymilya.visa.shared.dto.AddressDto;
import com.dmitrymilya.visa.shared.dto.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.ContactInfoDto;
import com.dmitrymilya.visa.shared.dto.PersonDocumentDto;
import com.dmitrymilya.visa.shared.dto.WorkOrStudyInfoDto;
import com.dmitrymilya.visa.shared.model.SexEnum;
import com.dmitrymilya.visa.shared.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ApplicantInfoToDtoConverter implements Converter<ApplicantInfo, ApplicantInfoDto> {

    private final ModelMapper modelMapper;

    @Override
    public ApplicantInfoDto convert(MappingContext<ApplicantInfo, ApplicantInfoDto> mappingContext) {
        ApplicantInfo applicantInfo = mappingContext.getSource();
        ApplicantInfoDto applicantInfoDto = new ApplicantInfoDto();
        FullPersonData personData = applicantInfo.getPersonData();

        applicantInfoDto.setSex(SexEnum.valueOf(personData.getSex().value()));
        applicantInfoDto.setBirthCountry(personData.getBirthCountry());
        applicantInfoDto.setCitizenship(personData.getCitizenship());

        FIO personDataFio = personData.getFio();
        applicantInfoDto.setLastName(personDataFio.getLastName());
        applicantInfoDto.setMiddleName(personDataFio.getMiddleName());
        applicantInfoDto.setFirstName(personDataFio.getFirstName());

        applicantInfoDto.setBirthDate(LocalDate.parse(personData.getBirthDate(), DateUtil.DATE_TIME_FORMATTER));
        applicantInfoDto.setContactInfo(modelMapper.map(applicantInfo.getContactInfo(), ContactInfoDto.class));
        applicantInfoDto.setAddress(modelMapper.map(applicantInfo.getForeignLivingAddress(), AddressDto.class));
        applicantInfoDto.setWorkOrStudyInfo(modelMapper.map(applicantInfo.getWorkOrStudyInfo(),
                WorkOrStudyInfoDto.class));
        applicantInfoDto.setPersonDocument(modelMapper.map(personData.getPersonDocument(), PersonDocumentDto.class));

        return applicantInfoDto;
    }
}
