package dto;

import entity.Utente;
import jakarta.persistence.DiscriminatorValue;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;

/**
 * Vista generica di un utente, indipendente dal ruolo (es. risposta al login, lista utenti per l'admin).
 */
public record UtenteDTO(
        long idUtente,
        String nome,
        String cognome,
        String email,
        String ruolo,
        LocalDateTime dataRegistrazione
) {
    public static UtenteDTO from(Utente u) {
        // unproxy: con un proxy Hibernate getClass() restituirebbe la classe del proxy, non Admin/Cliente
        Utente reale = (Utente) Hibernate.unproxy(u);
        String ruolo = reale.getClass().getAnnotation(DiscriminatorValue.class).value();
        return new UtenteDTO(
                reale.getIdUtente(),
                reale.getNome(),
                reale.getCognome(),
                reale.getEmail(),
                ruolo,
                reale.getDataRegistrazione()
        );
    }
}
