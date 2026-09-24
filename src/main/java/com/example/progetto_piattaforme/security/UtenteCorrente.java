package com.example.progetto_piattaforme.security;

import com.example.progetto_piattaforme.exception.AccessoNonAutorizzatoException;
import com.example.progetto_piattaforme.repository.UtenteRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtenteCorrente {

    private static final String HEADER = "X-Id-Utente";

    private final HttpServletRequest richiesta;
    private final UtenteRepository utenteRepository;

    public long id() {
        long id = leggiId();
        if (!utenteRepository.existsByIdUtenteAndIsVisibleTrue(id))
            throw new AccessoNonAutorizzatoException("Utente inesistente o disattivato.");
        return id;
    }

    private long leggiId() {
        String valore = richiesta.getHeader(HEADER);
        if (valore == null || valore.isBlank())
            throw new AccessoNonAutorizzatoException("Utente non identificato.");
        try {
            return Long.parseLong(valore.trim());
        } catch (NumberFormatException e) {
            throw new AccessoNonAutorizzatoException("Utente non identificato.");
        }
    }
}
