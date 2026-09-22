package dto;

import entity.Prodotto;
import entity.RecordCarrello;

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
                p.getQuantita() >= r.getQuantita() // la quantità richiesta è ancora in magazzino?
        );
    }
}
