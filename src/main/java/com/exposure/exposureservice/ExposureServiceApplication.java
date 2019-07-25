package com.exposure.exposureservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan(basePackages = {"com.exposure.exposureservice.repository"})
@EnableScheduling
public class ExposureServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExposureServiceApplication.class, args);
    }
}
