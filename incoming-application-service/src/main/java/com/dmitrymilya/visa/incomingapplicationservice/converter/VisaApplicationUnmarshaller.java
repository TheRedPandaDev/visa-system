package com.dmitrymilya.visa.incomingapplicationservice.converter;

import com.dmitrymilya.visa.incomingapplicationservice.model.VisaApplication;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class VisaApplicationUnmarshaller {

    private final Unmarshaller unmarshaller;

    public VisaApplicationUnmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(VisaApplication.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();
    }

    public VisaApplication unmarshallApplication(String xml) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        StreamSource source = new StreamSource(byteArrayInputStream);

        VisaApplication visaApplication;
        try {
            visaApplication = unmarshaller.unmarshal(source, VisaApplication.class).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(String.format("Could not unmarshall XML: %s", e));
        }

        return visaApplication;
    }
}
