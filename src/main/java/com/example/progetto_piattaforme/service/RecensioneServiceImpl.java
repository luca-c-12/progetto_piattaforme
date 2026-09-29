package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.RecensioneDTO;
import com.example.progetto_piattaforme.dto.request.RecensioneRequest;
import com.example.progetto_piattaforme.entity.Cliente;
import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.entity.Recensione;
import com.example.progetto_piattaforme.entity.VotoRecensione;
import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import com.example.progetto_piattaforme.repository.ClienteRepository;
import com.example.progetto_piattaforme.repository.LineaOrdineRepository;
import com.example.progetto_piattaforme.repository.ProdottoRepository;
import com.example.progetto_piattaforme.repository.RecensioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService {

    private final RecensioneRepository recensioneRepository;
    private final ClienteRepository clienteRepository;
    private final ProdottoRepository prodottoRepository;
    private final LineaOrdineRepository lineaOrdineRepository;

    @Override
    @Transactional
    public RecensioneDTO scriviRecensione(long idCliente, long codiceProdotto, RecensioneRequest richiesta) {

        if(richiesta.voto() < 1 || richiesta.voto() > 5)
            throw new OperazioneNonValidaException("Voto non valido.");

        Cliente cliente = caricaCliente(idCliente);
        Prodotto prodotto = caricaProdotto(codiceProdotto);

        if (recensioneRepository.existsByCliente_IdUtenteAndProdotto_CodiceProdotto(idCliente, codiceProdotto))
            throw new OperazioneNonValidaException("Hai già recensito questo prodotto.");
        if (!lineaOrdineRepository.existsByOrdine_Cliente_IdUtenteAndProdotto_CodiceProdotto(idCliente, codiceProdotto))
            throw new OperazioneNonValidaException("Puoi recensire solo prodotti che hai acquistato.");

        Recensione recensione = new Recensione();
        recensione.setCliente(cliente);
        recensione.setProdotto(prodotto);
        recensione.setTesto(richiesta.testo());

        recensione.setVoto(VotoRecensione.daValore(richiesta.voto()));
        recensioneRepository.save(recensione);
        return RecensioneDTO.from(recensione);
    }

    @Override
    @Transactional
    public RecensioneDTO modificaRecensione(long idCliente, long idRecensione, RecensioneRequest richiesta) {
        Recensione recensione = caricaRecensioneDelCliente(idCliente, idRecensione);
        if(richiesta.voto() < 1 || richiesta.voto() > 5)
            throw new OperazioneNonValidaException("Voto non valido.");
        recensione.setVoto(VotoRecensione.daValore(richiesta.voto()));
        recensione.setTesto(richiesta.testo());
        return RecensioneDTO.from(recensione);
    }

    @Override
    @Transactional
    public void eliminaRecensione(long idCliente, long idRecensione) {
        Recensione recensione = caricaRecensioneDelCliente(idCliente, idRecensione);
        recensioneRepository.delete(recensione);
    }

    @Override
    public Page<RecensioneDTO> getRecensioniProdotto(long codiceProdotto, Pageable paginazione) {
        Prodotto prodotto = caricaProdotto(codiceProdotto);
        return recensioneRepository.findByProdotto_CodiceProdotto(codiceProdotto, paginazione)
                .map(RecensioneDTO::from);
    }

    @Override
    public Page<RecensioneDTO> getRecensioniCliente(long idCliente, Pageable paginazione) {
        Cliente cliente = caricaCliente(idCliente);
        return recensioneRepository.findByCliente_IdUtente(idCliente, paginazione)
                .map(RecensioneDTO::from);
    }

    @Override
    @Transactional
    public void eliminaRecensioneComeAdmin(long idRecensione) {
        Recensione recensione = recensioneRepository.findById(idRecensione)
                        .orElseThrow(()->new RisorsaNonTrovataException("Recensione non trovata."));
        recensioneRepository.delete(recensione);
    }

    private Cliente caricaCliente(long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato."));
    }

    private Prodotto caricaProdotto(long codiceProdotto) {
        return prodottoRepository.findById(codiceProdotto)
                .orElseThrow(()->new RisorsaNonTrovataException("Prodotto non trovato."));
    }


    private Recensione caricaRecensioneDelCliente(long idCliente, long idRecensione) {
        return recensioneRepository.findByIdRecensioneAndCliente_IdUtente(idRecensione, idCliente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Recensione non trovata."));
    }
}
