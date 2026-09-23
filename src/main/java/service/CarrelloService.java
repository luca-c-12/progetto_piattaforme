package service;

import dto.CarrelloDTO;
import dto.request.RigaOspiteRequest;

import java.util.List;

public interface CarrelloService {

    CarrelloDTO getCarrello(long idCliente);

    CarrelloDTO aggiungiProdotto(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita);

    CarrelloDTO rimuoviProdotto(long idCliente, long codiceProdotto);

    CarrelloDTO svuotaCarrello(long idCliente);

    CarrelloDTO unisciCarrelloOspite(long idCliente, List<RigaOspiteRequest> righeOspite);
}
