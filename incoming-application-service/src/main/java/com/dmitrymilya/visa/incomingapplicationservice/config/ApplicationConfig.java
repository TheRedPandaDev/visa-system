package com.dmitrymilya.visa.incomingapplicationservice.config;

import com.dmitrymilya.visa.incomingapplicationservice.converter.ApplicantInfoToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.PersonDocumentToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaApplicationToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaInfoToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new ApplicantInfoToDtoConverter(modelMapper));
        modelMapper.addConverter(new PersonDocumentToDtoConverter());
        modelMapper.addConverter(new VisaApplicationToDtoConverter(modelMapper));
        modelMapper.addConverter(new VisaInfoToDtoConverter());
        return modelMapper;
    }

}
