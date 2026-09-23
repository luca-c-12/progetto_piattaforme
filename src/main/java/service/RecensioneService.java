package service;

import dto.RecensioneDTO;
import dto.request.RecensioneRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecensioneService {


    RecensioneDTO scriviRecensione(long idCliente, long codiceProdotto, RecensioneRequest richiesta);

    RecensioneDTO modificaRecensione(long idCliente, long idRecensione, RecensioneRequest richiesta);

    void eliminaRecensione(long idCliente, long idRecensione);

    Page<RecensioneDTO> getRecensioniProdotto(long codiceProdotto, Pageable paginazione);

    Page<RecensioneDTO> getRecensioniCliente(long idCliente, Pageable paginazione);

    void eliminaRecensioneComeAdmin(long idRecensione);
}
