package com.example.GestionDeEntregas_Rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestionDeEntregas_Rutas.DTO.repartidor.RepartidorDTO;
import com.example.GestionDeEntregas_Rutas.models.Repartidor;
import com.example.GestionDeEntregas_Rutas.services.repartidor.RepartidorService;

@RestController
@RequestMapping("/repartidor")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @PostMapping
    public ResponseEntity<?> registrarRepartidor(@RequestBody RepartidorDTO repartidorData) {

        try {
            Repartidor nuevo_repartidor = repartidorService.crearRepartidor(repartidorData);

            return ResponseEntity.ok("Perfil de repartidor creado:" + nuevo_repartidor);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Ocurrió un error al crear el perfil de repartidor al nuevo usuario.");
        }

    }

}
