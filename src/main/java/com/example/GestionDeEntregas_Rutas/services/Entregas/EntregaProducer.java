package com.example.GestionDeEntregas_Rutas.services.Entregas;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.GestionDeEntregas_Rutas.config.RabbitMQConfig;

@Service
public class EntregaProducer {

    private final RabbitTemplate rabbitTemplate;

    public EntregaProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEntregaAsignada(Long order_id){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_ENTREGAS,
        RabbitMQConfig.ROUTING_KEY_ENTREGA_ASIGNADA,
        order_id);
        System.out.println("Orden asignada a repartidor y enviada a Rabbit:" + order_id);
    }
}
