package com.dmitrymilya.visa.incomingapplicationservice.converter;

import com.dmitrymilya.visa.incomingapplicationservice.model.VisaApplication;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaForm;
import com.dmitrymilya.visa.shared.dto.AddressDto;
import com.dmitrymilya.visa.shared.dto.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.VisaApplicationDto;
import com.dmitrymilya.visa.shared.dto.VisaInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisaApplicationToDtoConverter implements Converter<VisaApplication, VisaApplicationDto> {

    private final ModelMapper modelMapper;

    @Override
    public VisaApplicationDto convert(MappingContext<VisaApplication, VisaApplicationDto> mappingContext) {
        VisaForm visaForm = mappingContext.getSource().getVisaForm();
        VisaApplicationDto visaApplicationDto = new VisaApplicationDto();

        visaApplicationDto.setApplicantInfo(modelMapper.map(visaForm.getApplicantInfo(), ApplicantInfoDto.class));
        visaApplicationDto.setVisaInfoDto(modelMapper.map(visaForm.getVisaInfo(), VisaInfoDto.class));
        visaApplicationDto.setVisitPoints(visaForm.getVisitPoints().getVisitPoint().stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList());
        visaApplicationDto.setAttachedPhoto(visaForm.getAttachedPhoto());

        return visaApplicationDto;
    }
}
