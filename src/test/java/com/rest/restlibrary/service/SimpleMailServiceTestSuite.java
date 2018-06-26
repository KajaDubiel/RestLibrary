package com.rest.restlibrary.service;

import com.rest.restlibrary.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class SimpleMailServiceTestSuite {

    @InjectMocks
    private SimpleEMailService simpleEMailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail(){
        //Given
        Mail mail = new Mail("test@gmail", "Test", "Test message", "testcc@gmail");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCC());

        //When
        simpleEMailService.send(mail);

        //Then
        Mockito.verify(javaMailSender, times(1)).send(mailMessage);
    }
}
