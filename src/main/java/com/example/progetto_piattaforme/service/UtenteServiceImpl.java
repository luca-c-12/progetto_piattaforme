package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.ClienteDTO;
import com.example.progetto_piattaforme.dto.UtenteDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProfiloRequest;
import com.example.progetto_piattaforme.dto.request.RegistrazioneAdminRequest;
import com.example.progetto_piattaforme.dto.request.RegistrazioneClienteRequest;
import com.example.progetto_piattaforme.dto.request.RicaricaSaldoRequest;
import com.example.progetto_piattaforme.entity.*;

import com.example.progetto_piattaforme.exception.EmailGiaRegistrataException;
import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.progetto_piattaforme.repository.AdminRepository;
import com.example.progetto_piattaforme.repository.ClienteRepository;
import com.example.progetto_piattaforme.repository.UtenteRepository;

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
        String email = richiesta.email().trim().toLowerCase();
        verificaEmail(email);
        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.ZERO);
        cliente.setCarrello(new Carrello());
        cliente.setIndirizzo(richiesta.indirizzo());
        cliente.setEmail(email);
        cliente.setNome(richiesta.nome());
        cliente.setCognome(richiesta.cognome());
        cliente.setVisible(true);
        clienteRepository.save(cliente);
        return ClienteDTO.from(cliente);
    }

    @Override
    @Transactional
    public UtenteDTO creaAdmin(RegistrazioneAdminRequest richiesta) {
        String email = richiesta.email().trim().toLowerCase();
        verificaEmail(email);
        Admin admin = new Admin();
        admin.setNome(richiesta.nome());
        admin.setCognome(richiesta.cognome());
        admin.setEmail(email);
        admin.setVisible(true);
        adminRepository.save(admin);
        return UtenteDTO.from(admin);
    }

    @Override
    public UtenteDTO getUtente(long idUtente) {
        Utente utente = caricaUtente(idUtente);
        return UtenteDTO.from(utente);
    }

    @Override
    public ClienteDTO getProfiloCliente(long idCliente) {
        Cliente cliente = caricaCliente(idCliente);
        return ClienteDTO.from(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO aggiornaProfilo(long idCliente, AggiornaProfiloRequest richiesta) {
        Cliente cliente = caricaCliente(idCliente);
        cliente.setIndirizzo(richiesta.indirizzo());
        return ClienteDTO.from(cliente);
    }

    @Override
    @Transactional
    public ClienteDTO ricaricaSaldo(long idCliente, RicaricaSaldoRequest richiesta) {
        if (richiesta.importo() == null || richiesta.importo().signum() <= 0)
            throw new OperazioneNonValidaException("L'importo della ricarica deve essere positivo.");
        Cliente cliente = caricaCliente(idCliente);
        cliente.setSaldo(cliente.getSaldo().add(richiesta.importo()));
        return ClienteDTO.from(cliente);
    }

    @Override
    @Transactional
    public void disattivaUtente(long idUtente) {
        Utente utente = caricaUtente(idUtente);
        utente.setVisible(false);
    }

    @Override
    public Page<UtenteDTO> getUtenti(Pageable paginazione) {
        return utenteRepository.findAll(paginazione).map(UtenteDTO::from);
    }

    @Override
    @Transactional
    public long trovaOCrea(String keycloakId, String email, String nome, String cognome, Ruolo ruolo) {
        Utente utente = utenteRepository.findByKeycloakId(keycloakId).orElse(null);
        if(utente != null) {
            if(!utente.isVisible())
                utente.setVisible(true);
            return utente.getIdUtente();
        }

        utente = switch (ruolo) {
            case ADMIN -> new Admin();
            case CLIENTE -> {
                    Cliente cliente = new Cliente();
                    cliente.setCarrello(new Carrello());
                    yield cliente;
            }
        };

        utente.setKeycloakId(keycloakId);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        utente.setVisible(true);
        utenteRepository.save(utente);
        return utente.getIdUtente();
    }

    private void verificaEmail(String email) {
        if(utenteRepository.existsByEmail(email))
            throw new EmailGiaRegistrataException("indirizzo email associato ad un altro utente");
    }


    private Utente caricaUtente(long idUtente) {
        return utenteRepository.findById(idUtente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Utente non trovato."));
    }

    private Cliente caricaCliente(long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato."));
    }
}
