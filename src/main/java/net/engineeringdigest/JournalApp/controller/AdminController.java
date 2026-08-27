package net.engineeringdigest.JournalApp.controller;


import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUserEntry = userService.getAllUserEntry();
        if (allUserEntry != null && !allUserEntry.isEmpty()) {
            return new ResponseEntity<>(allUserEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
