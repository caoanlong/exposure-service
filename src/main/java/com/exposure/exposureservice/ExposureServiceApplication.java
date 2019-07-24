package com.exposure.exposureservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan(basePackages = {"com.exposure.exposureservice.repository"})
public class ExposureServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExposureServiceApplication.class, args);
    }
}
