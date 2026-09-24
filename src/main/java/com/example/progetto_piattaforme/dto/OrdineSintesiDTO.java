package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Ordine;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdineSintesiDTO(
        long idOrdine,
        LocalDateTime dataOrdine,
        BigDecimal totalePagato
) {
    public static OrdineSintesiDTO from(Ordine o) {
        return new OrdineSintesiDTO(
                o.getIdOrdine(),
                o.getDataOrdine(),
                o.getTotalePagato());
    }
}