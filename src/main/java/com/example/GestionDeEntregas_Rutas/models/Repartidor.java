package com.example.GestionDeEntregas_Rutas.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "repartidor")
public class Repartidor {
    
    @Id
    private Long id_repartidor;

    private String nombre;

    private String licencia;
    
    private String telefono;

    @OneToMany(mappedBy = "repartidor_id")
    private List<Asignacion> asignaciones;

    private String estado;
}
