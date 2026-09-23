package dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OffertaRequest(
        @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal importo
) { }
