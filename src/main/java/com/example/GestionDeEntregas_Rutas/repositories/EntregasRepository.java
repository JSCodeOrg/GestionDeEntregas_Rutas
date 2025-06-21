package com.example.GestionDeEntregas_Rutas.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestionDeEntregas_Rutas.models.Entrega;

public interface EntregasRepository extends JpaRepository<Entrega, Long>{
        Page<Entrega> findByEstadoId(Long estadoId, Pageable pageable);

        Optional<Entrega> findByPedidoId(Long pedidoId);
    
}
