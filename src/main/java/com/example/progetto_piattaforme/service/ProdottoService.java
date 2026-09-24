package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.ProdottoAdminDTO;
import com.example.progetto_piattaforme.dto.ProdottoDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProdottoRequest;
import com.example.progetto_piattaforme.dto.request.CreaProdottoRequest;
import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdottoService {

    Page<ProdottoDTO> cercaProdotti(CategoriaProdotto categoria, String brand, String nome,
                                    Pageable paginazione);

    ProdottoDTO getProdotto(long codiceProdotto);


    Page<ProdottoAdminDTO> getProdottiAdmin(Pageable paginazione);

    ProdottoAdminDTO creaProdotto(CreaProdottoRequest richiesta);


    ProdottoAdminDTO aggiornaProdotto(long codiceProdotto, AggiornaProdottoRequest richiesta);

    ProdottoAdminDTO impostaGiacenza(long codiceProdotto, int quantita);

    ProdottoAdminDTO impostaVisibilita(long codiceProdotto, boolean visibile);
}
