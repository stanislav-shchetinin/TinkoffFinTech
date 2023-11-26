package com.example.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerSchedulerService {
    private final static String CRON = "*/5 * * * * *";
    private final WeatherAPIService weatherAPIService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${scheduler.cities}")
    private String[] arrayCity;

    @Value("${scheduler.break}")
    private int breakTime;
    @Scheduled(cron = CRON)
    @SneakyThrows
    public void scheduler() {
        for (String city : arrayCity){
            getWeather(city);
            log.info("complete {}", city);
            Thread.sleep(breakTime);
        }
    }
    public void getWeather(String city){
        Map<String, Object> map = weatherAPIService.getWeather(city);
        Map<String, Object> current = (Map<String, Object>) map.get("current");
        BigDecimal temperature = (BigDecimal) current.get("temp_c");
        kafkaTemplate.send("my-topic", city, temperature.toString())
                .whenComplete((result, ex) -> {
                    if (ex == null){
                        log.info("good job!");
                    } else {
                        log.error(ex.getMessage());
                    }
                });
        log.info(temperature.toString());
    }

}
