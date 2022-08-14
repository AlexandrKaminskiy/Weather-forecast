package by.singularity.weatherforecast.serviceTask;

import by.singularity.weatherforecast.model.WeatherInfo;
import by.singularity.weatherforecast.repository.WeatherInfoRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SaveToDbHandler implements JavaDelegate {
    private final WeatherInfoRepository weatherInfoRepository;

    public SaveToDbHandler(WeatherInfoRepository weatherInfoRepository) {
        this.weatherInfoRepository = weatherInfoRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<?,?> weatherInfoOpt = (Map<?, ?>) delegateExecution.getVariable("weatherInfoMap");
        AtomicReference<WeatherInfo> weatherInfo = new AtomicReference<>();

        String name = (String) (weatherInfoOpt).get("name");
        Double temperature =(Double) ((Map<?,?>)(weatherInfoOpt).get("main")).get("temp");
        weatherInfo.set(new WeatherInfo(name, temperature, new Date()));
        weatherInfoRepository.save(weatherInfo.get());
        delegateExecution.setVariable("weatherInfo",weatherInfo.get());
    }
}
