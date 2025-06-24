/*
INSERT INTO ruta (origen, destino, duracion_estimada) VALUES
('Cali', 'Tulua', '2h'),
('Medellin', 'Cali', '15h'),
('Bogota', 'Buenaventura', '20h');

-- Insertar estados (corregido)
INSERT INTO estado (nombre) VALUES
('Pendiente'),
('En camino'),
('Entregado'),
('Cancelado'),
('Asignado');

-- Insertar repartidor
INSERT INTO repartidor (id_repartidor, nombre, licencia, telefono) VALUES
(1, 'jairo reparte', 'XYZ789', '555-5678');

-- Insertar entregas
INSERT INTO entrega (fecha_salida, fecha_entrega, id_estado, id_ruta, id_pedido, user_id, direccion) VALUES
('2025-05-22', '2025-05-23', 1, 1, 1001, 1, 'Carrera 7 #72-41, Bogotá'),
('2025-05-21', '2025-05-23', 1, 2, 1002, 1, 'Calle 13 #68-98, Bogotá'),
('2025-05-20', '2025-05-22', 1, 3, 1003, 3, 'Carrera 100 #19-60, Bogotá'),
('2025-05-20', '2025-05-25', 1, 1, 1004, 3, 'Calle 183 #45-03, Bogotá'),  
('2025-05-21', '2025-05-24', 1, 2, 1005, 1, 'Carrera 24 #76-20, Bogotá');

-- Insertar asignaciones
INSERT INTO asignacion (id_entrega, id_repartidor) VALUES
(1, 1),
(2, 1),
(4, 1),  
(5, 1);
*/