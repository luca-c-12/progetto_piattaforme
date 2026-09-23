package service;

import dto.ClienteDTO;
import dto.UtenteDTO;
import dto.request.AggiornaProfiloRequest;
import dto.request.RegistrazioneAdminRequest;
import dto.request.RegistrazioneClienteRequest;
import dto.request.RicaricaSaldoRequest;
import entity.*;
import exception.EmailGiaRegistrataException;
import exception.OperazioneNonValidaException;
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
        cliente.setNome(richiesta.nome());
        cliente.setCognome(richiesta.cognome());
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
