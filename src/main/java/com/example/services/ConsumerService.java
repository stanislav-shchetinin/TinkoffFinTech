package com.example.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
@Slf4j
public class ConsumerService {

    private final Queue<Double> queue = new ArrayDeque<>();
    private double curResult = 0.;

    @Value("${spring.kafka.queue.size}")
    private int maxSize;

    @KafkaListener(topics = "my-topic")
    public void listen(String message) {
        double temp = Double.parseDouble(message);
        log.info("get message: {}", message);

        if (queue.size() == maxSize && maxSize != 0){
            Double head = queue.poll();
            curResult -= head/maxSize;
            curResult += temp/maxSize;
            queue.add(temp);
        } else {
            queue.add(temp);
            curResult *= (queue.size() - 1.)/queue.size();
            curResult += temp/queue.size();
        }

        System.out.println("Average value: " + curResult);

    }
}
