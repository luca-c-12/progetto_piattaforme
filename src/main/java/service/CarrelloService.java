package service;

import dto.CarrelloDTO;

public interface CarrelloService {

    CarrelloDTO getCarrello(long idCliente);

    /** Se il prodotto è già nel carrello, somma alla quantità esistente. */
    CarrelloDTO aggiungiProdotto(long idCliente, long codiceProdotto, int quantita);

    /** quantita > 0; per rimuovere usare rimuoviProdotto. */
    CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO rimuoviProdotto(long idCliente, long codiceProdotto);

    CarrelloDTO svuotaCarrello(long idCliente);
}
