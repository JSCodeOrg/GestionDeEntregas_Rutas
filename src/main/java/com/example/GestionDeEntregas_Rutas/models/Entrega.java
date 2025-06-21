package com.example.GestionDeEntregas_Rutas.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    
    private String direccion;

    @Column(name = "id_estado")
    private Long estadoId;

    @Column(name = "id_pedido")
    private Long pedidoId;
}
