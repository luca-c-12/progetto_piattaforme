package service;

import dto.RecensioneDTO;
import dto.request.RecensioneRequest;
import entity.Cliente;
import entity.Prodotto;
import entity.Recensione;
import entity.VotoRecensione;
import exception.OperazioneNonValidaException;
import exception.RisorsaNonTrovataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ClienteRepository;
import repository.LineaOrdineRepository;
import repository.ProdottoRepository;
import repository.RecensioneRepository;

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
                .orElseThrow(()->new RisorsaNonTrovataException("prodotto inesistente"));
    }


    private Recensione caricaRecensioneDelCliente(long idCliente, long idRecensione) {
        return recensioneRepository.findByIdRecensioneAndCliente_IdUtente(idRecensione, idCliente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Recensione non trovata."));
    }
}
