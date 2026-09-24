package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Ruolo;
import com.example.progetto_piattaforme.entity.Utente;


import java.time.LocalDateTime;


public record UtenteDTO(
        long idUtente,
        String nome,
        String cognome,
        String email,
        Ruolo ruolo,
        LocalDateTime dataRegistrazione,
        boolean isVisible
) {
    public static UtenteDTO from(Utente utente) {

        return new UtenteDTO(
                utente.getIdUtente(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getRuolo(),
                utente.getDataRegistrazione(),
                utente.isVisible()
        );
    }
}
