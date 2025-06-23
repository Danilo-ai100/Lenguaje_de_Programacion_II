-- ========================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS MYSQL
-- Sistema de Gestión Escolar "Escuelita Feliz"
-- ========================================

-- Crear la base de datos
DROP DATABASE IF EXISTS escuela_sistema;
CREATE DATABASE escuela_sistema 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE escuela_sistema;

-- Crear usuario específico para la aplicación
DROP USER IF EXISTS 'escuela_user'@'localhost';
CREATE USER 'escuela_user'@'localhost' IDENTIFIED BY 'escuela_password_2024';

-- Otorgar permisos completos al usuario
GRANT ALL PRIVILEGES ON escuela_sistema.* TO 'escuela_user'@'localhost';
FLUSH PRIVILEGES;

-- Verificar la creación
SELECT 'Base de datos escuela_sistema creada exitosamente' AS mensaje;
SELECT USER() AS usuario_actual;
SELECT DATABASE() AS base_datos_actual;
