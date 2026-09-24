package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Ordine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrdineDTO(
        long idOrdine,
        long idCliente,
        LocalDateTime dataOrdine,
        BigDecimal totalePagato,
        String indirizzoSpedizione,
        List<LineaOrdineDTO> prodottiAcquistati
) {
    public OrdineDTO {
        prodottiAcquistati = List.copyOf(prodottiAcquistati);
    }

    public static OrdineDTO from(Ordine o) {
        return new OrdineDTO(
                o.getIdOrdine(),
                o.getCliente().getIdUtente(),
                o.getDataOrdine(),
                o.getTotalePagato(),
                o.getIndirizzoSpedizione(),
                o.getProdottiAcquistati().stream().map(LineaOrdineDTO::from).toList()
        );
    }
}
