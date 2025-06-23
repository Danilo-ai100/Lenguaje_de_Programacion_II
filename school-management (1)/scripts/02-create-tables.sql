-- ========================================
-- SCRIPT DE CREACIÓN DE TABLAS
-- (Opcional - Hibernate las crea automáticamente)
-- ========================================

USE escuela_sistema;

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    tipo ENUM('ADMIN', 'PADRE') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Estudiantes
CREATE TABLE IF NOT EXISTS estudiantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nivel_educativo ENUM(
        'INICIAL_3_ANOS', 'INICIAL_4_ANOS', 'INICIAL_5_ANOS',
        'PRIMARIO_PRIMER_ANO', 'PRIMARIO_SEGUNDO_ANO', 'PRIMARIO_TERCER_ANO',
        'PRIMARIO_CUARTO_ANO', 'PRIMARIO_QUINTO_ANO', 'PRIMARIO_SEXTO_ANO'
    ) NOT NULL,
    nombre_apoderado VARCHAR(100) NOT NULL,
    telefono_apoderado VARCHAR(15) NOT NULL,
    email_apoderado VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    estado_pago ENUM('AL_DIA', 'PENDIENTE', 'ATRASADO') DEFAULT 'PENDIENTE',
    ano_academico VARCHAR(4) NOT NULL,
    es_traslado BOOLEAN DEFAULT FALSE,
    codigo_modular VARCHAR(20),
    institucion_anterior VARCHAR(200),
    motivo_traslado TEXT,
    informacion_medica TEXT,
    contacto_emergencia VARCHAR(100),
    telefono_emergencia VARCHAR(15),
    activo BOOLEAN DEFAULT TRUE,
    fecha_matricula TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Pagos
CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    concepto VARCHAR(100) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    fecha_pago DATE,
    metodo_pago ENUM('EFECTIVO', 'TRANSFERENCIA', 'TARJETA') DEFAULT 'EFECTIVO',
    numero_comprobante VARCHAR(50),
    observaciones TEXT,
    mes VARCHAR(20) NOT NULL,
    ano VARCHAR(4) NOT NULL,
    pagado BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE
);

-- Índices para mejorar rendimiento
CREATE INDEX idx_estudiantes_dni ON estudiantes(dni);
CREATE INDEX idx_estudiantes_nivel ON estudiantes(nivel_educativo);
CREATE INDEX idx_estudiantes_ano ON estudiantes(ano_academico);
CREATE INDEX idx_pagos_estudiante ON pagos(estudiante_id);
CREATE INDEX idx_pagos_fecha ON pagos(fecha_vencimiento);
CREATE INDEX idx_pagos_mes_ano ON pagos(mes, ano);

SELECT 'Tablas creadas exitosamente' AS mensaje;
