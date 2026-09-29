package com.example.progetto_piattaforme.popolamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdottoDummyJson(
        String title,
        String description,
        BigDecimal price,
        int stock,
        String brand,
        String category,
        String thumbnail
) {}
