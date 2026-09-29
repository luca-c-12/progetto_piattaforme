package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.LineaOrdine;

import java.math.BigDecimal;

public record LineaOrdineDTO(
        long idLineaOrdine,
        Long codiceProdotto, // si pone a null se il prodotto non esiste
        String nomeProdotto,
        BigDecimal prezzoUnitario,
        int quantita,
        BigDecimal subtotale
) {
    public static LineaOrdineDTO from(LineaOrdine l) {
        Long codice = l.getProdotto() != null ? l.getProdotto().getCodiceProdotto() : null;
        // nome e prezzo presi dallo snapshot salvato nella linea e NON dal prodotto attuale
        return new LineaOrdineDTO(
                l.getIdLineaOrdine(),
                codice,
                l.getNomeProdotto(),
                l.getPrezzoUnitario(),
                l.getQuantita(),
                l.getPrezzoUnitario().multiply(BigDecimal.valueOf(l.getQuantita()))
        );
    }
}
