package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Recensione;

import java.time.LocalDateTime;

public record RecensioneDTO(
        long idRecensione,
        long codiceProdotto,
        long idCliente,
        String autore,
        int voto,
        String testo,
        LocalDateTime dataRecensione
) {
    public static RecensioneDTO from(Recensione r) {
        return new RecensioneDTO(
                r.getIdRecensione(),
                r.getProdotto().getCodiceProdotto(),
                r.getCliente().getIdUtente(),
                OffertaAstaDTO.nomePubblico(r.getCliente()),
                r.getVoto().getValue(),
                r.getTesto(),
                r.getDataRecensione()
        );
    }
}
