package service;

import dto.ProdottoAdminDTO;
import dto.ProdottoDTO;
import dto.request.AggiornaProdottoRequest;
import dto.request.CreaProdottoRequest;
import entity.CategoriaProdotto;
import entity.Prodotto;
import exception.OperazioneNonValidaException;
import exception.RisorsaNonTrovataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AstaRepository;
import repository.ProdottoRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProdottoServiceImpl implements ProdottoService {

    private final ProdottoRepository prodottoRepository;
    private final AstaRepository astaRepository;


    @Override
    public Page<ProdottoDTO> cercaProdotti(CategoriaProdotto categoria, String brand, String nome, Pageable paginazione) {
        return prodottoRepository.cerca(categoria, brand, nome, paginazione).map(ProdottoDTO::from);
    }

    @Override
    public ProdottoDTO getProdotto(long codiceProdotto) {
        return ProdottoDTO.from(caricaProdottoVisibile(codiceProdotto));
    }

    @Override
    public Page<ProdottoAdminDTO> getProdottiAdmin(Pageable paginazione) {
        return prodottoRepository.findAll(paginazione).map(ProdottoAdminDTO::from);
    }

    @Override
    @Transactional
    public ProdottoAdminDTO creaProdotto(CreaProdottoRequest richiesta) {
        if(richiesta.quantita() < 0)
            throw new OperazioneNonValidaException("Quantita non valida");
        Prodotto prodotto = new Prodotto();
        prodotto.setVisible(true);
        prodotto.setQuantita(richiesta.quantita());
        prodotto.setBrand(richiesta.brand());
        prodotto.setCategoria(richiesta.categoria());
        prodotto.setNome(richiesta.nome());
        prodotto.setDescrizione(richiesta.descrizione());
        prodotto.setPrezzo(richiesta.prezzo());
        prodottoRepository.save(prodotto);
        return ProdottoAdminDTO.from(prodotto);
    }

    @Override
    @Transactional
    public ProdottoAdminDTO aggiornaProdotto(long codiceProdotto, AggiornaProdottoRequest richiesta) {
        Prodotto prodotto = caricaProdotto(codiceProdotto);
        prodotto.setNome(richiesta.nome());
        prodotto.setDescrizione(richiesta.descrizione());
        prodotto.setPrezzo(richiesta.prezzo());
        prodotto.setBrand(richiesta.brand());
        prodotto.setCategoria(richiesta.categoria());
        return ProdottoAdminDTO.from(prodotto);
    }


    @Override
    @Transactional
    public ProdottoAdminDTO impostaGiacenza(long codiceProdotto, int quantita) {
        if (quantita < 0)
            throw new OperazioneNonValidaException("Quantità non valida.");
        Prodotto prodotto = caricaProdotto(codiceProdotto);
        prodotto.setQuantita(quantita);
        return ProdottoAdminDTO.from(prodotto);
    }

    @Override
    @Transactional
    public ProdottoAdminDTO impostaVisibilita(long codiceProdotto, boolean visibile) {
        Prodotto prodotto = caricaProdotto(codiceProdotto);
        if(visibile && astaRepository.existsByProdotto_CodiceProdottoAndIsVisibleTrueAndDataFineAfter(codiceProdotto, LocalDateTime.now()))
            throw new OperazioneNonValidaException("un prodotto all'asta non puó essere visibile nel catalogo");
        prodotto.setVisible(visibile);
        return ProdottoAdminDTO.from(prodotto);
    }

    private Prodotto caricaProdotto(long codiceProdotto) {
        return prodottoRepository.findById(codiceProdotto)
                .orElseThrow(()->new RisorsaNonTrovataException("prodotto inesistente"));
    }

    private Prodotto caricaProdottoVisibile(long codiceProdotto) {
        Prodotto prodotto = caricaProdotto(codiceProdotto);
        // nascosto o esaurito: per il catalogo pubblico è come se non esistesse
        if (!prodotto.isVisible() || prodotto.getQuantita() == 0)
            throw new RisorsaNonTrovataException("prodotto inesistente");
        return prodotto;
    }
}
