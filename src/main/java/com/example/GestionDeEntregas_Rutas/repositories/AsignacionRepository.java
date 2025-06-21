package com.example.GestionDeEntregas_Rutas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionDeEntregas_Rutas.models.Asignacion;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    
}
