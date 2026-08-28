package net.engineeringdigest.JournalApp.api.response.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

        private Current current;



    @Getter
    @Setter
    public class Current{
        @JsonProperty("temp_c")
        private double tempCelcious;

        @JsonProperty("temp_f")
        private double tempFarenhite;
    }






}
