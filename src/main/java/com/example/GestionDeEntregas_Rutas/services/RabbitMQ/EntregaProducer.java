package com.example.GestionDeEntregas_Rutas.services.RabbitMQ;

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

    public void enviarNotificacionAsignacion(Long order_id, Long user_id) {
        System.out.println("Ingresa a enviar la notificacion");
        System.out.println(order_id);
        System.out.println(user_id);
        NotificacionAsignacionDTO notificacion = new NotificacionAsignacionDTO();
        notificacion.setOrderId(order_id);
        notificacion.setUserId(user_id);
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NOTIFICACIONES,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACION_ASIGNACION,
                notificacion);
        System.out.println("Notificación de asignación enviada a Rabbit. Order: " + order_id + ", User: " + user_id);
    }
}
