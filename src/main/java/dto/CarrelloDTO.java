package dto;

import entity.Carrello;
import entity.RecordCarrello;

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
        prodotti = List.copyOf(prodotti); // record davvero immutabile
    }

    public static CarrelloDTO from(Carrello c) {
        List<RecordCarrelloDTO> righe = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        int numeroArticoli = 0;

        for (RecordCarrello record : c.getProdotti()) {
            RecordCarrelloDTO riga = RecordCarrelloDTO.from(record);
            righe.add(riga);
            totale = totale.add(riga.subtotale()); // BigDecimal è immutabile: add restituisce un nuovo valore
            numeroArticoli += riga.quantita();
        }

        return new CarrelloDTO(c.getIdCarrello(), righe, numeroArticoli, totale);
    }
}
