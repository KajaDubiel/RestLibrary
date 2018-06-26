package com.rest.restlibrary.service;

import com.rest.restlibrary.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleEMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    JavaMailSender javaMailSender;

    public void send(Mail mail){
        try{
            LOGGER.info("Preparing email...");

            SimpleMailMessage simpleMailMessage = createMailMessage(mail);
            javaMailSender.send(simpleMailMessage);

            LOGGER.info("Email has been succesfully sent");

        } catch(MailException e){
            LOGGER.error("Failed to send email: " + e.getMessage());
        }
    }

    private SimpleMailMessage createMailMessage(Mail mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        Optional<String> cc = Optional.ofNullable(mail.getToCC());
        if(cc.isPresent()){
            simpleMailMessage.setCc(mail.getToCC());
        }
        return simpleMailMessage;
    }
}
