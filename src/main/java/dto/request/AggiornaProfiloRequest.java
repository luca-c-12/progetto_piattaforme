package dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AggiornaProfiloRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Size(max = 100) String cognome,
        @NotBlank @Size(max = 255) String indirizzo
) { }
