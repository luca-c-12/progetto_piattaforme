package dto;

import entity.Recensione;

public record RecensioneDTO(
        long idRecensione,
        long codiceProdotto,
        long idCliente,
        String autore,
        int voto,
        String testo
) {
    public static RecensioneDTO from(Recensione r) {
        return new RecensioneDTO(
                r.getIdRecensione(),
                r.getProdotto().getCodiceProdotto(),
                r.getCliente().getIdUtente(),
                OffertaAstaDTO.nomePubblico(r.getCliente()),
                r.getVoto().getValue(), // 1..5 invece di "CINQUE"
                r.getTesto()
        );
    }
}
