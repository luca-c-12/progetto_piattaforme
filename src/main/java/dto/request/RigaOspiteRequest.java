package dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record RigaOspiteRequest(
        @Positive long codiceProdotto,
        @Min(1) @Max(99) int quantita
) { }
