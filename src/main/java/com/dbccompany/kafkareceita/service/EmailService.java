package com.dbccompany.kafkareceita.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMessage(String mailTo, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mailTo);
        message.setSubject("TESTE");
        message.setText(msg);
        emailSender.send(message);
    }
}