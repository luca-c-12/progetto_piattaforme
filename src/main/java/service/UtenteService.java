package service;

import dto.ClienteDTO;
import dto.UtenteDTO;
import dto.request.AggiornaProfiloRequest;
import dto.request.RegistrazioneAdminRequest;
import dto.request.RegistrazioneClienteRequest;
import dto.request.RicaricaSaldoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UtenteService {


    ClienteDTO registraCliente(RegistrazioneClienteRequest richiesta);

    UtenteDTO creaAdmin(RegistrazioneAdminRequest richiesta);

    UtenteDTO getUtente(long idUtente);

    ClienteDTO getProfiloCliente(long idCliente);

    ClienteDTO aggiornaProfilo(long idCliente, AggiornaProfiloRequest richiesta);

    ClienteDTO ricaricaSaldo(long idCliente, RicaricaSaldoRequest richiesta);

    void disattivaUtente(long idUtente);

    Page<UtenteDTO> getUtenti(Pageable paginazione);
}
