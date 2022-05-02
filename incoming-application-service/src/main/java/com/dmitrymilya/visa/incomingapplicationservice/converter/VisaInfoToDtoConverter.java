package com.dmitrymilya.visa.incomingapplicationservice.converter;

import com.dmitrymilya.visa.incomingapplicationservice.model.DatePeriod;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaInfo;
import com.dmitrymilya.visa.shared.dto.application.VisaInfoDto;
import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.EntryTypeEnum;
import com.dmitrymilya.visa.shared.util.DateUtil;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VisaInfoToDtoConverter implements Converter<VisaInfo, VisaInfoDto> {

    @Override
    public VisaInfoDto convert(MappingContext<VisaInfo, VisaInfoDto> mappingContext) {
        VisaInfo visaInfo = mappingContext.getSource();
        VisaInfoDto visaInfoDto = new VisaInfoDto();

        visaInfoDto.setEntryType(EntryTypeEnum.valueOf(visaInfo.getEntryType().value()));
        visaInfoDto.setCategory(CategoryEnum.valueOf(visaInfo.getCategory().value()));

        DatePeriod validPeriod = visaInfo.getValidPeriod();
        visaInfoDto.setValidFrom(LocalDate.parse(validPeriod.getStart(), DateUtil.DATE_TIME_FORMATTER));
        visaInfoDto.setValidTo(LocalDate.parse(validPeriod.getEnd(), DateUtil.DATE_TIME_FORMATTER));

        return visaInfoDto;
    }
}
