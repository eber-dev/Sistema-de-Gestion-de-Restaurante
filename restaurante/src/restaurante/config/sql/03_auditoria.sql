-- Actualización no destructiva para una base existente.
-- Ejecute primero USE restaurante_intermedio; o seleccione la base correcta.
CREATE TABLE IF NOT EXISTS auditoria (
    id_auditoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    entidad VARCHAR(40) NOT NULL,
    id_registro VARCHAR(80) NOT NULL,
    accion VARCHAR(30) NOT NULL,
    detalle VARCHAR(255),
    usuario_bd VARCHAR(255) NOT NULL,
    INDEX idx_auditoria_fecha (fecha_hora),
    INDEX idx_auditoria_entidad (entidad, id_registro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
