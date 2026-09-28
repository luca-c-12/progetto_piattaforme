package com.example.progetto_piattaforme.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RigaCheckoutRequest(
        @Positive long codiceProdotto,
        @Min(1) int quantita,
        @NotNull @Positive BigDecimal prezzoUnitario
) { }
