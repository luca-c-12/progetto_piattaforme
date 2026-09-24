package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.OrdineDTO;
import com.example.progetto_piattaforme.dto.OrdineSintesiDTO;
import com.example.progetto_piattaforme.entity.*;
import com.example.progetto_piattaforme.repository.ClienteRepository;
import com.example.progetto_piattaforme.repository.OrdineRepository;

import com.example.progetto_piattaforme.exception.OperazioneNonValidaException;
import com.example.progetto_piattaforme.exception.RisorsaNonTrovataException;
import com.example.progetto_piattaforme.exception.SaldoNonSufficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.progetto_piattaforme.service.ServiceUtils.verificaGiacenza;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService{

    private final ClienteRepository clienteRepository;
    private final OrdineRepository ordineRepository;

    @Override
    @Transactional
    public OrdineDTO effettuaCheckout(long idCliente) {
        Cliente cliente = caricaCliente(idCliente);
        Carrello carrello = cliente.getCarrello();
        if(carrello == null || carrello.getProdotti().isEmpty())
            throw new OperazioneNonValidaException("nessun prodotto nel carrello");
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        ordine.setIndirizzoSpedizione(cliente.getIndirizzo());
        BigDecimal totale = BigDecimal.ZERO;
        for(RecordCarrello r : carrello.getProdotti()) {
            if (!r.getProdotto().isVisible())
                throw new OperazioneNonValidaException(r.getProdotto().getNome() + " non è più disponibile.");
            verificaGiacenza(r.getProdotto(), r.getQuantita());
            LineaOrdine linea = new LineaOrdine();
            linea.setQuantita(r.getQuantita());
            linea.setProdotto(r.getProdotto());
            linea.setNomeProdotto(r.getProdotto().getNome());
            linea.setPrezzoUnitario(r.getProdotto().getPrezzo());
            linea.setOrdine(ordine);
            ordine.getProdottiAcquistati().add(linea);
            Prodotto prodotto = r.getProdotto();
            prodotto.setQuantita(prodotto.getQuantita() - r.getQuantita());
            totale = totale.add(r.getProdotto().getPrezzo().multiply(BigDecimal.valueOf(r.getQuantita())));
        }
        if(cliente.getSaldo().compareTo(totale) < 0)
            throw new SaldoNonSufficienteException("il totale dell'ordine eccede il saldo disponibile");
        ordine.setTotalePagato(totale);
        carrello.getProdotti().clear();
        cliente.setSaldo(cliente.getSaldo().subtract(totale));
        ordineRepository.save(ordine);
        return OrdineDTO.from(ordine);
    }

    @Override
    public OrdineDTO getOrdine(long idCliente, long idOrdine) {
        Ordine ordine = ordineRepository.findByIdOrdineAndCliente_IdUtente(idOrdine, idCliente)
                .orElseThrow(()->new RisorsaNonTrovataException("ordine non trovato"));
        return OrdineDTO.from(ordine);
    }

    @Override
    public Page<OrdineSintesiDTO> getOrdiniCliente(long idCliente, Pageable paginazione) {
        return ordineRepository.findByCliente_IdUtenteOrderByDataOrdineDesc(idCliente, paginazione)
                .map(OrdineSintesiDTO::from);
    }

    @Override
    public Page<OrdineSintesiDTO> getTuttiGliOrdini(LocalDate dal, LocalDate al, Pageable paginazione) {
        if (dal.isAfter(al))
            throw new OperazioneNonValidaException("La data iniziale è successiva a quella finale.");

        return ordineRepository
                .findByDataOrdineBetween(dal.atStartOfDay(), al.atTime(LocalTime.MAX), paginazione)
                .map(OrdineSintesiDTO::from);
    }

    @Override
    public OrdineDTO getOrdineComeAdmin(long idOrdine) {
        Ordine ordine = ordineRepository.findById(idOrdine)
                .orElseThrow(()->new RisorsaNonTrovataException("ordine non trovato"));
        return OrdineDTO.from(ordine);
    }

    private Cliente caricaCliente(long idCliente) {
        return clienteRepository.findConCarrelloByIdUtente(idCliente).
                orElseThrow(()->new RisorsaNonTrovataException("cliente non trovato."));
    }
}
