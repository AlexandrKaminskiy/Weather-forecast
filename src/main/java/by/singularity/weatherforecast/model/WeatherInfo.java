package by.singularity.weatherforecast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather_info")
public class WeatherInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
    @Id
    @Length(min = 1)
    private String town;
    private Double temperature;
    private Date creationTime;

    @Override
    public String toString() {
        return "WeatherInfo{" +
                ", town='" + town + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}