package com.example.GestionDeEntregas_Rutas.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.GestionDeEntregas_Rutas.DTO.entregas.AsignacionDTO;
import com.example.GestionDeEntregas_Rutas.DTO.entregas.EntregaDTO;
import com.example.GestionDeEntregas_Rutas.services.Entregas.EntregasService;

@RestController
@RequestMapping("/entregas")
public class EntregasController {

    @Autowired
    private EntregasService entregasService;

    @GetMapping
    public ResponseEntity<Page<EntregaDTO>> obtenerEntregas(@RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<EntregaDTO> entregas;

        if (estado != null && !estado.isEmpty()) {

            entregas = entregasService.obtenerEntregasPorEstado(estado, pageable);

        } else {
            entregas = entregasService.obtenerEntregas(pageable);
        }

        return ResponseEntity.ok(entregas);
    }

    @PatchMapping
    public ResponseEntity<?> asignarPedido(@RequestParam(required = true) Long pedido_id,
            @RequestHeader("Authorization") String authToken) {

        System.out.println("Entró al endpoint");
        if (authToken == null || authToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Se requiere el token del usuario");
        }

        String token = authToken.substring(7);

        try {
            AsignacionDTO nueva_asignacion = entregasService.asignarEntrega(token, pedido_id);

            return ResponseEntity.ok("Orden de entrega asignada correctamente." + nueva_asignacion);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "mensaje");

        }
    }
}
