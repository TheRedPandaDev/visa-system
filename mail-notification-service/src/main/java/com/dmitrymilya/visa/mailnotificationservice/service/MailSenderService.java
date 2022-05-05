package com.dmitrymilya.visa.mailnotificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${application.mail.subject:Information about your visa application}")
    private String mailSubject;

    @Value("${application.mail.from:noreply@visa-system.ru}")
    private String mailFrom;

    @Value("${application.mail.personal:Visa System}")
    private String mailPersonal;

    public void sendEmail(String message, String recipientEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setSubject(mailSubject);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.setFrom(mailFrom, mailPersonal);
            mimeMessageHelper.setTo(recipientEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Got an error during writing the message: %s", e));
        }

        javaMailSender.send(mimeMessage);
    }

}
