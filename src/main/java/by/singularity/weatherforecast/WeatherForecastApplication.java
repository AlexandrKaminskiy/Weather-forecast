package by.singularity.weatherforecast;

import by.singularity.weatherforecast.service.WeatherInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class WeatherForecastApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastApplication.class, args);
    }

}
