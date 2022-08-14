package by.singularity.weatherforecast.controller;

import by.singularity.weatherforecast.model.WeatherInfo;
import by.singularity.weatherforecast.service.WeatherInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HelloController {
    final private WeatherInfoService weatherInfoService;


    @GetMapping("/{town}")
    public WeatherInfo showWeatherInTownInfo(@PathVariable @Valid String town){
        return weatherInfoService.getTemperatureOnTown(town);
    }
}
