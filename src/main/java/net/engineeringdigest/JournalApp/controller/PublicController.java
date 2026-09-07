package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.EmailSenderService;
import net.engineeringdigest.JournalApp.service.PublicControllerService;
import net.engineeringdigest.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/public")

public class PublicController {

    //create Logger instance
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PublicController.class);


    @Autowired
    private PublicControllerService publicControllerService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;



    @PostMapping("create-user")
    public void createUser(@RequestBody User user){
        logger.info("Creating user for Username :- {}", user.getUserName());
        user.setRole(Arrays.asList("ADMIN"));
        publicControllerService.createUserWithEncodedPassword(user);

    }

//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public String userScheduler() {
        List<User> allUserEntry = userService.getAllUserEntry();
        logger.info("Total number of users in the database: {}", allUserEntry.size());
        for (User user : allUserEntry) {
            String userName = user.getUserName();
            String email = user.getEmail();
            String sentimentalAnalysis = user.getSentimentalAnalysis();
            List<JournalEntry> journalEntries = user.getJournalEntries();
            String title ="";
            String content ="";
            for (JournalEntry journalEntry : journalEntries) {
                title = title + journalEntry.getTitle() +"|";
                content= content+journalEntry.getContent() +"|";
            }
            //add title list in one string with comma separated dont use stream api
            logger.info("UserName: {}, Email: {}, SentimentalAnalysis: {}, Title: {}, Content: {}", userName, email, sentimentalAnalysis, title, content);
            if(email!=null && !email.isEmpty()){
                emailSenderService.sendEmail(email,title,content);
            }
        }
        return "User Scheduler is working fine";
    }

    }

