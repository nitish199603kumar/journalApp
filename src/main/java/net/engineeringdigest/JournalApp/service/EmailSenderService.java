package net.engineeringdigest.JournalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.config.EmailConfig;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
@Slf4j
public class EmailSenderService {


//    @Autowired
//    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private EmailConfig emailConfig;
    //create logger instance


    public String sendEmail(String to, String subject, String body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailConfig.javaMailSender().send(message);
//            return "Email sent successfully to " + to + " by user " + userName;
            return "Email sent successfully to " + to ;
        } catch (Exception e) {
            log.error("Error sending email {} "+ e.getMessage(), e);
//            return "Error sending email to " + to + " by user " + userName;
        }
        return null;
    }


}
