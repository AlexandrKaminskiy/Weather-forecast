package by.singularity.weatherforecast.serviceTask;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


public class MakeRequestHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<?,?> weatherInfoMap = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = (String) delegateExecution.getVariable("url");
            String partOfPath = (String) delegateExecution.getVariable("partOfPath");
            String key = (String) delegateExecution.getVariable("key");
            weatherInfoMap = restTemplate.getForObject(
                    url + "q=" + delegateExecution.getVariable("town") + partOfPath +
                            key, Map.class);
        } catch (HttpClientErrorException e) {
            delegateExecution.setVariable("weatherInfoMap",Optional.empty());
            return;
        }
        delegateExecution.setVariable("weatherInfoMap",weatherInfoMap);
    }
}
