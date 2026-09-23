package dto;

import entity.Asta;
import entity.OffertaAsta;
import entity.StatoAsta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AstaDTO(
        long idAsta,
        ProdottoDTO prodotto,
        LocalDateTime dataInizio,
        LocalDateTime dataFine,
        BigDecimal prezzoBase,
        OffertaAstaDTO offertaMassima, // null se non ci sono ancora offerte
        BigDecimal prezzoCorrente,
        StatoAsta stato
) {
    public static AstaDTO from(Asta a) {
        OffertaAsta max = a.getOffertaMax();
        OffertaAstaDTO offertaMassima = max != null ? OffertaAstaDTO.from(max) : null;
        BigDecimal prezzoCorrente = max != null ? max.getImportoOfferta() : a.getPrezzoBase();
        return new AstaDTO(
                a.getIdAsta(),
                ProdottoDTO.from(a.getProdotto()),
                a.getDataInizio(),
                a.getDataFine(),
                a.getPrezzoBase(),
                offertaMassima,
                prezzoCorrente,
                a.statoAl(LocalDateTime.now())
        );
    }
}
