package net.engineeringdigest.JournalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.Repository.JournalAppRepository;
import net.engineeringdigest.JournalApp.entity.JournalEntry;
import net.engineeringdigest.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalAppRepository journalAppRepository;

    @Autowired
    private UserService userService;

   private static final Logger Logger= LoggerFactory.getLogger(JournalEntryService.class);

    JournalEntryService journalEntryServiceObj =null;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username){

        try {
            User getUserData = userService.findByUserName(username);
            System.out.println("before saving journal in journalapp db");
            JournalEntry savedJournalEntry = journalAppRepository.save(journalEntry);
            getUserData.getJournalEntries().add(savedJournalEntry);
//            getUserData.setUserName(null);
            userService.saveUserEntry(getUserData);
        }catch (Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the data");
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalAppRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry(){
        return journalAppRepository.findAll();
    }

    public JournalEntry getJournalEntryById(ObjectId id){
        return journalAppRepository.findById(id).get();
    }

    public boolean deleteJournalEntryById(ObjectId id, String username){
        boolean removed=false;
        try{
            User byUserName = userService.findByUserName(username);
            boolean remove = byUserName.getJournalEntries().removeIf(x -> x.getId().equals(id));
//        List<JournalEntry> journalEntries= byUserName.getJournalEntries();
//        if(journalEntries!=null){
//            journalEntries.remove(journalEntries);
//            userService.saveUserEntry(byUserName);
//        }
            if(remove){
                userService.saveUserEntry(byUserName);
                journalAppRepository.deleteById(id);
                removed=true;
            }

        }catch (Exception e){
            Logger.info("catch block " ,e);
            throw new RuntimeException("An error occurred while deleting the entry");
        }

        return removed;
    }

    public JournalEntry updateJournalEntryById(ObjectId id,JournalEntry updatedJournalEntry){

        JournalEntry oldJournalEntry =null;
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println("UserName from update journaldata ===>" +username);
            User user = userService.findByUserName(username);
            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
            System.out.println("matching user id" +collect);
            if(!collect.isEmpty()){
                oldJournalEntry=  journalAppRepository.findById(id).orElse(null);
                if(oldJournalEntry !=null){
                    oldJournalEntry.setTitle(updatedJournalEntry.getTitle()!=null && !updatedJournalEntry.getTitle().equals("") ?updatedJournalEntry.getTitle(): oldJournalEntry.getTitle());
                    oldJournalEntry.setContent(updatedJournalEntry.getContent()!=null && !updatedJournalEntry.getContent().equals("")? updatedJournalEntry.getContent() : oldJournalEntry.getContent());
                    saveEntry(oldJournalEntry);
                    return oldJournalEntry;
                }
            }
        }catch (Exception e){
            System.out.println("Inside Catch block (update journalAppDetails)");
            e.printStackTrace();
        }
        System.out.println("update details ===> " +oldJournalEntry);
        return oldJournalEntry;
    }
}
