package dto;

import entity.CategoriaProdotto;

import java.math.BigDecimal;

public record ProdottoDTO(
        long codiceProdotto,
        String nome,
        String descrizione,
        String brand,
        BigDecimal prezzo,
        CategoriaProdotto categoria,
        boolean disponibile
) {}
