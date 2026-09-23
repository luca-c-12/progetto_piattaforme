package dto;

import entity.Ruolo;
import entity.Utente;


import java.time.LocalDateTime;


public record UtenteDTO(
        long idUtente,
        String nome,
        String cognome,
        String email,
        Ruolo ruolo,
        LocalDateTime dataRegistrazione
) {
    public static UtenteDTO from(Utente utente) {

        return new UtenteDTO(
                utente.getIdUtente(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getRuolo(),
                utente.getDataRegistrazione()
        );
    }
}
