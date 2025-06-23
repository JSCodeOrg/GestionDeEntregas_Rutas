package com.example.GestionDeEntregas_Rutas.services;

import com.example.GestionDeEntregas_Rutas.DTO.repartidor.RepartidorDTO;
import com.example.GestionDeEntregas_Rutas.models.Repartidor;
import com.example.GestionDeEntregas_Rutas.repositories.RepartidorRepository;
import com.example.GestionDeEntregas_Rutas.services.repartidor.RepartidorService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class RepartidorServiceTest {

    @Mock
    private RepartidorRepository repartidorRepository;

    @InjectMocks
    private RepartidorService repartidorService;

    public RepartidorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearRepartidor() {
        // Arrange
        RepartidorDTO dto = new RepartidorDTO();
        dto.setUser_id(1L);
        dto.setName("Juan Pérez");
        dto.setPhone("3001234567");

        // Act
        Repartidor resultado = repartidorService.crearRepartidor(dto);

        // Assert
        assertEquals(dto.getUser_id(), resultado.getIdRepartidor());
        assertEquals(dto.getName(), resultado.getNombre());
        assertEquals(dto.getPhone(), resultado.getTelefono());
        assertEquals("ACTIVO", resultado.getEstado());

        // Verifica que se llamó a save() con el repartidor creado
        verify(repartidorRepository).save(resultado);
    }
}
