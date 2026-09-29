package com.example.progetto_piattaforme.popolamento;

import com.example.progetto_piattaforme.entity.CategoriaProdotto;
import com.example.progetto_piattaforme.entity.Prodotto;
import com.example.progetto_piattaforme.repository.ProdottoRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PopolamentoDatabase implements CommandLineRunner {

    private final ProdottoRepository prodottoRepository;

    @Value("${popolamento.url}")
    private String url;

    @Override
    public void run(String... args) {
        if (prodottoRepository.count() > 0)
            return;
        RispostaDummyJson risposta;
        try {
            risposta = RestClient.create().get().uri(url).retrieve().body(RispostaDummyJson.class);
        } catch (RestClientException e) {
            log.warn("Popolamento non riuscito, catalogo vuoto: {}", e.getMessage());
            return;
        }
        if (risposta == null || risposta.products() == null)
            return;
        List<Prodotto> prodotti = new ArrayList<>(risposta.products().size());
        for (ProdottoDummyJson p : risposta.products()) {
            if (!valido(p)) {
                log.warn("Prodotto DummyJSON scartato: {}", p.title());
                continue;
            }
            Prodotto prodotto = new Prodotto();
            prodotto.setNome(p.title());
            prodotto.setDescrizione(p.description());
            prodotto.setBrand(p.brand());
            prodotto.setPrezzo(p.price());
            prodotto.setQuantita(p.stock());
            prodotto.setCategoria(categoria(p.category()));
            prodotto.setUrlImmagine(p.thumbnail());
            prodotto.setVisible(true);
            prodotti.add(prodotto);
        }
        prodottoRepository.saveAll(prodotti);
        log.info("Caricati {} prodotti da DummyJSON", prodotti.size());
    }

    private CategoriaProdotto categoria(String categoriaDummy) {
        return switch (categoriaDummy) {
            case "smartphones", "mobile-accessories" -> CategoriaProdotto.ELETTRONICA;
            case "laptops", "tablets" -> CategoriaProdotto.INFORMATICA;
            case "kitchen-accessories", "furniture", "home-decoration" -> CategoriaProdotto.CASA_E_CUCINA;
            case "groceries" -> CategoriaProdotto.ALIMENTARI;
            case "beauty", "fragrances", "skin-care" -> CategoriaProdotto.BELLEZZA_E_SALUTE;
            case "sports-accessories" -> CategoriaProdotto.SPORT_E_TEMPO_LIBERO;
            case "motorcycle", "vehicle" -> CategoriaProdotto.AUTO_E_MOTO;
            case null, default -> CategoriaProdotto.ABBIGLIAMENTO_SCARPE_GIOIELLI;
        };
    }

    private boolean valido(ProdottoDummyJson p) {
        return p != null
                && p.title() != null && !p.title().isBlank() && p.title().length() <= 255
                && p.price() != null && p.price().signum() > 0
                && p.stock() >= 0
                && (p.description() == null || p.description().length() <= 2000)
                && (p.brand() == null || p.brand().length() <= 255)
                && (p.thumbnail() == null || p.thumbnail().length() <= 1000);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record RispostaDummyJson(List<ProdottoDummyJson> products) { }
}
