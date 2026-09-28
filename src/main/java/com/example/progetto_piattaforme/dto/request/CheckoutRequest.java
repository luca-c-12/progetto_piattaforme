package com.example.progetto_piattaforme.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckoutRequest(
        @NotNull List<@NotNull @Valid RigaCheckoutRequest> righe
) { }
