package com.example.progetto_piattaforme.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AggiornaProfiloRequest(
        @NotBlank @Size(max = 255) String indirizzo
) { }
