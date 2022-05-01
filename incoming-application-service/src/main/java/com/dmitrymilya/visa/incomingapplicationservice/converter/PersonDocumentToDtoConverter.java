package com.dmitrymilya.visa.incomingapplicationservice.converter;

import com.dmitrymilya.visa.incomingapplicationservice.model.DatePeriod;
import com.dmitrymilya.visa.incomingapplicationservice.model.PersonDocument;
import com.dmitrymilya.visa.shared.dto.PersonDocumentDto;
import com.dmitrymilya.visa.shared.util.DateUtil;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PersonDocumentToDtoConverter implements Converter<PersonDocument, PersonDocumentDto> {
    @Override
    public PersonDocumentDto convert(MappingContext<PersonDocument, PersonDocumentDto> mappingContext) {
        PersonDocument personDocument = mappingContext.getSource();
        PersonDocumentDto personDocumentDto = new PersonDocumentDto();

        personDocumentDto.setSeriesCode(personDocument.getSeriesCode());
        personDocumentDto.setDocNo(personDocument.getDocNo());
        personDocumentDto.setIssueDate(LocalDate.parse(personDocument.getIssueDate(), DateUtil.DATE_TIME_FORMATTER));

        DatePeriod validPeriod = personDocument.getValidPeriod();
        personDocumentDto.setValidFrom(LocalDate.parse(validPeriod.getStart(), DateUtil.DATE_TIME_FORMATTER));
        personDocumentDto.setValidTo(LocalDate.parse(validPeriod.getEnd(), DateUtil.DATE_TIME_FORMATTER));

        personDocumentDto.setIssuer(personDocument.getIssuer());

        return personDocumentDto;
    }
}
