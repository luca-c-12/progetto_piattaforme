package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "prodotti_carrello", uniqueConstraints = {
        @UniqueConstraint(
                name = "unicita_record_prodotto",
                columnNames = {"id_prodotto", "id_carrello"}
        )
})
@Getter
@Setter
public class RecordCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_record_carrello")
    private long idRecordCarrello;

    @Column(name = "quantita")
    private int quantita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto", nullable = false)
    private Prodotto prodotto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrello", nullable = false)
    @ToString.Exclude
    private Carrello carrello;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordCarrello that)) return false;
        return that.idRecordCarrello != 0 && idRecordCarrello == that.idRecordCarrello;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
