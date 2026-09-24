package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.LineaOrdine;

import java.math.BigDecimal;

public record LineaOrdineDTO(
        long idLineaOrdine,
        Long codiceProdotto, // Long e non long: il prodotto potrebbe non esistere più
        String nomeProdotto,
        BigDecimal prezzoUnitario,
        int quantita,
        BigDecimal subtotale
) {
    public static LineaOrdineDTO from(LineaOrdine l) {
        Long codice = l.getProdotto() != null ? l.getProdotto().getCodiceProdotto() : null;
        // nome e prezzo presi dallo snapshot salvato nella linea, NON dal Prodotto attuale
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
