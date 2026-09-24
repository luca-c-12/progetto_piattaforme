package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.entity.RecordCarrello;

import java.math.BigDecimal;

public record RecordCarrelloDTO(
        long idRecordCarrello,
        long codiceProdotto,
        String nomeProdotto,
        BigDecimal prezzoUnitario,
        int quantita,
        BigDecimal subtotale,
        boolean disponibile
) {
    public static RecordCarrelloDTO from(RecordCarrello r) {
        Prodotto p = r.getProdotto();
        BigDecimal subtotale = p.getPrezzo().multiply(BigDecimal.valueOf(r.getQuantita()));
        return new RecordCarrelloDTO(
                r.getIdRecordCarrello(),
                p.getCodiceProdotto(),
                p.getNome(),
                p.getPrezzo(),
                r.getQuantita(),
                subtotale,
                p.isVisible() && p.getQuantita() >= r.getQuantita()
        );
    }
}
