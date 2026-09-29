package com.example.progetto_piattaforme.controller;


import com.example.progetto_piattaforme.dto.ClienteDTO;
import com.example.progetto_piattaforme.dto.UtenteDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProfiloRequest;
import com.example.progetto_piattaforme.dto.request.RicaricaSaldoRequest;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteCorrente utenteCorrente;

    @GetMapping("/utenti/me")
    @PreAuthorize("hasRole('CLIENTE')")
    public ClienteDTO getProfiloCliente() {
        return utenteService.getProfiloCliente(utenteCorrente.id());
    }

    @PutMapping("/utenti/me")
    @PreAuthorize("hasRole('CLIENTE')")
    public ClienteDTO aggiornaProfilo(@Valid @RequestBody AggiornaProfiloRequest richiesta) {
        return utenteService.aggiornaProfilo(utenteCorrente.id(), richiesta);
    }

    @PostMapping("/utenti/me/ricarica")
    @PreAuthorize("hasRole('CLIENTE')")
    public ClienteDTO ricaricaSaldo(@Valid @RequestBody RicaricaSaldoRequest richiesta) {
        return utenteService.ricaricaSaldo(utenteCorrente.id(), richiesta);
    }

    @DeleteMapping("/utenti/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CLIENTE')")
    public void disattivaAccount() {
        utenteService.disattivaUtente(utenteCorrente.id());
    }

    @GetMapping("/admin/utenti")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UtenteDTO> getUtenti(@PageableDefault(size = 20, sort = "cognome") Pageable paginazione) {
        return utenteService.getUtenti(paginazione);
    }

    @GetMapping("/admin/utenti/{idUtente}")
    @PreAuthorize("hasRole('ADMIN')")
    public UtenteDTO getUtente(@PathVariable long idUtente) {
        return utenteService.getUtente(idUtente);
    }
}
