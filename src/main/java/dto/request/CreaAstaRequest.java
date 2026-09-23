package dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreaAstaRequest(
        @Positive long codiceProdotto,
        @Future LocalDateTime dataInizio,          // null = parte subito
        @NotNull @Future LocalDateTime dataFine,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal prezzoBase
) { }
