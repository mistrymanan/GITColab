package com.gitcolab.utilities;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    EmailSender() {}

    public boolean sendEmail(String emailTo, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@gitcolab.com");
        msg.setTo(emailTo);
        msg.setSubject(subject);
        msg.setText(message);
        try {
            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
