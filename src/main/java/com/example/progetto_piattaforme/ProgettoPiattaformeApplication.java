package com.example.progetto_piattaforme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class ProgettoPiattaformeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgettoPiattaformeApplication.class, args);
    }
}
