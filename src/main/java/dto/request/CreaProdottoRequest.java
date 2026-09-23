package dto.request;

import entity.CategoriaProdotto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreaProdottoRequest(
        @NotBlank @Size(max = 255) String nome,
        @Size(max = 2000) String descrizione,
        @Size(max = 255) String brand,
        @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal prezzo,
        @NotNull CategoriaProdotto categoria,
        @PositiveOrZero int quantita
) { }


