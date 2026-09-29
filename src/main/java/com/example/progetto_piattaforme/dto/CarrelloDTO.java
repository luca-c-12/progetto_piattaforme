package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Carrello;
import com.example.progetto_piattaforme.entity.RecordCarrello;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record CarrelloDTO(
        long idCarrello,
        List<RecordCarrelloDTO> prodotti,
        int numeroArticoli,
        BigDecimal totale
) {
    public CarrelloDTO {
        prodotti = List.copyOf(prodotti);
    }

    public static CarrelloDTO from(Carrello c) {
        List<RecordCarrelloDTO> righe = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        int numeroArticoli = 0;

        for (RecordCarrello record : c.getProdotti()) {
            RecordCarrelloDTO riga = RecordCarrelloDTO.from(record);
            righe.add(riga);
            totale = totale.add(riga.subtotale());
            numeroArticoli += riga.quantita();
        }

        return new CarrelloDTO(c.getIdCarrello(), righe, numeroArticoli, totale);
    }
}
