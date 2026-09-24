package com.example.progetto_piattaforme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrelli")
@Getter
@Setter
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrello")
    private long idCarrello;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecordCarrello> prodotti = new ArrayList<>();

    @OneToOne(mappedBy = "carrello")
    private Cliente cliente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrello carrello)) return false;
        return carrello.idCarrello != 0 && idCarrello == carrello.idCarrello;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
