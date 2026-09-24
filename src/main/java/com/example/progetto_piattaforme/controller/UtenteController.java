package com.example.progetto_piattaforme.controller;


import com.example.progetto_piattaforme.dto.ClienteDTO;
import com.example.progetto_piattaforme.dto.UtenteDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProfiloRequest;
import com.example.progetto_piattaforme.dto.request.RegistrazioneAdminRequest;
import com.example.progetto_piattaforme.dto.request.RegistrazioneClienteRequest;
import com.example.progetto_piattaforme.dto.request.RicaricaSaldoRequest;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final UtenteCorrente utenteCorrente;

    @PostMapping("/utenti")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO registraCliente(@Valid @RequestBody RegistrazioneClienteRequest richiesta) {
        return utenteService.registraCliente(richiesta);
    }

    @GetMapping("/utenti/me")
    public ClienteDTO getProfiloCliente() {
        return utenteService.getProfiloCliente(utenteCorrente.id());
    }

    @PutMapping("/utenti/me")
    public ClienteDTO aggiornaProfilo(@Valid @RequestBody AggiornaProfiloRequest richiesta) {
        return utenteService.aggiornaProfilo(utenteCorrente.id(), richiesta);
    }

    @PostMapping("/utenti/me/ricarica")
    public ClienteDTO ricaricaSaldo(@Valid @RequestBody RicaricaSaldoRequest richiesta) {
        return utenteService.ricaricaSaldo(utenteCorrente.id(), richiesta);
    }

    @GetMapping("/admin/utenti")
    public Page<UtenteDTO> getUtenti(@PageableDefault(size = 20, sort = "cognome") Pageable paginazione) {
        return utenteService.getUtenti(paginazione);
    }

    @GetMapping("/admin/utenti/{idUtente}")
    public UtenteDTO getUtente(@PathVariable long idUtente) {
        return utenteService.getUtente(idUtente);
    }

    @PostMapping("/admin/utenti")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteDTO creaAdmin(@Valid @RequestBody RegistrazioneAdminRequest richiesta) {
        return utenteService.creaAdmin(richiesta);
    }

    @DeleteMapping("/admin/utenti/{idUtente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disattivaUtente(@PathVariable long idUtente) {
        utenteService.disattivaUtente(idUtente);
    }
}
