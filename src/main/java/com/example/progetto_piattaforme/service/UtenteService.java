package com.example.progetto_piattaforme.service;

import com.example.progetto_piattaforme.dto.ClienteDTO;
import com.example.progetto_piattaforme.dto.UtenteDTO;
import com.example.progetto_piattaforme.dto.request.AggiornaProfiloRequest;
import com.example.progetto_piattaforme.dto.request.RicaricaSaldoRequest;
import com.example.progetto_piattaforme.entity.Ruolo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UtenteService {

    UtenteDTO getUtente(long idUtente);

    ClienteDTO getProfiloCliente(long idCliente);

    ClienteDTO aggiornaProfilo(long idCliente, AggiornaProfiloRequest richiesta);

    ClienteDTO ricaricaSaldo(long idCliente, RicaricaSaldoRequest richiesta);

    void disattivaUtente(long idUtente);

    Page<UtenteDTO> getUtenti(Pageable paginazione);

    long trovaOCrea(String keycloakId, String email, String nome, String cognome, Ruolo ruolo);
}
