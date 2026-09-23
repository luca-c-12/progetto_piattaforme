package dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RicaricaSaldoRequest(
        @NotNull @Positive @Digits(integer = 6, fraction = 2) BigDecimal importo
) { }
