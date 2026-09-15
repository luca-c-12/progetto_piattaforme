package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "prodotti_carrello")
@Getter
@Setter
public class RecordCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_record_carrello")
    private long idRecordCarrello;

    @Column(name = "quantita")
    private int quantita;

    @ManyToOne
    @JoinColumn(name = "id_prodotto")
    private Prodotto prodotto;


    @ManyToOne
    @JoinColumn(name = "id_carrello")
    @ToString.Exclude
    private Carrello carrello;

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
