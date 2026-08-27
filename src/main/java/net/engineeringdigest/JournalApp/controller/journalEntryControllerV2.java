package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.JournalEntryService;
import net.engineeringdigest.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journalApp")
public class journalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("all-journalEntry")
    public ResponseEntity<?> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byUserName = userService.findByUserName(username);
//        List<JournalEntry> allEntry = journalEntryService.getAllEntry();
        List<JournalEntry> allEntry = byUserName.getJournalEntries();
        try{
            return new ResponseEntity<>(allEntry,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Catch Block");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create-journalEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntry.setDate(LocalDateTime.now());
            System.out.println("before calling the journalSaveEntry");
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("journalEntryById/{getById}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId getById ){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println("username===>" +username);
            User user = userService.findByUserName(username);
            System.out.println("user===> " +user);
            List<JournalEntry> collect = user.getJournalEntries().stream()
                    .filter(x -> x.getId().equals(getById))
                    .collect(Collectors.toList());
            System.out.println("collect===> " +collect.size() +" isEmpty Result :- "+collect.isEmpty());
            if (collect != null && !collect.isEmpty()) {
               JournalEntry journalEntryById = journalEntryService.getJournalEntryById(getById);
                System.out.println("inside getJournalEntryById if stm" +journalEntryById);
                return new ResponseEntity<>(journalEntryById, HttpStatus.OK);
            }
         }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/journalEntryId/{getById}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId getById){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean deletedResult = journalEntryService.deleteJournalEntryById(getById, username);
        if(deletedResult){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("journalEntryId/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry updatedJournalEntry){


        JournalEntry journalEntry = journalEntryService.updateJournalEntryById(id, updatedJournalEntry);
        if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
