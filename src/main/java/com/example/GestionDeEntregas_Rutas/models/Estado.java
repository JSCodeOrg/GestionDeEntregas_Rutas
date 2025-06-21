package com.example.GestionDeEntregas_Rutas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    
    private Long id_estado;
    
    private String nombre;
    
}
