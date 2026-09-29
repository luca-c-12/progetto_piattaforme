package com.example.progetto_piattaforme.security;

import com.example.progetto_piattaforme.entity.Ruolo;

import com.example.progetto_piattaforme.exception.AccessoNonAutorizzatoException;
import com.example.progetto_piattaforme.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtenteCorrente {

    private final UtenteService utenteService;


    public long id() {
        Authentication autenticazione = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticazione instanceof JwtAuthenticationToken tokenAutenticato))
            throw new AccessoNonAutorizzatoException("Utente non identificato.");

        Jwt jwt = tokenAutenticato.getToken();
        Ruolo ruolo;

        if (tokenAutenticato.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            ruolo = Ruolo.ADMIN;
        else
            ruolo = Ruolo.CLIENTE;

        String email = jwt.getClaimAsString("email");
        String nome = jwt.getClaimAsString("given_name");
        String cognome = jwt.getClaimAsString("family_name");
        String keycloakId = jwt.getClaimAsString("sub");

        try {
            return utenteService.trovaOCrea(keycloakId, email, nome, cognome, ruolo);
        } catch (DataIntegrityViolationException e) { //due richieste simultanee di GET per l'utente appena creato ed il suo carrello da parte del front end
            return utenteService.trovaOCrea(keycloakId, email, nome, cognome, ruolo);
        }
    }
}
