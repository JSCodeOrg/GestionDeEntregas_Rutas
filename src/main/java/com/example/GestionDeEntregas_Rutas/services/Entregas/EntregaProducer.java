package com.example.GestionDeEntregas_Rutas.services.Entregas;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.GestionDeEntregas_Rutas.config.RabbitMQConfig;
import com.example.GestionDeEntregas_Rutas.DTO.NotificacionAsignacionDTO;

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

    public void enviarNotificacionAsignacion(Long order_id, Long user_id) {
        NotificacionAsignacionDTO notificacion = new NotificacionAsignacionDTO();
        notificacion.setOrderId(order_id);
        notificacion.setUserId(user_id);
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_ENTREGAS,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACION_ASIGNACION,
                notificacion);
        System.out.println("Notificación de asignación enviada a Rabbit. Order: " + order_id + ", User: " + user_id);
    }
}
