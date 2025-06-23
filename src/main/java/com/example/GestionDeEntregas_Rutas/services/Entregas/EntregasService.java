package com.example.GestionDeEntregas_Rutas.services.Entregas;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.GestionDeEntregas_Rutas.DTO.entregas.AsignacionDTO;
import com.example.GestionDeEntregas_Rutas.DTO.entregas.EntregaDTO;
import com.example.GestionDeEntregas_Rutas.DTO.pedidos.PedidoDTO;
import com.example.GestionDeEntregas_Rutas.models.Asignacion;
import com.example.GestionDeEntregas_Rutas.models.Entrega;
import com.example.GestionDeEntregas_Rutas.models.Estado;
import com.example.GestionDeEntregas_Rutas.models.Repartidor;
import com.example.GestionDeEntregas_Rutas.repositories.AsignacionRepository;
import com.example.GestionDeEntregas_Rutas.repositories.EntregasRepository;
import com.example.GestionDeEntregas_Rutas.repositories.EstadoRepository;
import com.example.GestionDeEntregas_Rutas.repositories.RepartidorRepository;
import com.example.GestionDeEntregas_Rutas.security.JwtUtil;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@Service
public class EntregasService {

    @Autowired
    private EntregasRepository entregasRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Autowired
    private EntregaProducer entregaProducer;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public Entrega crearEntrega(PedidoDTO pedido) {
        try {

            Estado estado_pendiente = estadoRepository.findByNombre("PENDIENTE").orElseThrow(
                    () -> new NotFoundException(
                            "No se ha encontrado el estado PENDIENTE entre los estados de entrega."));

            Entrega nueva_entrega = new Entrega();
            nueva_entrega.setEstadoId(estado_pendiente.getId_estado());
            nueva_entrega.setDireccion(pedido.getShippingAddress());
            nueva_entrega.setPedidoId(pedido.getId());
            nueva_entrega.setUserId(pedido.getUserId());

            entregasRepository.save(nueva_entrega);

            return nueva_entrega;

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al asignar  la entrega." + e.getMessage());
            throw new RuntimeException("Error al  asignar entrega", e);
        }
    }

    public Page<EntregaDTO> obtenerEntregas(Pageable pageable) {

        Page<Entrega> entregas = entregasRepository.findAll(pageable);

        return entregas.map(entrega -> {
            EntregaDTO dto = new EntregaDTO();
            dto.setId_entrega(entrega.getId_entrega());
            dto.setDireccion(entrega.getDireccion());
            dto.setUser_id(entrega.getUserId());

            Estado estado = estadoRepository.findById(entrega.getEstadoId())
                    .orElseThrow(() -> new NotFoundException("No se ha encontrado el estado de un pedido"));

            dto.setEstado(estado.getNombre());
            dto.setPedido_id(entrega.getPedidoId());
            return dto;
        });
    }

    public Page<EntregaDTO> obtenerEntregasPorEstado(String estado, Pageable pageable) {

        Estado estadoEntity = estadoRepository.findByNombre(estado)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado estados con el nombre: " + estado));

        Page<Entrega> entregas = entregasRepository.findByEstadoId(estadoEntity.getId_estado(), pageable);

        return entregas.map(entrega -> {
            EntregaDTO dto = new EntregaDTO();
            dto.setId_entrega(entrega.getId_entrega());
            dto.setDireccion(entrega.getDireccion());
            dto.setEstado(estadoEntity.getNombre());
            dto.setPedido_id(entrega.getPedidoId());
            dto.setUser_id(entrega.getUserId());
            return dto;
        });
    }

    @Transactional
    public AsignacionDTO asignarEntrega(String authToken, Long pedido_id) {
        System.out.println("Entró al microservicio");

        try {
            String user_id = jwtUtil.extractUsername(authToken);

            Long user_long = Long.parseLong(user_id);

            Repartidor repartidor = repartidorRepository.findById(user_long)
                    .orElseThrow(() -> new NotFoundException("Verifique que el usuario sea un repartidor."));

            System.out.println("Encontró repartidor," + repartidor.getNombre());

            Entrega entrega = entregasRepository.findByPedidoId(pedido_id)
                    .orElseThrow(() -> new NotFoundException("No se ha encontrado una orden asociada a este id."));

            System.out.println("Encontró entrega" + entrega.getPedidoId());

            Estado estado_ready = estadoRepository.findByNombre("ASIGNADA").orElseThrow(
                    () -> new NotFoundException("No se ha encontrado el estado ASIGNADA en la tabla estados"));

            System.out.println("Estado ASIGNADA encontrado con ID: " + estado_ready.getId_estado());


            Asignacion asignacion = new Asignacion();
            asignacion.setRepartidor_id(repartidor);
            asignacion.setEntrega_id(entrega);

            System.out.println("Asignó");

            asignacionRepository.save(asignacion);

            entrega.setFechaEntrega(LocalDate.now().plusDays(3));
            entrega.setEstadoId(estado_ready.getId_estado());

            entregasRepository.save(entrega);

            entregaProducer.enviarEntregaAsignada(pedido_id);

            entregaProducer.enviarNotificacionAsignacion(pedido_id, entrega.getUserId());

            AsignacionDTO asignacionDTO = new AsignacionDTO();
            asignacionDTO.setEntrega_id(pedido_id);
            asignacionDTO.setRepartidor_id(user_long);

            return asignacionDTO;

        }

        catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al asignar" + e.getMessage());
        }
    }
}
