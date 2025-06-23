-- ========================================
-- SCRIPT DE DATOS DE PRUEBA
-- ========================================

USE escuela_sistema;

-- Insertar usuarios de prueba
INSERT INTO usuarios (username, password, nombre, email, tipo) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRuvO.H.6ba', 'Administrador Principal', 'admin@escuelita.com', 'ADMIN'),
('padre1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRuvO.H.6ba', 'María García', 'maria.garcia@email.com', 'PADRE'),
('padre2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRuvO.H.6ba', 'Juan López', 'juan.lopez@email.com', 'PADRE');

-- Insertar estudiantes de prueba
INSERT INTO estudiantes (
    nombres, apellidos, dni, fecha_nacimiento, nivel_educativo,
    nombre_apoderado, telefono_apoderado, email_apoderado, direccion,
    estado_pago, ano_academico, es_traslado
) VALUES
('Ana', 'García Pérez', '12345678', '2020-03-15', 'INICIAL_4_ANOS',
 'María García', '987654321', 'maria.garcia@email.com', 'Av. Los Olivos 123, Lima',
 'AL_DIA', '2024', FALSE),

('Carlos', 'López Mendoza', '87654321', '2018-07-22', 'PRIMARIO_PRIMER_ANO',
 'Juan López', '912345678', 'juan.lopez@email.com', 'Jr. Las Flores 456, Lima',
 'PENDIENTE', '2024', FALSE),

('Diego', 'Martínez Torres', '55667788', '2016-09-05', 'PRIMARIO_TERCER_ANO',
 'Carmen Torres', '955443322', 'carmen.torres@email.com', 'Av. Principal 321, Lima',
 'PENDIENTE', '2024', TRUE);

-- Actualizar datos adicionales para el estudiante de traslado
UPDATE estudiantes SET 
    codigo_modular = '1234567',
    institucion_anterior = 'I.E. San José',
    motivo_traslado = 'Cambio de domicilio familiar',
    informacion_medica = 'Alérgico a los frutos secos',
    contacto_emergencia = 'Pedro Martínez',
    telefono_emergencia = '911223344'
WHERE dni = '55667788';

-- Insertar pagos de prueba
INSERT INTO pagos (
    estudiante_id, concepto, monto, fecha_vencimiento, mes, ano, pagado, fecha_pago, metodo_pago
) VALUES
(1, 'Mensualidad Marzo', 350.00, '2024-03-05', 'MARZO', '2024', TRUE, '2024-03-03', 'TRANSFERENCIA'),
(1, 'Mensualidad Abril', 350.00, '2024-04-05', 'ABRIL', '2024', TRUE, '2024-04-02', 'EFECTIVO'),
(1, 'Mensualidad Mayo', 350.00, '2024-05-05', 'MAYO', '2024', FALSE, NULL, 'EFECTIVO'),

(2, 'Mensualidad Marzo', 350.00, '2024-03-05', 'MARZO', '2024', FALSE, NULL, 'EFECTIVO'),
(2, 'Mensualidad Abril', 350.00, '2024-04-05', 'ABRIL', '2024', FALSE, NULL, 'EFECTIVO'),

(3, 'Mensualidad Abril', 350.00, '2024-04-05', 'ABRIL', '2024', FALSE, NULL, 'EFECTIVO'),
(3, 'Mensualidad Mayo', 350.00, '2024-05-05', 'MAYO', '2024', FALSE, NULL, 'EFECTIVO');

SELECT 'Datos de prueba insertados exitosamente' AS mensaje;
SELECT COUNT(*) AS total_usuarios FROM usuarios;
SELECT COUNT(*) AS total_estudiantes FROM estudiantes;
SELECT COUNT(*) AS total_pagos FROM pagos;
