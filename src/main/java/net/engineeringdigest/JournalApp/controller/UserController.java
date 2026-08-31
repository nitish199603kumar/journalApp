package net.engineeringdigest.JournalApp.controller;

import net.engineeringdigest.JournalApp.api.response.pojo.WeatherResponse;
import net.engineeringdigest.JournalApp.entity.User;
import net.engineeringdigest.JournalApp.service.UserService;
import net.engineeringdigest.JournalApp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

   private static final Logger log= LoggerFactory.getLogger(UserController.class);

   @Autowired
   private UserService userService;

   @Autowired
   private WeatherService weatherService;

   @GetMapping
   public List<User> getAllUsers(){
      List<User> allUserEntry = userService.getAllUserEntry();
      return allUserEntry;
   }



   @PutMapping("update-user")
   //Here user controller is authenticated by password .
   // To update the password you must have the enter old password in authorization and whatever you want to update
   //pass in to updated(password) details.
   //Then after you enter old password in authorization you will get unauthorized.Se you have to enter new
   // updated password in authorization.
   public ResponseEntity<?> updateUser(@RequestBody User user){
      User user1 = userService.updateUserDetails(user);
      return new ResponseEntity<>(user1,HttpStatus.OK);
   }

   @DeleteMapping("delete-user")
   public ResponseEntity<?> deleteUser(){
      userService.deleteUserByUsername();
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

   }

   @GetMapping("/weather-report")
   public ResponseEntity<?> getWeatherReport()
   {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
      log.info("weatherResponse {}",weatherResponse);
      String greetings="";
      if(weatherResponse!=null){
         greetings="Hi " + authentication.getName() + ", Your city temp is :" + weatherResponse.getCurrent().getTempCelcious();
         return new ResponseEntity<>(greetings , HttpStatus.OK);
      }
      return new ResponseEntity<>("Hi : " + authentication.getName(), HttpStatus.NOT_FOUND);
   }


   }



