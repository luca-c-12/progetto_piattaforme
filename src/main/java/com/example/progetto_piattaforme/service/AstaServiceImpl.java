package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.AstaDTO;
import com.example.progetto_piattaforme.dto.OffertaAstaDTO;
import com.example.progetto_piattaforme.dto.request.CreaAstaRequest;
import com.example.progetto_piattaforme.dto.request.OffertaRequest;
import com.example.progetto_piattaforme.entity.*;
import com.example.progetto_piattaforme.repository.*;

import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import com.example.progetto_piattaforme.exception.SaldoNonSufficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AstaServiceImpl implements AstaService {

    private final AstaRepository astaRepository;
    private final ClienteRepository clienteRepository;
    private final OffertaAstaRepository offertaAstaRepository;
    private final ProdottoRepository prodottoRepository;

    private final OrdineRepository ordineRepository;


    @Override
    public Page<AstaDTO> cercaAsteInCorso(CategoriaProdotto categoria, String nome, Pageable paginazione) {
        LocalDateTime adesso = LocalDateTime.now();
        return astaRepository.cercaAsteInCorso(adesso, categoria, nome, paginazione)
                .map(AstaDTO::from);
    }

    @Override
    public Page<AstaDTO> cercaAsteInCorso(Pageable paginazione) {
        LocalDateTime adesso = LocalDateTime.now();
        return astaRepository.cercaAsteInCorso(adesso, null, null, paginazione)
                .map(AstaDTO::from);
    }

    @Override
    public Page<AstaDTO> getAsteProgrammate(Pageable paginazione) {
        LocalDateTime adesso = LocalDateTime.now();
        return astaRepository.findByIsVisibleTrueAndDataInizioAfter(adesso, paginazione)
                .map(AstaDTO::from);
    }

    @Override
    public AstaDTO getAsta(long idAsta) {
        return AstaDTO.from(caricaAsta(idAsta));
    }

    @Override
    public Page<OffertaAstaDTO> getOfferte(long idAsta, Pageable paginazione) {
        Asta asta = caricaAsta(idAsta);
        return offertaAstaRepository.findByAsta_IdAstaOrderByImportoOffertaDesc(idAsta, paginazione)
                .map(OffertaAstaDTO::from);
    }

    @Override
    @Transactional
    public AstaDTO faiOfferta(long idCliente, long idAsta, OffertaRequest offertaRequest) {
        Asta asta = caricaAstaPerAggiornamento(idAsta);
        LocalDateTime adesso = LocalDateTime.now();
        if (!asta.isVisible() || asta.statoAl(adesso) != StatoAsta.IN_CORSO)
            throw new OperazioneNonValidaException("si possono fare offerte solo per aste in corso");
        if (asta.getOffertaMax() == null) {
            if (offertaRequest.importo().compareTo(asta.getPrezzoBase()) < 0)
                throw new OperazioneNonValidaException("Offerta inferiore al prezzo base.");
        } else if (offertaRequest.importo().compareTo(asta.getOffertaMax().getImportoOfferta()) <= 0) {
            throw new OperazioneNonValidaException("L'offerta deve superare quella attuale.");
        }
        Cliente cliente = caricaCliente(idCliente);
        if(cliente.getSaldo().compareTo(offertaRequest.importo()) < 0)
            throw new SaldoNonSufficienteException("saldo non sufficiente");
        OffertaAsta offertaAsta = new OffertaAsta();
        offertaAsta.setAsta(asta);
        offertaAsta.setCliente(cliente);
        offertaAsta.setImportoOfferta(offertaRequest.importo());
        offertaAstaRepository.save(offertaAsta);
        asta.getOfferteRicevute().add(offertaAsta);
        asta.setOffertaMax(offertaAsta);
        return AstaDTO.from(asta);
    }

    @Override
    public Page<OffertaAstaDTO> getOfferteCliente(long idCliente, Pageable paginazione) {
        return offertaAstaRepository.findByCliente_IdUtenteOrderByDataOffertaDesc(idCliente, paginazione)
                .map(OffertaAstaDTO::from);
    }

    @Override
    @Transactional
    public AstaDTO creaAsta(CreaAstaRequest richiesta) {
        LocalDateTime inizio;
        if(richiesta.dataInizio() == null)
            inizio = LocalDateTime.now();
        else
            inizio = richiesta.dataInizio();
        if(richiesta.prezzoBase().compareTo(BigDecimal.ZERO) <= 0 ||
                inizio.isAfter(richiesta.dataFine()))
            throw new OperazioneNonValidaException("richiesta malformata");
        Prodotto prodotto = caricaProdotto(richiesta.codiceProdotto());
        if(astaRepository.existsByProdotto_CodiceProdottoAndChiusaFalse(richiesta.codiceProdotto()))
            throw new OperazioneNonValidaException("esiste gia un'asta attiva/programmata per questo prodotto");
        Asta asta = new Asta();
        asta.setProdotto(prodotto);
        asta.setPrezzoBase(richiesta.prezzoBase());
        asta.setDataInizio(inizio);
        asta.setDataFine(richiesta.dataFine());
        asta.setVisible(true);
        asta.setChiusa(false);
        astaRepository.save(asta);
        return AstaDTO.from(asta);
    }

    @Override
    @Transactional
    public void annullaAsta(long idAsta) {
        Asta asta = caricaAsta(idAsta);
        LocalDateTime adesso = LocalDateTime.now();
        if(asta.statoAl(adesso) != StatoAsta.TERMINATA) {
            asta.setVisible(false);
            asta.setChiusa(true);
            asta.setVincitore(null);
        }else
            throw new OperazioneNonValidaException("impossibile annullare un'asta gia conclusa");
    }

    @Override
    public List<Long> idAsteDaChiudere() {
        return astaRepository.findIdDaChiudere(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void chiudiAsta(long idAsta) {
        Asta asta = caricaAstaPerAggiornamento(idAsta);
        LocalDateTime adesso = LocalDateTime.now();
        if(asta.statoAl(adesso) == StatoAsta.TERMINATA) {
            asta.setVisible(false);
            asta.setChiusa(true);
            List<OffertaAsta> offerteRicevute = offertaAstaRepository.findByAsta_IdAstaOrderByImportoOffertaDesc(idAsta);
            Set<Long> clientiInsolventi = new HashSet<Long>();
            asta.setOffertaMax(null);  // resta null se nessuno può pagare
            for(OffertaAsta offerta : offerteRicevute) {
                Cliente cliente = offerta.getCliente();
                if(!cliente.isVisible() || cliente.getSaldo().compareTo(offerta.getImportoOfferta()) < 0) {
                    clientiInsolventi.add(Long.valueOf(cliente.getIdUtente()));
                }
                if(clientiInsolventi.contains(cliente.getIdUtente()))
                    continue;
                else{
                    Ordine ordine = new Ordine();
                    ordine.setTotalePagato(offerta.getImportoOfferta());
                    ordine.setCliente(cliente);
                    ordine.setIndirizzoSpedizione(cliente.getIndirizzo());

                    LineaOrdine lineaOrdine = new LineaOrdine();
                    lineaOrdine.setOrdine(ordine);
                    lineaOrdine.setPrezzoUnitario(offerta.getImportoOfferta());
                    lineaOrdine.setNomeProdotto(asta.getProdotto().getNome());
                    lineaOrdine.setProdotto(asta.getProdotto());
                    lineaOrdine.setQuantita(1);

                    Prodotto p = asta.getProdotto();
                    p.setQuantita(p.getQuantita() - 1);

                    ordine.getProdottiAcquistati().add(lineaOrdine);

                    cliente.setSaldo(cliente.getSaldo().subtract(offerta.getImportoOfferta()));

                    asta.setVincitore(cliente);

                    asta.setOffertaMax(offerta);  // la massima offerta valida è quella vincente

                    clienteRepository.save(cliente);

                    ordineRepository.save(ordine);

                    break;
                }
            }

            astaRepository.save(asta);
        }else
            throw new OperazioneNonValidaException("impossibile chiudere un'asta non terminata");

    }

    private Cliente caricaCliente(long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato."));
    }

    private Asta caricaAsta(long idAsta) {
        return astaRepository.findById(idAsta)
                .orElseThrow(()->new RisorsaNonTrovataException("Asta non trovata."));
    }

    private Asta caricaAstaPerAggiornamento(long idAsta) {
        return astaRepository.findByIdPerAggiornamento(idAsta)
                .orElseThrow(() -> new RisorsaNonTrovataException("Asta non trovata."));
    }

    private Prodotto caricaProdotto(long codiceProdotto) {
        Prodotto prodotto = prodottoRepository.findById(codiceProdotto)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prodotto inesistente"));
        if (prodotto.isVisible() || prodotto.getQuantita() == 0)
            throw new OperazioneNonValidaException("Solo prodotti fuori catalogo e con pezzi disponibili.");
        return prodotto;
    }
}
