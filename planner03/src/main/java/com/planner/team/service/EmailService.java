package com.planner.team.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sonji9510@gmail.com");   // 보내는 사람
        message.setTo(to);                        // 받는 사람
        message.setSubject(subject);              // 제목
        message.setText(body);                    // 본문

        mailSender.send(message);
    }
}
