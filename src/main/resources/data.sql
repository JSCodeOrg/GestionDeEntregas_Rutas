-- Insertar rutas
INSERT INTO ruta (origen, destino, duracion_estimada) VALUES
('Cali', 'Tulua', '2h'),
('Medellin', 'Cali', '15h'),
('Bogota', 'Buenaventura', '20h');

-- Insertar estados
INSERT INTO estado (nombre) VALUES
('Pendiente'),
('En camino'),
('Entregado'),
('Cancelado');

-- Insertar repartidores
INSERT INTO repartidor (id_repartidor, nombre, licencia, telefono) VALUES
(1, 'Juan Pérez', 'ABC123', '555-1234'),
(2, 'María Gómez', 'XYZ789', '555-5678');

-- Insertar entregas
-- Asumiendo que los IDs de estado insertados son 1: Pendiente, 2: En camino, 3: Entregado
INSERT INTO entrega (fecha_salida, fecha_entrega, id_estado, id_ruta, id_pedido) VALUES
('2025-05-22', '2025-05-23', 1, 1, 1001),
('2025-05-21', '2025-05-23', 2, 2, 1002),
('2025-05-20', '2025-05-22', 3, 3, 1003);

-- Insertar asignaciones
INSERT INTO asignacion (id_entrega, id_repartidor) VALUES
(1, 1),
(2, 2);
