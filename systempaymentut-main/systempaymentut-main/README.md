# Sistema de Pagos Académicos

Aplicación **Full Stack** para gestionar los pagos académicos de estudiantes de la Universidad.
Permite registrar, consultar y validar pagos como matrícula, pensiones, derechos de grado, entre otros, garantizando trazabilidad y control administrativo.

## 🚀 Tecnologías utilizadas
- **Backend:** Java + Spring Boot
- **Frontend:** Angular
- **Base de datos:** Relacional (PostgreSQL o MySQL)
- **Comunicación:** API REST (HTTP + JSON)

## 🎯 Objetivo del Proyecto
Desarrollar una aplicación web que permita a la Universidad gestionar los pagos académicos de sus estudiantes, aplicando buenas prácticas de desarrollo, arquitectura y diseño de base de datos.

## 📚 Funcionalidades principales

### Módulo Estudiantes
- Registrar estudiantes con datos personales y académicos.
- Listar y consultar estudiantes por documento o código institucional.
- Actualizar o eliminar estudiantes.

### Módulo Pagos
- Registrar pagos indicando concepto, valor y tipo.
- Validar o rechazar pagos.
- Listar y filtrar pagos por estado, tipo, estudiante o fecha.

## 📐 Arquitectura de Software

- Arquitectura por Capas (Layered Architecture)
- **Backend (Spring Boot):**
  - Capas: DTO, Controlador, Servicio, Repositorio, Entidad, Enum
  - Uso de JPA para persistencia
  - Validación de datos del frontend

## 🗄️ Base de Datos

- Relación 1 Estudiante ➔ Múltiples Pagos
- Entidades principales:
  - `Estudiante`: id, nombre, email, programa, etc.
  - `Pago`: id, estudiante_id, concepto, valor, typePago, pagoStatus, fecha

## 🔒 Seguridad (en desarrollo)
- Integración futura con JWT (JSON Web Token)
- Guards en Angular para proteger rutas según rol

## 📊 Requerimientos del Sistema

### Funcionales
- Registro y gestión de estudiantes y pagos
- Validación de pagos
- Filtrado y consultas avanzadas
- Interfaz amigable y responsiva

### No Funcionales
- Tiempo de respuesta menor a 2 segundos
- Escalable y mantenible
- Validaciones de datos en todas las capas

## 📈 Ventajas de la Arquitectura
- Separación clara entre frontend y backend
- Escalable (posible migración a microservicios)
- Código organizado y mantenible
- Compatible con integración de autenticación y control de roles

## 🚀 Cómo contribuir
1. Clona este repositorio:
    ```bash
    git clone https://github.com/carlosvelezdev/systempaymentut.git
    ```
2. Crea tu rama de trabajo:
    ```bash
    git checkout -b mi-nueva-funcionalidad
    ```
3. Sube tus cambios:
    ```bash
    git push origin mi-nueva-funcionalidad
    ```

---

## 📄 Licencia
Este proyecto es desarrollado con fines educativos y puede ser adaptado por la comunidad universitaria.
