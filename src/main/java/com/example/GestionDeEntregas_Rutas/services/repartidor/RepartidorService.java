package com.example.GestionDeEntregas_Rutas.services.repartidor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionDeEntregas_Rutas.DTO.repartidor.RepartidorDTO;
import com.example.GestionDeEntregas_Rutas.models.Repartidor;
import com.example.GestionDeEntregas_Rutas.repositories.RepartidorRepository;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    public Repartidor crearRepartidor(RepartidorDTO repartidorData) {

        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor(repartidorData.getUser_id());
        repartidor.setNombre(repartidorData.getName());
        repartidor.setTelefono(repartidorData.getPhone());
        repartidor.setEstado("ACTIVO");

        repartidorRepository.save(repartidor);
        return repartidor;
    }
}

