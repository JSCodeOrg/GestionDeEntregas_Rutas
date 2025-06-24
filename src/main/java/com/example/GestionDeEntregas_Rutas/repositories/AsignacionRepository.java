package com.example.GestionDeEntregas_Rutas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.GestionDeEntregas_Rutas.models.Asignacion;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    @Query("SELECT a FROM Asignacion a WHERE a.repartidor_id.idRepartidor = :id")
    List<Asignacion> buscarPorIdRepartidor(@Param("id") Long idRepartidor);
}
