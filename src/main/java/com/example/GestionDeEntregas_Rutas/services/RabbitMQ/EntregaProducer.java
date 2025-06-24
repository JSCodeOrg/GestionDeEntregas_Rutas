package com.example.GestionDeEntregas_Rutas.services.RabbitMQ;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionDeEntregas_Rutas.config.RabbitMQConfig;
import com.example.GestionDeEntregas_Rutas.security.JwtUtil;
import com.example.GestionDeEntregas_Rutas.DTO.NotificacionAsignacionDTO;

@Service
public class EntregaProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private JwtUtil jwtUtil;

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
        List<Long> roles = Arrays.asList(1L);
        String provisionaltoken = jwtUtil.generateToken(user_id, roles);
        notificacion.setToken(provisionaltoken);
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NOTIFICACIONES,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACION_ASIGNACION,
                notificacion);
        System.out.println("Notificación de asignación enviada a Rabbit. Order: " + order_id + ", User: " + user_id + "Token enviado:" + provisionaltoken);
    }
}
