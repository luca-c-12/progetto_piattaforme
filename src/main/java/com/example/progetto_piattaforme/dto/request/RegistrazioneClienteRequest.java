package com.example.progetto_piattaforme.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrazioneClienteRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 100) String cognome,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(max = 255) String indirizzo
) { }
