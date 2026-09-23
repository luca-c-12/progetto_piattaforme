package service;

import dto.ClienteDTO;
import dto.UtenteDTO;
import dto.request.AggiornaProfiloRequest;
import dto.request.RegistrazioneAdminRequest;
import dto.request.RegistrazioneClienteRequest;
import dto.request.RicaricaSaldoRequest;
import entity.*;
import exception.EmailGiaRegistrataException;
import exception.RisorsaNonTrovataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AdminRepository;
import repository.ClienteRepository;
import repository.UtenteRepository;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService{

    private final UtenteRepository utenteRepository;
    private final ClienteRepository clienteRepository;
    private final AdminRepository adminRepository;


    @Override
    @Transactional
    public ClienteDTO registraCliente(RegistrazioneClienteRequest richiesta) {
        verificaEmail(richiesta.email());
        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.ZERO);
        cliente.setCarrello(new Carrello());
        cliente.setIndirizzo(richiesta.indirizzo());
        cliente.setEmail(richiesta.email());
        cliente.setNome(richiesta.nome());
        cliente.setCognome(richiesta.cognome());
        cliente.setVisible(true);
        clienteRepository.save(cliente);
        return ClienteDTO.from(cliente);
    }

    @Override
    @Transactional
    public UtenteDTO creaAdmin(RegistrazioneAdminRequest richiesta) {
        verificaEmail(richiesta.email());
        Admin admin = new Admin();
        admin.setNome(richiesta.nome());
        admin.setCognome(richiesta.cognome());
        admin.setEmail(richiesta.email());
        admin.setVisible(true);
        adminRepository.save(admin);
        return UtenteDTO.from(admin);
    }

    @Override
    public UtenteDTO getUtente(long idUtente) {
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(()-> new RisorsaNonTrovataException("utente inesistente"));
        return UtenteDTO.from(utente);
    }

    @Override
    public ClienteDTO getProfiloCliente(long idCliente) {
        return null;
    }

    @Override
    @Transactional
    public ClienteDTO aggiornaProfilo(long idCliente, AggiornaProfiloRequest richiesta) {
        return null;
    }

    @Override
    @Transactional
    public ClienteDTO ricaricaSaldo(long idCliente, RicaricaSaldoRequest richiesta) {
        return null;
    }

    @Override
    @Transactional
    public void disattivaUtente(long idUtente) {

    }

    @Override
    public Page<UtenteDTO> getUtenti(Pageable paginazione) {
        return null;
    }

    private void verificaEmail(String email) {
        if(utenteRepository.existsByEmail(email))
            throw new EmailGiaRegistrataException("indirizzo email associato ad un altro utente");
    }

    private Utente caricaUtente(long idUtente, Ruolo ruolo) {
        return null;
    }
}
