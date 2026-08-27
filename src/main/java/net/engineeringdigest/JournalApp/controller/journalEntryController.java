//package net.engineeringdigest.JournalApp.controller;
//
//import net.engineeringdigest.JournalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journalApp")
//public class journalEntryController {
//
//    private Map<Long, JournalEntry> journalEntries=new HashMap<>();
//
//    @GetMapping()
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping()
//    public boolean createEntry(@RequestBody JournalEntry journalEntry){
//      journalEntries.put(journalEntry.getId(),journalEntry);
//      return true;
//    }
//
//    @GetMapping("id/{getById}")
//    public JournalEntry getJournalEntryById(@PathVariable Long getById ){
//        return journalEntries.get(getById);
//    }
//
//    @DeleteMapping("/id/{getById}")
//    public JournalEntry deleteJournalEntryById(@PathVariable Long getById){
//        return journalEntries.remove(getById);
//    }
//
//    @PutMapping("id/{id}")
//    public JournalEntry updateJournalById(@PathVariable Long id,@RequestBody JournalEntry journalEntry){
//        return journalEntries.put(id,journalEntry);
//    }
//}
