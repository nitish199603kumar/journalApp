package net.engineeringdigest.JournalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "Hey Nitish ! Your app is working fine :)";
    }
}

