package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.api.response.pojo.Email;
import net.engineeringdigest.JournalApp.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "Hey Nitish ! Your app is working fine :)";
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody Email email) {
        String response = emailSenderService.sendEmail(email.getTo(), email.getSubject(), email.getBody());
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error sending email", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

