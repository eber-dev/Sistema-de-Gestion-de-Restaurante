/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  EBER
 * Created: 1 nov. 2026
 */


INSERT IGNORE INTO categorias (id_categoria, nombre, descripcion, id_categoria_padre) VALUES
(1, 'Bebidas', 'Bebidas frías y calientes', NULL),
(2, 'Entradas', 'Platos de entrada', NULL),
(3, 'Platos Fuertes', 'Platos principales', NULL),
(4, 'Postres', 'Postres y dulces', NULL);

INSERT IGNORE INTO clientes (id_cliente, nombre, apellido, telefono, correo, direccion) VALUES
(1, 'Juan', 'Pérez', '987654321', 'juan.perez@mail.com', 'Av. Los Álamos 123'),
(2, 'María', 'Gómez', '912345678', 'maria.gomez@mail.com', 'Jr. Las Flores 456'),
(3, 'Carlos', 'Ramírez', '999888777', 'carlos.ramirez@mail.com', 'Calle Sol 789');

INSERT IGNORE INTO empleados (id_empleado, nombre, apellido, dni, telefono, cargo, salario, estado) VALUES
(1, 'Admin', 'Sistema', '00000000', '900000000', 'Administrador', 2500.00, 'Activo'),
(2, 'Luis', 'Cajero', '11111111', '900000001', 'Cajero', 1800.00, 'Activo'),
(3, 'Ana', 'Mozo', '22222222', '900000002', 'Mozo', 1500.00, 'Activo');

INSERT IGNORE INTO usuarios (id_usuario, id_empleado, nombre_usuario, clave, rol, estado) VALUES
(1, 1, 'admin', 'admin123', 'Administrador', 'Activo'),
(2, 2, 'cajero', 'cajero123', 'Cajero', 'Activo'),
(3, 3, 'mozo', 'mozo123', 'Mozo', 'Activo');

INSERT IGNORE INTO productos (id_producto, id_categoria, nombre, descripcion, precio, estado) VALUES
(1, 2, 'Ceviche Clásico', 'Pescado fresco en leche de tigre', 25.50, 'Disponible'),
(2, 3, 'Lomo Saltado', 'Carne salteada con papas y arroz', 32.00, 'Disponible'),
(3, 1, 'Inca Kola 500ml', 'Gaseosa peruana', 5.00, 'Disponible'),
(4, 4, 'Suspiro Limeño', 'Postre tradicional', 12.00, 'Disponible'),
(5, 2, 'Anticucho', 'Brochetas de corazón', 18.00, 'No disponible');

INSERT IGNORE INTO mesas (id_mesa, numero_mesa, capacidad, estado) VALUES
(1, 1, 4, 'Libre'),
(2, 2, 4, 'Libre'),
(3, 3, 2, 'Libre'),
(4, 4, 6, 'Libre'),
(5, 5, 6, 'Libre');