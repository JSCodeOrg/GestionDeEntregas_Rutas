package com.example.GestionDeEntregas_Rutas.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "ruta")
public class Ruta {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id_ruta;

    private String origen;
    private String destino;
    private String duracionEstimada;
    
    @OneToMany(mappedBy = "ruta_id")
    private List<Entrega> entregas;
}
