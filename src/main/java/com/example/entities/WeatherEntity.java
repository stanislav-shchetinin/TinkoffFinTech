package com.example.entities;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class WeatherEntity {
    private Integer id;
    private Integer cityId;
    private Double temperature;
    private Date localDate;
}
