package com.example.GestionDeEntregas_Rutas.services;

import com.example.GestionDeEntregas_Rutas.DTO.pedidos.PedidoDTO;
import com.example.GestionDeEntregas_Rutas.services.Entregas.EntregasService;
import com.example.GestionDeEntregas_Rutas.services.RabbitMQ.PedidoConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoConsumerTest {

    private PedidoConsumer pedidoConsumer;
    private EntregasService entregasService;

    @BeforeEach
    void setUp() {
        entregasService = mock(EntregasService.class);
        pedidoConsumer = new PedidoConsumer();
        
        // Inyección manual del mock
        pedidoConsumer.getClass().getDeclaredFields();
        try {
            var field = PedidoConsumer.class.getDeclaredField("entregasService");
            field.setAccessible(true);
            field.set(pedidoConsumer, entregasService);
        } catch (Exception e) {
            throw new RuntimeException("Error inyectando mock", e);
        }
    }

    @Test
    void testRecibirMensaje_llamaCrearEntrega() throws Exception {
        PedidoDTO pedidoMock = new PedidoDTO();
        pedidoMock.setId(1L);
        pedidoMock.setShippingAddress("Calle Falsa 123");

        pedidoConsumer.recibirMensaje(pedidoMock);

        verify(entregasService, times(1)).crearEntrega(pedidoMock);
    }

    @Test
    void testRecibirMensaje_lanzaExcepcionSiCrearFalla() throws Exception {
        PedidoDTO pedidoMock = new PedidoDTO();
        pedidoMock.setId(2L);
        pedidoMock.setShippingAddress("Avenida Siempre Viva");

        doThrow(new RuntimeException("fallo interno")).when(entregasService).crearEntrega(any());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidoConsumer.recibirMensaje(pedidoMock);
        });

        assertTrue(ex.getMessage().contains("Ocurrió un error"));
    }
}
