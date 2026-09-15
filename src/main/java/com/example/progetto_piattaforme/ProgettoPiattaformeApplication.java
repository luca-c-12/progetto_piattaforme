package com.example.progetto_piattaforme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgettoPiattaformeApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ProgettoPiattaformeApplication.class, args);
        // Stampa la versione di Java
        System.out.println("Versione Java: " + System.getProperty("java.version"));

        // Stampa il fornitore del JDK/JRE (es. Oracle, OpenJDK)
        System.out.println("Vendor JDK: " + System.getProperty("java.vendor"));

        // Stampa la cartella di installazione del JDK/JRE
        System.out.println("Percorso JDK: " + System.getProperty("java.home"));
    }

}
