package dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record RecensioneRequest(
        @Min(1) @Max(5) int voto,
        @Size(max = 4000) String testo
) { }
