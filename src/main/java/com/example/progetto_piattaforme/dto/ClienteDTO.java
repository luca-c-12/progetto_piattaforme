package com.example.progetto_piattaforme.dto;

import com.example.progetto_piattaforme.entity.Cliente;

import java.math.BigDecimal;

public record ClienteDTO(
        UtenteDTO utente,
        String indirizzo,
        BigDecimal saldo
) {
    public static ClienteDTO from(Cliente c) {
        return new ClienteDTO(UtenteDTO.from(c), c.getIndirizzo(), c.getSaldo());
    }
}