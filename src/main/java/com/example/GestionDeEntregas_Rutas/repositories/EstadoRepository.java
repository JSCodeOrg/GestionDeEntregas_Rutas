package com.example.GestionDeEntregas_Rutas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionDeEntregas_Rutas.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByNombre(String nombre);

}
