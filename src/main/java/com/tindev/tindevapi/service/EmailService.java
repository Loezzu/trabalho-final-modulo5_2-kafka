package com.tindev.tindevapi.service;

import com.tindev.tindevapi.dto.user.UserDTO;
import com.tindev.tindevapi.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String tindev_mail;

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(UserEntity user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(tindev_mail);
        message.setTo(user.getPersonInfoEntity().getEmail());
        message.setSubject("Vire PRO hoje mesmo!");
        message.setText("Ola " + user.getPersonInfoEntity().getRealName() +"!"+"\n\nAproveite esse momento para virar PRO e garanta todas as vantagens!");
        emailSender.send(message);
    }

}
