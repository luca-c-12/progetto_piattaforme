package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.AstaDTO;
import com.example.progetto_piattaforme.dto.OffertaAstaDTO;
import com.example.progetto_piattaforme.dto.request.CreaAstaRequest;
import com.example.progetto_piattaforme.dto.request.OffertaRequest;
import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AstaService {

    Page<AstaDTO> cercaAsteInCorso(CategoriaProdotto categoria, String nome, Pageable paginazione);

    Page<AstaDTO> cercaAsteInCorso(Pageable paginazione);

    Page<AstaDTO> getAsteProgrammate(Pageable paginazione);

    AstaDTO getAsta(long idAsta);

    Page<OffertaAstaDTO> getOfferte(long idAsta, Pageable paginazione);

    AstaDTO faiOfferta(long idCliente, long idAsta, OffertaRequest offertaRequest);

    Page<OffertaAstaDTO> getOfferteCliente(long idCliente, Pageable paginazione);


    AstaDTO creaAsta(CreaAstaRequest richiesta);


    void annullaAsta(long idAsta);


    List<Long> idAsteDaChiudere();


    void chiudiAsta(long idAsta);
}
