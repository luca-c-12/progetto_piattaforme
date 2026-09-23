package service;

import dto.CarrelloDTO;

public interface CarrelloService {

    CarrelloDTO getCarrello(long idCliente);

    CarrelloDTO aggiungiProdotto(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO rimuoviProdotto(long idCliente, long codiceProdotto);

    CarrelloDTO svuotaCarrello(long idCliente);
}
