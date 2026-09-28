package com.example.progetto_piattaforme.controller;

import com.example.progetto_piattaforme.dto.OrdineDTO;
import com.example.progetto_piattaforme.dto.OrdineSintesiDTO;
import com.example.progetto_piattaforme.dto.request.CheckoutRequest;
import com.example.progetto_piattaforme.security.UtenteCorrente;
import com.example.progetto_piattaforme.service.OrdineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;
    private final UtenteCorrente utenteCorrente;

    @PostMapping("/ordini")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CLIENTE')")
    public OrdineDTO effettuaCheckout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return ordineService.effettuaCheckout(utenteCorrente.id(), checkoutRequest);
    }

    @GetMapping("/ordini")
    @PreAuthorize("hasRole('CLIENTE')")
    public Page<OrdineSintesiDTO> getOrdiniCliente(@PageableDefault(size = 10) Pageable paginazione) {
        return ordineService.getOrdiniCliente(utenteCorrente.id(), paginazione);
    }

    @GetMapping("/ordini/{idOrdine}")
    @PreAuthorize("hasRole('CLIENTE')")
    public OrdineDTO getOrdine(@PathVariable long idOrdine) {
        return ordineService.getOrdine(utenteCorrente.id(), idOrdine);
    }

    @GetMapping("/admin/ordini")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrdineSintesiDTO> getTuttiGliOrdini(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate al,
            @PageableDefault(size = 20, sort = "dataOrdine", direction = Sort.Direction.DESC) Pageable paginazione) {
        return ordineService.getTuttiGliOrdini(dal, al, paginazione);
    }

    @GetMapping("/admin/ordini/{idOrdine}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrdineDTO getOrdineComeAdmin(@PathVariable long idOrdine) {
        return ordineService.getOrdineComeAdmin(idOrdine);
    }
}
