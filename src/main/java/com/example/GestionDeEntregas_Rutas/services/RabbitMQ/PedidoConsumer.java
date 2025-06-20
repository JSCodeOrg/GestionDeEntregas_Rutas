package com.example.GestionDeEntregas_Rutas.services.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.example.GestionDeEntregas_Rutas.DTO.PedidoDTO;
import com.example.GestionDeEntregas_Rutas.config.RabbitMQConfig;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void recibirMensaje(PedidoDTO pedido){
        System.out.println("Pedido recibido:" + pedido.getId() + pedido.getShippingAddress());
    }
}
