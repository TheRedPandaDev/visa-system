package com.dmitrymilya.visa.applicationprocessingservice;

import com.dmitrymilya.visa.applicationprocessingservice.dto.UserTaskDto;
import com.dmitrymilya.visa.applicationprocessingservice.facade.ApplicationProcessingFacade;
import com.dmitrymilya.visa.applicationprocessingservice.service.UserTaskService;
import com.dmitrymilya.visa.shared.dto.AddressDto;
import com.dmitrymilya.visa.shared.dto.ApplicantInfoDto;
import com.dmitrymilya.visa.shared.dto.ContactInfoDto;
import com.dmitrymilya.visa.shared.dto.PersonDocumentDto;
import com.dmitrymilya.visa.shared.dto.VisaApplicationDto;
import com.dmitrymilya.visa.shared.dto.VisaInfoDto;
import com.dmitrymilya.visa.shared.dto.WorkOrStudyInfoDto;
import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.EntryTypeEnum;
import com.dmitrymilya.visa.shared.model.SexEnum;
import com.dmitrymilya.visa.shared.util.Page;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@MapperScan
public class ApplicationProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationProcessingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ApplicationProcessingFacade applicationProcessingFacade, UserTaskService userTaskService) {
		return args -> {
			try {
				VisaApplicationDto visaApplicationDto = new VisaApplicationDto();
				visaApplicationDto.setAttachedPhoto(new byte[]{1});

				VisaInfoDto visaInfoDto = new VisaInfoDto();
				visaInfoDto.setCategory(CategoryEnum.TOURIST);
				visaInfoDto.setEntryType(EntryTypeEnum.MULTIPLE);
				visaInfoDto.setValidFrom(LocalDate.now());
				visaInfoDto.setValidTo(LocalDate.now());
				visaApplicationDto.setVisaInfo(visaInfoDto);

				ApplicantInfoDto applicantInfoDto = new ApplicantInfoDto();
				applicantInfoDto.setBirthCountry("Finland");
				applicantInfoDto.setCitizenship("Finland");
				applicantInfoDto.setBirthDate(LocalDate.now());
				applicantInfoDto.setLastName("Ljeri");
				applicantInfoDto.setFirstName("Tuomas");
				applicantInfoDto.setSex(SexEnum.MALE);
				visaApplicationDto.setApplicantInfo(applicantInfoDto);

				PersonDocumentDto personDocumentDto = new PersonDocumentDto();
				personDocumentDto.setIssuer("Finnish Government");
				personDocumentDto.setDocNo("000");
				personDocumentDto.setSeriesCode("111");
				personDocumentDto.setValidFrom(LocalDate.now());
				personDocumentDto.setValidTo(LocalDate.now());
				personDocumentDto.setIssueDate(LocalDate.now());
				applicantInfoDto.setPersonDocument(personDocumentDto);

				WorkOrStudyInfoDto workOrStudyInfoDto = new WorkOrStudyInfoDto();
				workOrStudyInfoDto.setOrganization("Nokia");
				workOrStudyInfoDto.setJobTitle("Java Web Developer");
				applicantInfoDto.setWorkOrStudyInfo(workOrStudyInfoDto);

				AddressDto addressDto = new AddressDto();
				addressDto.setRegion("Uusimaa");
				addressDto.setCity("Helsinki");
				workOrStudyInfoDto.setAddress(addressDto);
				applicantInfoDto.setAddress(addressDto);
				visaApplicationDto.setVisitPoints(Collections.singletonList(addressDto));

				ContactInfoDto contactInfoDto = new ContactInfoDto();
				contactInfoDto.setEmail("Dmilya2000@gmail.com");
				contactInfoDto.setPhoneNumber("+7 (950) 501-51-37");
				workOrStudyInfoDto.setContactInfo(contactInfoDto);
				applicantInfoDto.setContactInfo(contactInfoDto);

//				applicationProcessingFacade.prepareApplicationForProcessing(visaApplicationDto);
				Page<List<UserTaskDto>> userTasksForDecision = userTaskService.getUserTasksForDecision(1, 10);

				System.out.println(userTasksForDecision);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
