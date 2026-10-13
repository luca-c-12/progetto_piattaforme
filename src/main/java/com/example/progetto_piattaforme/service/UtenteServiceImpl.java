package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.ClienteDTO;
import com.example.progetto_piattaforme.dto.UtenteDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProfiloRequest;
import com.example.progetto_piattaforme.dto.request.RicaricaSaldoRequest;
import com.example.progetto_piattaforme.entity.*;
import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import com.example.progetto_piattaforme.repository.ClienteRepository;
import com.example.progetto_piattaforme.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService{

    private final UtenteRepository utenteRepository;
    private final ClienteRepository clienteRepository;

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
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
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

    @Override
    public boolean isAttivo(long idUtente) {
        Optional<Utente> u = utenteRepository.findById(idUtente);
        return u.map(Utente::isVisible).orElse(false);
    }

    @Override
    @Transactional
    public void riattivaUtente(long idUtente) {
        caricaCliente(idUtente).setVisible(true);
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
