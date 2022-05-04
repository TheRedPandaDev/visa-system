package com.dmitrymilya.visa.visaissueservice.service;

import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.shared.entity.PersonDocumentEntity;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.entity.VisaInfoEntity;
import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.SexEnum;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VisaPrintServiceTest {

    private final VisaPrintService visaPrintService = new VisaPrintService();

    @Test
    void testPrintVisa() throws IOException {
        VisaCaseEntity visaCase = getVisaCase();
        byte[] bytes = visaPrintService.printVisa(visaCase);
        File file = new File("test.docx");
        file.delete();
        file.createNewFile();
        Files.write(file.toPath(), bytes);
    }

    private VisaCaseEntity getVisaCase() {
        VisaCaseEntity visaCase = new VisaCaseEntity();
        byte[] photo;
        try {
            photo = FileUtils.readFileToByteArray(new ClassPathResource("files/visa-photo.jpg").getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        visaCase.setAttachedPhoto(photo);
        VisaInfoEntity visaInfo = new VisaInfoEntity();
        visaInfo.setValidFrom(LocalDate.now());
        visaInfo.setValidTo(LocalDate.now().plusDays(1));
        visaInfo.setCategory(CategoryEnum.TOURIST);
        visaCase.setVisaInfo(visaInfo);
        ApplicantInfoEntity applicantInfo = new ApplicantInfoEntity();
        PersonDocumentEntity personDocument = new PersonDocumentEntity();
        personDocument.setDocNo("123456");
        applicantInfo.setPersonDocument(personDocument);
        applicantInfo.setCitizenship("Finland");
        applicantInfo.setLastName("Ljeri");
        applicantInfo.setFirstName("Tuomas");
        applicantInfo.setBirthDate(LocalDate.now().minusYears(30));
        applicantInfo.setSex(SexEnum.MALE);
        visaCase.setApplicantInfo(applicantInfo);

        return visaCase;
    }
}