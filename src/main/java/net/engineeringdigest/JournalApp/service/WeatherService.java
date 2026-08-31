package net.engineeringdigest.JournalApp.service;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.api.response.pojo.WeatherResponse;
import net.engineeringdigest.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String API;



//    private static final String API="https://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";

//    private static final String apiKey="4c2650fe365c4f1a97e180012262708";

    @Autowired
    private RestTemplate restTemplate;

// write a method which will take city name as input and return the weather report of that city using the above API and apiKey
    

    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("API_KEY", apiKey).replace("CITY", city);

//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.set("key","value");
//        User user =User.builder().userName("Niitish").password("Nitish").build();
//        HttpEntity<User> httpEntity=new HttpEntity<>(user,httpHeaders);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST,httpEntity , WeatherResponse.class);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        HttpStatus statusCode = response.getStatusCode();
        log.info("Status Code {}",statusCode);
        WeatherResponse body = response.getBody();
        log.info("Response Body {} {}",body,response);
        return body;
    }
}

