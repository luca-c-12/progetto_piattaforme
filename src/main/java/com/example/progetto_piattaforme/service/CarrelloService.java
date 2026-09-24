package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.CarrelloDTO;
import com.example.progetto_piattaforme.dto.request.RigaOspiteRequest;

import java.util.List;

public interface CarrelloService {

    CarrelloDTO getCarrello(long idCliente);

    CarrelloDTO aggiungiProdotto(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO rimuoviProdotto(long idCliente, long codiceProdotto);

    CarrelloDTO svuotaCarrello(long idCliente);

    CarrelloDTO unisciCarrelloOspite(long idCliente, List<RigaOspiteRequest> righeOspite);
}
