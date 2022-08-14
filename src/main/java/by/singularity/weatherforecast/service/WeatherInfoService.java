package by.singularity.weatherforecast.service;

import by.singularity.weatherforecast.model.WeatherInfo;
import by.singularity.weatherforecast.repository.WeatherInfoRepository;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.TaskInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherInfoService {
    final private WeatherInfoRepository weatherInfoRepository;
    final private RuntimeService runtimeService;
    final private TaskService taskService;
    final private RepositoryService repositoryService;
    @Value("${api.url}")
    private String url;
    @Value("${api.key}")
    private String key;
    @Value("${api.partofpath}")
    private String partOfPath;

    @PostConstruct
    public void runProcess() {
        Deployment deployment =
                repositoryService
                        .createDeployment()
                        .addClasspathResource("weather-forecast-flow.bpmn20.xml")
                        .deploy();
        runtimeService.startProcessInstanceByKey("weather-forecast-flow");
    }

    public WeatherInfo getTemperatureOnTown(String town) {
        String s = taskService.createTaskQuery().list().get(0).getId();
        taskService.setVariable(s,"town", town);
        taskService.setVariable(s,"key",key);
        taskService.setVariable(s,"partOfPath",partOfPath);
        taskService.setVariable(s,"url",url);
        taskService.complete(s);
        return (WeatherInfo) taskService.getVariable(s, "weatherInfo");
    }
    @PreDestroy
    private void clearTaskHistory(){
        runtimeService.deleteProcessInstance("weather-forecast-flow"," ");
        taskService.deleteTasks(taskService.createTaskQuery().list().stream().map(TaskInfo::getId).collect(Collectors.toList()));
    }

}
