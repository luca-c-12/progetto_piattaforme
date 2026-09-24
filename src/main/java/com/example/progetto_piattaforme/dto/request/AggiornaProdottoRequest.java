package com.example.progetto_piattaforme.dto.request;

import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AggiornaProdottoRequest(
        @NotBlank @Size(max = 255) String nome,
        @Size(max = 2000) String descrizione,
        @Size(max = 255) String brand,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal prezzo,
        @NotNull CategoriaProdotto categoria,
        @Size(max = 1000) @Pattern(regexp = "https?://.+") String urlImmagine
) { }
