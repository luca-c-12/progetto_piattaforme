package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import com.example.progetto_piattaforme.entity.Prodotto;

import java.math.BigDecimal;

public record ProdottoDTO(
        long codiceProdotto,
        String nome,
        String descrizione,
        String brand,
        BigDecimal prezzo,
        CategoriaProdotto categoria,
        boolean disponibile,
        String urlImmagine
) {
    public static ProdottoDTO from(Prodotto p) {
        return new ProdottoDTO(
                p.getCodiceProdotto(),
                p.getNome(),
                p.getDescrizione(),
                p.getBrand(),
                p.getPrezzo(),
                p.getCategoria(),
                p.isInCatalogo(),
                p.getUrlImmagine()
        );
    }
}
