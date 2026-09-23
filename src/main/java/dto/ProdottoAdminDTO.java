package dto;

import entity.Prodotto;

public record ProdottoAdminDTO(
        ProdottoDTO prodotto,
        int quantita,
        boolean visibile
) {
    public static ProdottoAdminDTO from(Prodotto p) {
        return new ProdottoAdminDTO(ProdottoDTO.from(p), p.getQuantita(), p.isVisible());
    }
}