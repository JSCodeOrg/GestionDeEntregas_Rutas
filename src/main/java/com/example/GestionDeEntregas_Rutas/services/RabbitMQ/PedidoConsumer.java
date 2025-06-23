package com.example.GestionDeEntregas_Rutas.services.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.GestionDeEntregas_Rutas.DTO.pedidos.PedidoDTO;
import com.example.GestionDeEntregas_Rutas.config.RabbitMQConfig;
import com.example.GestionDeEntregas_Rutas.services.Entregas.EntregasService;

import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @Autowired
    private EntregasService entregasService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS)
    public void recibirMensaje(PedidoDTO pedido) {
        System.out.println("Pedido recibido:" + pedido.getId() + pedido.getShippingAddress() + pedido.getUserId());

        try {
            entregasService.crearEntrega(pedido);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error al asignar la entrega del pedido. " + e.getMessage());
        }

    }
}
