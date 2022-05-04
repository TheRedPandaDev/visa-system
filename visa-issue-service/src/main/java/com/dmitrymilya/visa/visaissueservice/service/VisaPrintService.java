package com.dmitrymilya.visa.visaissueservice.service;

import com.dmitrymilya.visa.shared.entity.ApplicantInfoEntity;
import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import com.dmitrymilya.visa.shared.entity.VisaInfoEntity;
import io.micrometer.core.instrument.util.StringUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.finders.RangeFinder;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.Document;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisaPrintService {

    public byte[] printVisa(VisaCaseEntity visaCase) {
        byte[] printedVisa;
        try(ByteArrayOutputStream docxOutput = new ByteArrayOutputStream()) {
            File visaBlankFile = new ClassPathResource("files/visaBlank.docx").getFile();
            WordprocessingMLPackage wPackage = WordprocessingMLPackage.load(visaBlankFile);
            MainDocumentPart mainDocumentPart = wPackage.getMainDocumentPart();
            Document document = mainDocumentPart.getContents();
            Body body = document.getBody();
            List<Object> paragraphs = body.getContent();
            RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
            new TraversalUtil(paragraphs, rt);
            for (CTBookmark bm : rt.getStarts()) {
                if (bm.getName().equals("visaPhoto")) {
                    BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wPackage,
                            visaCase.getAttachedPhoto());

                    Inline inline = imagePart
                            .createImageInline(null, null, 0, 1, false, 1300);

                    Object parent = bm.getParent();
                    P paragraph = (P) parent;

                    ObjectFactory factory = new ObjectFactory();
                    R run = factory.createR();
                    paragraph.getContent().add(run);
                    Drawing drawing = factory.createDrawing();
                    run.getContent().add(drawing);
                    drawing.getAnchorOrInline().add(inline);
                }
            }
            Map<String, String> variables = getVariables(visaCase);
            VariablePrepare.prepare(wPackage);
            mainDocumentPart.variableReplace(variables);
            wPackage.save(docxOutput);
            printedVisa = docxOutput.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Got an error during visa printing: %s", e));
        }

        return printedVisa;
    }

    private Map<String, String> getVariables(VisaCaseEntity visaCase) {
        Map<String, String> variables = new HashMap<>();

        variables.put("issueDate", LocalDate.now().toString());
        VisaInfoEntity visaInfo = visaCase.getVisaInfo();
        variables.put("fromToDates", visaInfo.getValidFrom().toString() + "/" + visaInfo.getValidTo().toString());
        variables.put("category", visaInfo.getCategory().name());
        ApplicantInfoEntity applicantInfo = visaCase.getApplicantInfo();
        variables.put("citizenship", applicantInfo.getCitizenship());
        variables.put("fio", getFio(applicantInfo));
        variables.put("docNo", applicantInfo.getPersonDocument().getDocNo());
        variables.put("birthDate", applicantInfo.getBirthDate().toString());
        variables.put("sex", applicantInfo.getSex().name());
        variables.put("additionalInfo", "");

        return variables;
    }

    private String getFio(ApplicantInfoEntity applicantInfo) {
        return applicantInfo.getLastName() + " " + applicantInfo.getFirstName() +
                (StringUtils.isBlank(applicantInfo.getMiddleName()) ? "" : " " + applicantInfo.getMiddleName());
    }

}
