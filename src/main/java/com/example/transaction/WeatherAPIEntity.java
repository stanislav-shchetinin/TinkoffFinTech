package com.example.transaction;

import com.example.entities.CityEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "weather_api")
public class WeatherAPIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CityEntity city;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public WeatherAPIEntity(CityEntity city, Double temperature, Timestamp timestamp){
        this.city = city;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

}
