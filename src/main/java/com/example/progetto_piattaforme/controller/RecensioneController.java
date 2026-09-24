package com.example.progetto_piattaforme.controller;


import com.example.progetto_piattaforme.dto.RecensioneDTO;
import com.example.progetto_piattaforme.dto.request.RecensioneRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.RecensioneService;

@RestController
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final UtenteCorrente utenteCorrente;

    @GetMapping("/prodotti/{codiceProdotto}/recensioni")
    public Page<RecensioneDTO> getRecensioniProdotto(@PathVariable long codiceProdotto,
                                                     @PageableDefault(size = 10, sort = "dataRecensione", direction = Sort.Direction.DESC) Pageable paginazione) {
        return recensioneService.getRecensioniProdotto(codiceProdotto, paginazione);
    }

    @PostMapping("/prodotti/{codiceProdotto}/recensioni")
    @ResponseStatus(HttpStatus.CREATED)
    public RecensioneDTO scriviRecensione(@PathVariable long codiceProdotto, @RequestBody @Valid RecensioneRequest richiesta) {
        return recensioneService.scriviRecensione(utenteCorrente.id(), codiceProdotto, richiesta);
    }

    @GetMapping("/recensioni/mie")
    public Page<RecensioneDTO> getRecensioniCliente(
            @PageableDefault(size = 10, sort = "dataRecensione", direction = Sort.Direction.DESC) Pageable paginazione) {
        return recensioneService.getRecensioniCliente(utenteCorrente.id(), paginazione);
    }

    @PutMapping("/recensioni/{idRecensione}")
    public RecensioneDTO modificaRecensione(@PathVariable long idRecensione, @RequestBody @Valid RecensioneRequest richiesta) {
        return recensioneService.modificaRecensione(utenteCorrente.id(), idRecensione, richiesta);
    }

    @DeleteMapping("/recensioni/{idRecensione}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaRecensione(@PathVariable long idRecensione) {
        recensioneService.eliminaRecensione(utenteCorrente.id(), idRecensione);
    }

    @DeleteMapping("/admin/recensioni/{idRecensione}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaRecensioneComeAdmin(@PathVariable long idRecensione) {
        recensioneService.eliminaRecensioneComeAdmin(idRecensione);
    }
}
