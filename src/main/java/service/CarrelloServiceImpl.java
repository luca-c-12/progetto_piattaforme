package service;

import dto.CarrelloDTO;
import entity.Carrello;
import entity.Prodotto;
import entity.RecordCarrello;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CarrelloRepository;
import repository.ProdottoRepository;
import repository.RecordCarrelloRepository;
import exception.*;

import java.util.Optional;

import static service.ServiceUtils.verificaGiacenza;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarrelloServiceImpl implements CarrelloService {

    private final CarrelloRepository carrelloRepository;
    private final RecordCarrelloRepository recordCarrelloRepository;
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
        return CarrelloDTO.from(carrello);
    }

    @Override
    @Transactional
    public CarrelloDTO aggiornaQuantita(long idCliente, long codiceProdotto, int quantita) {
        if (quantita < 0) //es. su amazon se decremento fino a zero il prodotto viene rimosso dal carrello
            throw new OperazioneNonValidaException("Inserire una quantita valida.");

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
        return recordCarrelloRepository
                .findByCarrello_IdCarrelloAndProdotto_CodiceProdotto(carrello.getIdCarrello(), codiceProdotto);
    }
}

