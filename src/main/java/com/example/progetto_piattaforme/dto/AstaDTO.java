package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Asta;
import com.example.progetto_piattaforme.entity.OffertaAsta;
import com.example.progetto_piattaforme.entity.StatoAsta;

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
        StatoAsta stato,
        boolean chiusa,
        String vincitore              // null finché l'asta non è chiusa, o se nessuno poteva pagare
) {
    public static AstaDTO from(Asta a) {
        OffertaAsta max = a.getOffertaMax();
        OffertaAstaDTO offertaMassima = max != null ? OffertaAstaDTO.from(max) : null;
        BigDecimal prezzoCorrente = max != null ? max.getImportoOfferta() : a.getPrezzoBase();
        String vincitore = a.getVincitore() != null ? OffertaAstaDTO.nomePubblico(a.getVincitore()) : null;
        return new AstaDTO(
                a.getIdAsta(),
                ProdottoDTO.from(a.getProdotto()),
                a.getDataInizio(),
                a.getDataFine(),
                a.getPrezzoBase(),
                offertaMassima,
                prezzoCorrente,
                a.statoAl(LocalDateTime.now()),
                a.isChiusa(),
                vincitore
        );
    }
}
