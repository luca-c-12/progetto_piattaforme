package com.example.progetto_piattaforme.controller;

import com.example.progetto_piattaforme.dto.CarrelloDTO;
import com.example.progetto_piattaforme.dto.request.AggiungiProdottoRequest;
import com.example.progetto_piattaforme.dto.request.MergeCarrelloRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.CarrelloService;

@RestController
@RequiredArgsConstructor
public class CarrelloController {

    private final CarrelloService carrelloService;
    private final UtenteCorrente utenteCorrente;

    @GetMapping("/carrello")
    public CarrelloDTO getCarrello() {
        return carrelloService.getCarrello(utenteCorrente.id());
    }

    @PostMapping("/carrello/prodotti")
    public CarrelloDTO aggiungiProdotto(@RequestBody @Valid AggiungiProdottoRequest richiesta) {
        return carrelloService.aggiungiProdotto(utenteCorrente.id(), richiesta.codiceProdotto(), richiesta.quantita());
    }

    @PatchMapping("/carrello/prodotti/{codiceProdotto}")
    public CarrelloDTO aggiornaQuantita(@PathVariable long codiceProdotto, @RequestParam @PositiveOrZero int quantita) {
        return carrelloService.aggiornaQuantita(utenteCorrente.id(), codiceProdotto, quantita);
    }

    @DeleteMapping("/carrello/prodotti/{codiceProdotto}")
    public CarrelloDTO rimuoviProdotto(@PathVariable long codiceProdotto) {
        return carrelloService.rimuoviProdotto(utenteCorrente.id(), codiceProdotto);
    }

    @DeleteMapping("/carrello")
    public CarrelloDTO svuotaCarrello() {
        return carrelloService.svuotaCarrello(utenteCorrente.id());
    }

    @PostMapping("/carrello/merge")
    public CarrelloDTO unisciCarrelloOspite(@RequestBody @Valid MergeCarrelloRequest richiesta) {
        return carrelloService.unisciCarrelloOspite(utenteCorrente.id(), richiesta.righe());
    }
}
