package com.example.GestionDeEntregas_Rutas.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id_entrega;

    private LocalDate fechaSalida;
    private LocalDate fechaEntrega;
    
    @Column(name = "id_estado")
    private Long estado_id;

    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private Ruta ruta_id;

    @Column(name = "id_pedido")
    private Long Pedido_id;
}
