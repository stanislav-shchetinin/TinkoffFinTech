package com.example.entities;

import com.example.util.annotations.ID;
import com.example.util.annotations.NameColumn;
import com.example.util.annotations.NotTableColumn;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class WeatherEntity {
    @NotTableColumn
    public static final String TABLE_NAME = "weather";
    @ID
    private Integer id;
    @NameColumn(name="city_id")
    private Integer cityId;
    @NameColumn(name="temperature")
    private Double temperature;
    @NameColumn(name="date")
    private Date localDate;
    public WeatherEntity(Integer cityId, Double temperature, Date localDate){
        this.cityId = cityId;
        this.temperature = temperature;
        this.localDate = localDate;
    }
}
