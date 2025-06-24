package com.example.GestionDeEntregas_Rutas.DTO.entregas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaDTO {
    
    private Long id_entrega;

    private String estado;

    private Long Pedido_id;

    private String direccion;

    private Long user_id;

}
