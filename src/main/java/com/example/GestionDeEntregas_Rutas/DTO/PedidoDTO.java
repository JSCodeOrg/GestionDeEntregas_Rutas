package com.example.GestionDeEntregas_Rutas.DTO;

import java.io.Serializable;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class PedidoDTO implements Serializable {
    private Long id;
    private String shippingAddress;
}
