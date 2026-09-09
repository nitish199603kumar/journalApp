package net.engineeringdigest.JournalApp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.JournalApp.api.response.pojo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedishTestService {

    @Autowired
    private  RedisTemplate redisTemplate;

    public <T> T getRedisData(String key, Class<T> entityClass) {
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(obj.toString(), entityClass);

        } catch (Exception e) {
            log.error("Error getting data from redis {} " + e.getMessage(), e);
            return null;
        }
    }
        public void setRedisData(String key,Object o,Long ttl){
            try{

                ObjectMapper objectMapper = new ObjectMapper();
                String s = objectMapper.writeValueAsString(o);
                redisTemplate.opsForValue().set(key,s,ttl, TimeUnit.SECONDS);

            }catch (Exception e){
                log.error("Error getting to save the data in redis {} "+ e.getMessage(), e);
            }


        }
}
