package com.example.entities;

import com.example.util.annotations.ID;
import com.example.util.annotations.NameColumn;
import com.example.util.annotations.NotTableColumn;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "weather")
public class WeatherEntity {
    @NotTableColumn //my annotation
    @Transient
    public static final String TABLE_NAME = "weather";

    @ID //my annotation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NameColumn(name="city_id") //my annotation
    @Column(name = "city_id")
    private Integer cityId;

    @NameColumn(name="temperature") //my annotation
    @Column(name = "temperature")
    private Double temperature;

    @NameColumn(name="date") //my annotation
    @Column(name = "date")
    private Date localDate;
    public WeatherEntity(Integer cityId, Double temperature, Date localDate){
        this.cityId = cityId;
        this.temperature = temperature;
        this.localDate = localDate;
    }
}
