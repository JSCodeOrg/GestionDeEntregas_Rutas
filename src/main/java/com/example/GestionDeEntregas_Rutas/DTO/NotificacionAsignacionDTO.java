package com.example.GestionDeEntregas_Rutas.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacionAsignacionDTO {
    private Long orderId;
    private Long userId;
    private String token;
}