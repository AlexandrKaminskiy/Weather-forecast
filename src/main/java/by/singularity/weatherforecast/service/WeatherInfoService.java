package by.singularity.weatherforecast.service;

import by.singularity.weatherforecast.model.WeatherInfo;
import by.singularity.weatherforecast.repository.WeatherInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WeatherInfoService {
    final private WeatherInfoRepository weatherInfoRepository;
    
    final private RestTemplate restTemplate;
    @Value("${api.url}")
    private String url;
    @Value("${api.key}")
    private String key;
    
    public WeatherInfo townWeatherInfo(String town) {
        return weatherInfoRepository.findByTown(town);
    }

    public WeatherInfo saveWeatherInfo(String town){
        Optional<?> weatherInfoMap = null;
        try {
            weatherInfoMap = Optional.ofNullable(restTemplate.getForObject(
                    url + "q=" + town + "&units=metric&APPID=" +
                            key, Map.class));
        } catch (HttpClientErrorException e) {
            return null;
        }
        AtomicReference<WeatherInfo> weatherInfo = new AtomicReference<>();
        weatherInfoMap.ifPresentOrElse(
                o -> {
                    String name = (String) ((Map<?,?>) o).get("name");
                    Double temperature =(Double) ((Map<?,?>)((Map<?,?>) o).get("main")).get("temp");
                    weatherInfo.set(new WeatherInfo(name, temperature, new Date()));
                    weatherInfoRepository.save(weatherInfo.get());
                }, () -> {});
        return weatherInfo.get();
    }
}
