package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.CarrelloDTO;
import com.example.progetto_piattaforme.dto.request.RigaOspiteRequest;
import com.example.progetto_piattaforme.entity.Carrello;
import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.entity.RecordCarrello;
import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import com.example.progetto_piattaforme.repository.CarrelloRepository;
import com.example.progetto_piattaforme.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.progetto_piattaforme.service.ServiceUtils.verificaGiacenza;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarrelloServiceImpl implements CarrelloService {

    private final CarrelloRepository carrelloRepository;
    private final ProdottoRepository prodottoRepository;

    @Override
    public CarrelloDTO getCarrello(long idCliente) {
        return CarrelloDTO.from(caricaCarrello(idCliente));
    }

    @Override
    @Transactional
    public CarrelloDTO aggiungiProdotto(long idCliente, long codiceProdotto, int quantita) {
        if(quantita < 1)
            throw new OperazioneNonValidaException("La quantità deve essere almeno 1.");
        Prodotto p = caricaProdottoAcquistabile(codiceProdotto);
        Carrello carrello = caricaCarrello(idCliente);
        Optional<RecordCarrello> opt = trovaRiga(carrello, codiceProdotto);
        if(opt.isPresent()) {
            RecordCarrello riga = opt.get();
            int quantitaAggiornata = riga.getQuantita() + quantita;
            verificaGiacenza(p, quantitaAggiornata);
            riga.setQuantita(quantitaAggiornata);
        }else {
            verificaGiacenza(p, quantita);
            RecordCarrello riga = new RecordCarrello();
            riga.setQuantita(quantita);
            riga.setProdotto(p);
            riga.setCarrello(carrello);
            carrello.getProdotti().add(riga);
        }
        carrelloRepository.save(carrello);
        return CarrelloDTO.from(carrello);
    }

    @Override
    @Transactional
    public CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita) {
        if (quantita < 0) //es. su amazon se decremento fino a zero il prodotto viene rimosso dal carrello
            throw new OperazioneNonValidaException("Inserire una quantità valida.");

        if(quantita == 0)
            return rimuoviProdotto(idCliente, codiceProdotto);

        Carrello carrello = caricaCarrello(idCliente);
        RecordCarrello riga = caricaRiga(carrello, codiceProdotto);

        verificaGiacenza(riga.getProdotto(), quantita);
        riga.setQuantita(quantita);

        return CarrelloDTO.from(carrello);
    }

    @Override
    @Transactional
    public CarrelloDTO rimuoviProdotto(long idCliente, long codiceProdotto) {
        Carrello carrello = caricaCarrello(idCliente);
        RecordCarrello riga = caricaRiga(carrello, codiceProdotto);
        carrello.getProdotti().remove(riga);
        return CarrelloDTO.from(carrello);
    }

    @Override
    @Transactional
    public CarrelloDTO svuotaCarrello(long idCliente) {
        Carrello carrello = caricaCarrello(idCliente);
        carrello.getProdotti().clear();
        return CarrelloDTO.from(carrello);
    }

    @Override
    @Transactional
    public CarrelloDTO unisciCarrelloOspite(long idCliente, List<RigaOspiteRequest> righeOspite) {
        Carrello carrello = caricaCarrello(idCliente);

        for (RigaOspiteRequest r : righeOspite) {
            Prodotto prodotto = prodottoRepository.findById(r.codiceProdotto()).orElse(null);
            if (prodotto == null || !prodotto.isInCatalogo())
                continue;

            RecordCarrello riga = trovaRiga(carrello, r.codiceProdotto()).orElse(null);
            if (riga == null) { // riga nuova
                riga = new RecordCarrello();
                riga.setCarrello(carrello);
                riga.setProdotto(prodotto);
                carrello.getProdotti().add(riga);
            }

            riga.setQuantita(Math.min(riga.getQuantita() + r.quantita(), prodotto.getQuantita()));
        }

        return CarrelloDTO.from(carrello);
    }

    private Carrello caricaCarrello(long idCliente) {
        Carrello carrello = carrelloRepository.findByCliente_IdUtente(idCliente);
        if(carrello == null)
            throw new RisorsaNonTrovataException("Il cliente non ha un carrello.");
        return carrello;
    }

    private Prodotto caricaProdottoAcquistabile(long codiceProdotto) {
        Prodotto prodotto = prodottoRepository.findById(codiceProdotto)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prodotto " + codiceProdotto + " non trovato."));

        if (!prodotto.isVisible())
            throw new RisorsaNonTrovataException("Prodotto " + codiceProdotto + " non trovato.");

        return prodotto;
    }

    private RecordCarrello caricaRiga(Carrello carrello, long codiceProdotto) {
        return trovaRiga(carrello, codiceProdotto)
                .orElseThrow(()->new RisorsaNonTrovataException("Prodotto " + codiceProdotto + " non presente nel carrello."));
    }


    private Optional<RecordCarrello> trovaRiga(Carrello carrello, long codiceProdotto) {
        for (RecordCarrello riga : carrello.getProdotti()) {
            if (riga.getProdotto().getCodiceProdotto() == codiceProdotto)
                return Optional.of(riga);
        }
        return Optional.empty();
    }
}

