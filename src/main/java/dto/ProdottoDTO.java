package dto;

import entity.CategoriaProdotto;
import entity.Prodotto;

import java.math.BigDecimal;

public record ProdottoDTO(
        long codiceProdotto,
        String nome,
        String descrizione,
        String brand,
        BigDecimal prezzo,
        CategoriaProdotto categoria,
        boolean disponibile
) {
    public static ProdottoDTO from(Prodotto p) {
        return new ProdottoDTO(
                p.getCodiceProdotto(),
                p.getNome(),
                p.getDescrizione(),
                p.getBrand(),
                p.getPrezzo(),
                p.getCategoria(),
                p.isAvailable()
        );
    }
}
