package com.example.GestionDeEntregas_Rutas.services;

import com.example.GestionDeEntregas_Rutas.DTO.pedidos.PedidoDTO;
import com.example.GestionDeEntregas_Rutas.models.Entrega;
import com.example.GestionDeEntregas_Rutas.models.Estado;
import com.example.GestionDeEntregas_Rutas.repositories.*;

import com.example.GestionDeEntregas_Rutas.security.JwtUtil;
import com.example.GestionDeEntregas_Rutas.services.Entregas.EntregasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EntregasServiceTest {

    @Mock private EntregasRepository entregasRepository;
    @Mock private EstadoRepository estadoRepository;
    @Mock private AsignacionRepository asignacionRepository;
    @Mock private RepartidorRepository repartidorRepository;
    @Mock private Object entregaProducer;

    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private EntregasService entregasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicializa todos los @Mock
    }

    @Test
    void testCrearEntrega_ok() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setShippingAddress("Calle Principal 123");

        Estado estado = new Estado();
        estado.setId_estado(99L);
        estado.setNombre("PENDIENTE");

        when(estadoRepository.findByNombre("PENDIENTE")).thenReturn(Optional.of(estado));
        when(entregasRepository.save(any())).thenAnswer(i -> i.getArgument(0)); // retorna misma entrega

        Entrega resultado = entregasService.crearEntrega(pedido);

        assertNotNull(resultado);
        assertEquals("Calle Principal 123", resultado.getDireccion());
        assertEquals(1L, resultado.getPedidoId());
        assertEquals(99L, resultado.getEstadoId());

        verify(entregasRepository, times(1)).save(any());
    }

    @Test
    void testCrearEntrega_estadoNoExiste() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(2L);
        pedido.setShippingAddress("Sin Estado");

        when(estadoRepository.findByNombre("PENDIENTE")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            entregasService.crearEntrega(pedido);
        });

        assertTrue(ex.getMessage().contains("Error al  asignar entrega"));
    }
}
