package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.PublicControllerService;
import net.engineeringdigest.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private PublicControllerService publicControllerService;



    @PostMapping("create-user")
    public void createUser(@RequestBody User user){
        //Not secure by using Spring security . anyone can create a new user
        user.setRole(Arrays.asList("ADMIN"));
        publicControllerService.createUserWithEncodedPassword(user);

    }


}

