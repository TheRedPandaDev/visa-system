package com.dmitrymilya.visa.incomingapplicationservice.consumer;

import com.dmitrymilya.visa.incomingapplicationservice.converter.ApplicantInfoToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.PersonDocumentToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaApplicationToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaApplicationUnmarshaller;
import com.dmitrymilya.visa.incomingapplicationservice.converter.VisaInfoToDtoConverter;
import com.dmitrymilya.visa.incomingapplicationservice.dto.KafkaSendMessageDto;
import com.dmitrymilya.visa.incomingapplicationservice.model.Address;
import com.dmitrymilya.visa.incomingapplicationservice.model.ApplicantInfo;
import com.dmitrymilya.visa.incomingapplicationservice.model.CategoryEnum;
import com.dmitrymilya.visa.incomingapplicationservice.model.ContactInfo;
import com.dmitrymilya.visa.incomingapplicationservice.model.DatePeriod;
import com.dmitrymilya.visa.incomingapplicationservice.model.EntryTypeEnum;
import com.dmitrymilya.visa.incomingapplicationservice.model.FIO;
import com.dmitrymilya.visa.incomingapplicationservice.model.FullPersonData;
import com.dmitrymilya.visa.incomingapplicationservice.model.PersonDocument;
import com.dmitrymilya.visa.incomingapplicationservice.model.SexEnum;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaApplication;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaForm;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisaInfo;
import com.dmitrymilya.visa.incomingapplicationservice.model.VisitPoints;
import com.dmitrymilya.visa.incomingapplicationservice.model.WorkOrStudyInfo;
import com.dmitrymilya.visa.incomingapplicationservice.service.ApplicationSender;
import com.dmitrymilya.visa.shared.dto.application.AddressDto;
import com.dmitrymilya.visa.shared.dto.application.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.application.ContactInfoDto;
import com.dmitrymilya.visa.shared.dto.application.PersonDocumentDto;
import com.dmitrymilya.visa.shared.dto.application.VisaApplicationDto;
import com.dmitrymilya.visa.shared.dto.application.VisaInfoDto;
import com.dmitrymilya.visa.shared.dto.application.WorkOrStudyInfoDto;
import com.dmitrymilya.visa.shared.util.DateUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class EpguVisaApplicationSmevAdapterTest {

    private final ModelMapper modelMapper = new ModelMapper();

    private final VisaApplicationUnmarshaller visaApplicationUnmarshaller = new VisaApplicationUnmarshaller();

    @SuppressWarnings("unchecked")
    private final KafkaTemplate<String, VisaApplicationDto> kafkaTemplate = Mockito.mock(KafkaTemplate.class);

    private final ApplicationSender applicationSender = new ApplicationSender(modelMapper, kafkaTemplate);

    private final EpguVisaApplicationSmevAdapter epguVisaApplicationSmevAdapter =
            new EpguVisaApplicationSmevAdapter(visaApplicationUnmarshaller, applicationSender);

    private final ArgumentCaptor<VisaApplicationDto> argumentCaptor = ArgumentCaptor.forClass(VisaApplicationDto.class);

    EpguVisaApplicationSmevAdapterTest() throws JAXBException {
        modelMapper.addConverter(new PersonDocumentToDtoConverter());
        modelMapper.addConverter(new ApplicantInfoToDtoConverter(modelMapper));
        modelMapper.addConverter(new VisaInfoToDtoConverter());
        modelMapper.addConverter(new VisaApplicationToDtoConverter(modelMapper));
    }

    @Test
    public void testConsume() throws JAXBException {
        VisaApplication visaApplication = getApplication();

        String marshalledApplication = getMarshalledApplication(visaApplication);
        KafkaSendMessageDto kafkaSendMessageDto = new KafkaSendMessageDto("", marshalledApplication);
        epguVisaApplicationSmevAdapter.consume(kafkaSendMessageDto);

        Mockito.verify(kafkaTemplate).send(any(), argumentCaptor.capture());
        VisaApplicationDto visaApplicationDto = argumentCaptor.getValue();

        assertEqual(visaApplication, visaApplicationDto);
    }

    private VisaApplication getApplication() {
        String date = LocalDate.now().format(DateUtil.DATE_TIME_FORMATTER);

        DatePeriod datePeriod = new DatePeriod();
        datePeriod.setStart(date);
        datePeriod.setEnd(date);

        VisaInfo visaInfo = new VisaInfo();
        visaInfo.setEntryType(EntryTypeEnum.MULTIPLE);
        visaInfo.setCategory(CategoryEnum.TOURIST);
        visaInfo.setValidPeriod(datePeriod);

        PersonDocument personDocument = new PersonDocument();
        personDocument.setSeriesCode("000");
        personDocument.setDocNo("111");
        personDocument.setIssueDate(date);
        personDocument.setValidPeriod(datePeriod);
        personDocument.setIssuer("Finnish Government");

        FIO fio = new FIO();
        fio.setFirstName("Tuomas");
        fio.setLastName("Ljeri");

        FullPersonData fullPersonData = new FullPersonData();
        fullPersonData.setPersonDocument(personDocument);
        fullPersonData.setSex(SexEnum.MALE);
        fullPersonData.setBirthCountry("Finland");
        fullPersonData.setCitizenship("Finland");
        fullPersonData.setFio(fio);
        fullPersonData.setBirthDate(date);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("Dmilya2000@gmail.com");
        contactInfo.setPhoneNumber("+7 (950) 501-51-37");

        Address address = new Address();
        address.setRegion("Uusimaa");
        address.setCity("Helsinki");

        WorkOrStudyInfo workOrStudyInfo = new WorkOrStudyInfo();
        workOrStudyInfo.setOrganization("Nokia");
        workOrStudyInfo.setJobTitle("Java Web Developer");
        workOrStudyInfo.setContactInfo(contactInfo);
        workOrStudyInfo.setAddress(address);

        ApplicantInfo applicantInfo = new ApplicantInfo();
        applicantInfo.setPersonData(fullPersonData);
        applicantInfo.setContactInfo(contactInfo);
        applicantInfo.setForeignLivingAddress(address);
        applicantInfo.setWorkOrStudyInfo(workOrStudyInfo);

        Address visitAddress = new Address();
        visitAddress.setRegion("Saint Petersburg");
        visitAddress.setCity("Saint Petersburg");

        VisitPoints visitPoints = new VisitPoints();
        visitPoints.getVisitPoint().add(visitAddress);

        VisaForm visaForm = new VisaForm();
        visaForm.setVisaInfo(visaInfo);
        visaForm.setApplicantInfo(applicantInfo);
        visaForm.setVisitPoints(visitPoints);
        visaForm.setAttachedPhoto(new byte[]{1});

        VisaApplication visaApplication = new VisaApplication();
        visaApplication.setOrderId(1);
        visaApplication.setId("1");
        visaApplication.setVisaForm(visaForm);

        return visaApplication;
    }

    private String getMarshalledApplication(VisaApplication visaApplication) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(VisaApplication.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();

        marshaller.marshal(visaApplication, stringWriter);

        return stringWriter.toString();
    }

    private void assertEqual(VisaApplication visaApplication, VisaApplicationDto visaApplicationDto) {
        VisaForm visaForm = visaApplication.getVisaForm();

        VisaInfo visaInfo = visaForm.getVisaInfo();
        ApplicantInfo applicantInfo = visaForm.getApplicantInfo();
        List<Address> visitPoints = visaForm.getVisitPoints().getVisitPoint();

        DatePeriod visaInfoValidPeriod = visaInfo.getValidPeriod();

        ContactInfo applicantInfoContactInfo = applicantInfo.getContactInfo();
        FullPersonData applicantInfoPersonData = applicantInfo.getPersonData();
        Address applicantInfoForeignLivingAddress = applicantInfo.getForeignLivingAddress();
        WorkOrStudyInfo applicantInfoWorkOrStudyInfo = applicantInfo.getWorkOrStudyInfo();

        ContactInfo contactInfo = applicantInfoWorkOrStudyInfo.getContactInfo();
        Address address = applicantInfoWorkOrStudyInfo.getAddress();

        PersonDocument personDocument = applicantInfoPersonData.getPersonDocument();

        FIO personDataFio = applicantInfoPersonData.getFio();

        assertThat(visaApplicationDto.getAttachedPhoto()).isEqualTo(visaForm.getAttachedPhoto());
        assertThat(visaApplicationDto.getVisitPoints()).hasSize(visitPoints.size());

        VisaInfoDto visaInfoDto = visaApplicationDto.getVisaInfo();

        assertThat(visaInfoDto.getCategory().name()).isEqualTo(visaInfo.getCategory().value());
        assertThat(visaInfoDto.getEntryType().name()).isEqualTo(visaInfo.getEntryType().value());
        assertThat(visaInfoDto.getValidFrom().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(visaInfoValidPeriod.getStart());
        assertThat(visaInfoDto.getValidTo().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(visaInfoValidPeriod.getEnd());

        ApplicantInfoDto applicantInfoDto = visaApplicationDto.getApplicantInfo();

        assertThat(applicantInfoDto.getSex().name()).isEqualTo(applicantInfoPersonData.getSex().name());
        assertThat(applicantInfoDto.getBirthCountry()).isEqualTo(applicantInfoPersonData.getBirthCountry());
        assertThat(applicantInfoDto.getCitizenship()).isEqualTo(applicantInfoPersonData.getCitizenship());
        assertThat(applicantInfoDto.getLastName()).isEqualTo(personDataFio.getLastName());
        assertThat(applicantInfoDto.getFirstName()).isEqualTo(personDataFio.getFirstName());
        assertThat(applicantInfoDto.getMiddleName()).isEqualTo(personDataFio.getMiddleName());
        assertThat(applicantInfoDto.getBirthDate().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(applicantInfoPersonData.getBirthDate());

        ContactInfoDto applicantInfoDtoContactInfo = applicantInfoDto.getContactInfo();

        assertThat(applicantInfoDtoContactInfo.getEmail()).isEqualTo(applicantInfoContactInfo.getEmail());
        assertThat(applicantInfoDtoContactInfo.getPhoneNumber()).isEqualTo(applicantInfoContactInfo.getPhoneNumber());

        AddressDto addressDto = applicantInfoDto.getAddress();
        assertThat(addressDto.getRegion()).isEqualTo(applicantInfoForeignLivingAddress.getRegion());
        assertThat(addressDto.getCity()).isEqualTo(applicantInfoForeignLivingAddress.getCity());
        assertThat(addressDto.getStreet()).isEqualTo(applicantInfoForeignLivingAddress.getStreet());
        assertThat(addressDto.getHouse()).isEqualTo(applicantInfoForeignLivingAddress.getHouse());
        assertThat(addressDto.getBuilding()).isEqualTo(applicantInfoForeignLivingAddress.getBuilding());
        assertThat(addressDto.getSection()).isEqualTo(applicantInfoForeignLivingAddress.getSection());
        assertThat(addressDto.getApartment()).isEqualTo(applicantInfoForeignLivingAddress.getApartment());

        WorkOrStudyInfoDto workOrStudyInfoDto = applicantInfoDto.getWorkOrStudyInfo();
        assertThat(workOrStudyInfoDto.getOrganization()).isEqualTo(applicantInfoWorkOrStudyInfo.getOrganization());
        assertThat(workOrStudyInfoDto.getJobTitle()).isEqualTo(applicantInfoWorkOrStudyInfo.getJobTitle());

        ContactInfoDto workOrStudyInfoDtoContactInfo = workOrStudyInfoDto.getContactInfo();

        assertThat(workOrStudyInfoDtoContactInfo.getEmail()).isEqualTo(contactInfo.getEmail());
        assertThat(workOrStudyInfoDtoContactInfo.getPhoneNumber()).isEqualTo(contactInfo.getPhoneNumber());

        AddressDto workOrStudyInfoDtoAddress = workOrStudyInfoDto.getAddress();
        assertThat(workOrStudyInfoDtoAddress.getRegion()).isEqualTo(address.getRegion());
        assertThat(workOrStudyInfoDtoAddress.getCity()).isEqualTo(address.getCity());
        assertThat(workOrStudyInfoDtoAddress.getStreet()).isEqualTo(address.getStreet());
        assertThat(workOrStudyInfoDtoAddress.getHouse()).isEqualTo(address.getHouse());
        assertThat(workOrStudyInfoDtoAddress.getBuilding()).isEqualTo(address.getBuilding());
        assertThat(workOrStudyInfoDtoAddress.getSection()).isEqualTo(address.getSection());
        assertThat(workOrStudyInfoDtoAddress.getApartment()).isEqualTo(address.getApartment());

        PersonDocumentDto applicantInfoDtoPersonDocument = applicantInfoDto.getPersonDocument();
        assertThat(applicantInfoDtoPersonDocument.getSeriesCode()).isEqualTo(personDocument.getSeriesCode());
        assertThat(applicantInfoDtoPersonDocument.getDocNo()).isEqualTo(personDocument.getDocNo());
        assertThat(applicantInfoDtoPersonDocument.getIssueDate().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(personDocument.getIssueDate());
        DatePeriod validPeriod = personDocument.getValidPeriod();
        assertThat(applicantInfoDtoPersonDocument.getValidFrom().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(validPeriod.getStart());
        assertThat(applicantInfoDtoPersonDocument.getValidTo().format(DateUtil.DATE_TIME_FORMATTER))
                .isEqualTo(validPeriod.getEnd());
        assertThat(applicantInfoDtoPersonDocument.getIssuer()).isEqualTo(personDocument.getIssuer());
    }
}