package com.example.GestionDeEntregas_Rutas.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "asignacion")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id_asignacion;

    @OneToOne
    @JoinColumn(name = "id_entrega")
    private Entrega entrega_id;

    @ManyToOne
    @JoinColumn(name = "id_repartidor")
    private Repartidor repartidor_id;
}
