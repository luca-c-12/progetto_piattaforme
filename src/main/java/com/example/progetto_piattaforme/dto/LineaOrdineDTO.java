package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.LineaOrdine;
import com.example.progetto_piattaforme.entity.Prodotto;

import java.math.BigDecimal;

public record LineaOrdineDTO(
        long idLineaOrdine,
        Long codiceProdotto, // Long e non long: il prodotto potrebbe non esistere più
        String nomeProdotto,
        BigDecimal prezzoUnitario,
        int quantita,
        BigDecimal subtotale,
        String urlImmagine   // dal prodotto attuale (null se eliminato)
) {
    public static LineaOrdineDTO from(LineaOrdine l) {
        Prodotto p = l.getProdotto();
        // nome e prezzo presi dallo snapshot salvato nella linea, NON dal Prodotto attuale
        return new LineaOrdineDTO(
                l.getIdLineaOrdine(),
                p != null ? p.getCodiceProdotto() : null,
                l.getNomeProdotto(),
                l.getPrezzoUnitario(),
                l.getQuantita(),
                l.getPrezzoUnitario().multiply(BigDecimal.valueOf(l.getQuantita())),
                p != null ? p.getUrlImmagine() : null
        );
    }
}