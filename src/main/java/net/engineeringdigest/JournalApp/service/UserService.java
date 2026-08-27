package net.engineeringdigest.JournalApp.service;

import net.engineeringdigest.JournalApp.Repository.UserRepository;
import net.engineeringdigest.JournalApp.config.SpringSecurity;
import net.engineeringdigest.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringSecurity springSecurity;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveUserEntry(User user){
        System.out.println("before save journal entry in userdb");
        userRepository.save(user);
    }

    public void createUserWithEncodedPassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAllUserEntry(){
        return userRepository.findAll();
    }

    public User getUserEntryById(ObjectId id){
        return userRepository.findById(id).get();
    }

    public void deleteUserEntryById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User updateUserDetails(User userNewData, String userid){
        User userInDb = userRepository.findByUserName(userid);
        if(userInDb !=null){
            userInDb.setUserName(userNewData.getUserName());
            userInDb.setPassword(userNewData.getPassword());
            userRepository.save(userInDb);
        }
        return userInDb;
    }

    public User updateUserDetails(User userNewData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userRepository.findByUserName(userName);
        if(userInDb !=null){
            userInDb.setUserName(userNewData.getUserName());
            userInDb.setPassword(userNewData.getPassword());
            createUserWithEncodedPassword(userInDb);
        }
        return userInDb;
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deleteUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
    }





//    public User updateUserEntryById(ObjectId id,JournalEntry updatedJournalEntry){
////        User oldUserEntry =null;
////        try{
////
////            oldUserEntry=  userRepository.findById(id).orElse(null);
////            if(oldUserEntry !=null){
////                oldUserEntry.setTitle(updatedJournalEntry.getTitle()!=null && !updatedJournalEntry.getTitle().equals("") ?updatedJournalEntry.getTitle(): oldJournalEntry.getTitle());
////                oldUserEntry.setContent(updatedJournalEntry.getContent()!=null && !updatedJournalEntry.getContent().equals("")? updatedJournalEntry.getContent() : oldJournalEntry.getContent());
////                oldUserEntry.save(oldJournalEntry);
////                return oldUserEntry;
////            }
////        }catch (Exception e){
////
////        }
////        return oldUserEntry;
//        return null;
//    }
}
