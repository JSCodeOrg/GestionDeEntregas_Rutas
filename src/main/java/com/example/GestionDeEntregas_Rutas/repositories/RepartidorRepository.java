package com.example.GestionDeEntregas_Rutas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionDeEntregas_Rutas.models.Repartidor;

public interface RepartidorRepository extends JpaRepository<Repartidor, Long>{
    Optional<Repartidor> findByidRepartidor(Long idRepartidor);

    
}
