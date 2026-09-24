package com.example.progetto_piattaforme.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MergeCarrelloRequest(
        @NotNull @Size(max = 50) List<@Valid RigaOspiteRequest> righe
) { }
