package entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recensioni",   uniqueConstraints = {
        @UniqueConstraint(
                name = "unicita_cliente_prodotto", // Nome del vincolo sul database
                columnNames = {"id_cliente", "id_prodotto"} // I nomi delle colonne fisiche sul DB
        )
})
@Getter
@Setter
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRecensione;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "voto", nullable = false)
    private VotoRecensione voto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto", nullable = false)
    private Prodotto prodotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column
    private String testo;

    @Version
    private long version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recensione that)) return false;
        return that.idRecensione != 0 && idRecensione == that.idRecensione;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
