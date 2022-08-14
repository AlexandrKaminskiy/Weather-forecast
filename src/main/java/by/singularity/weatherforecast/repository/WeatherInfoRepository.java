package by.singularity.weatherforecast.repository;

import by.singularity.weatherforecast.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo,Long> {
    WeatherInfo findByTown(String town);
}