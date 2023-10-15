package com.example.entities;

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
    private Integer id;
    private Integer cityId;
    private Double temperature;
    private Date localDate;
}
