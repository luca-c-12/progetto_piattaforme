package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.ProdottoAdminDTO;
import com.example.progetto_piattaforme.dto.ProdottoDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProdottoRequest;
import com.example.progetto_piattaforme.dto.request.CreaProdottoRequest;
import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.progetto_piattaforme.repository.AstaRepository;
import com.example.progetto_piattaforme.repository.ProdottoRepository;


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
        prodotto.setUrlImmagine(richiesta.urlImmagine());
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
        prodotto.setUrlImmagine(richiesta.urlImmagine());
        return ProdottoAdminDTO.from(prodotto);
    }


    @Override
    @Transactional
    public ProdottoAdminDTO impostaGiacenza(long codiceProdotto, int quantita) {
        if (astaRepository.existsByProdotto_CodiceProdottoAndChiusaFalse(codiceProdotto))
            throw new OperazioneNonValidaException("Non si può modificare la giacenza di un prodotto all'asta.");
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
        if (visibile && astaRepository.existsByProdotto_CodiceProdottoAndChiusaFalse(codiceProdotto))
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
