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


    @Autowired
    private RedishTestService redisService;
    

    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("API_KEY", apiKey).replace("CITY", city);

        WeatherResponse getWeatherRedisData = redisService.getRedisData("city" + city, WeatherResponse.class);
        if(getWeatherRedisData != null){
            log.info("Weather data for city {} found in Redis cache", city);
            return getWeatherRedisData;
        }else {

//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.set("key","value");
//        User user =User.builder().userName("Niitish").password("Nitish").build();
//        HttpEntity<User> httpEntity=new HttpEntity<>(user,httpHeaders);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST,httpEntity , WeatherResponse.class);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            HttpStatus statusCode = response.getStatusCode();
            log.info("Status Code {}", statusCode);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.setRedisData("city"+city, body, 300L);
            }
            log.info("Response Body {},{}", body, response);
            return body;
        }
    }
}

