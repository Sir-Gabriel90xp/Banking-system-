# Banking System

Proyecto base para el sistema bancario.


# 🏦 Banking System

> Sistema bancario Full Stack desarrollado con tecnologías modernas, arquitectura limpia y buenas prácticas de ingeniería de software.

---

## 📖 Descripción

Banking System es un proyecto desarrollado con fines educativos y de portafolio, diseñado para simular el funcionamiento de un sistema bancario moderno.

El objetivo es aplicar conceptos utilizados en proyectos empresariales reales, incluyendo autenticación segura, gestión de cuentas bancarias, transferencias, préstamos, auditoría, mensajería asíncrona y detección de fraude.

Este proyecto prioriza la calidad del código, la escalabilidad, la documentación y la mantenibilidad.

---

# 🎯 Objetivos

- Construir una aplicación Full Stack moderna.
- Aplicar Arquitectura por Capas.
- Implementar principios SOLID.
- Desarrollar una API REST profesional.
- Implementar autenticación con JWT.
- Utilizar RabbitMQ para comunicación asíncrona.
- Dockerizar toda la aplicación.
- Documentar completamente el proyecto.
- Simular procesos bancarios reales.

---

# 🛠 Tecnologías

## Frontend

- Angular

## Backend

- Java
- Spring Boot

## Base de Datos

- PostgreSQL

## Seguridad

- Spring Security
- JWT

## Mensajería

- RabbitMQ

## Contenedores

- Docker
- Docker Compose

## Documentación

- Swagger / OpenAPI

## Control de Versiones

- Git
- GitHub

---

# 🏗 Arquitectura

El proyecto sigue una arquitectura por capas.

```text
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

Cada capa tiene una única responsabilidad para mantener un código limpio, escalable y fácil de mantener.

---

# 📂 Estructura del Proyecto

```text
banking-system/

├── backend/
├── frontend/
├── database/
├── docker/
├── docs/
├── diagrams/
├── screenshots/
├── README.md
├── PROJECT_CONTEXT.md
└── AI_HANDOFF.md
```

---

# 🚀 Funcionalidades

## Autenticación

- Login
- Registro
- JWT
- Roles
- Permisos

## Clientes

- Registro
- Actualización
- Consulta
- Eliminación lógica

## Cuentas Bancarias

- Crear cuentas
- Consultar saldo
- Bloquear cuentas
- Activar cuentas

## Transferencias

- Transferencias entre cuentas
- Validaciones
- Historial

## Préstamos

- Solicitudes
- Aprobaciones
- Pagos
- Historial

## Pagos

- Pago de préstamos
- Pago de servicios

## Auditoría

- Registro de acciones
- Historial completo
- Logs

## Seguridad

- JWT
- Spring Security
- Roles
- Permisos

## Detección de Fraude

- Bloqueo automático de cuentas
- Detección de transacciones sospechosas
- Alertas

---

# 📚 Documentación

Toda la documentación técnica se encuentra dentro de la carpeta:

```text
docs/
```

Incluye:

- Requisitos
- Arquitectura
- Modelo de Datos
- API
- Roadmap
- Bitácora
- Convenciones

---

# 📈 Estado del Proyecto

Actualmente el proyecto se encuentra en fase de planificación y documentación.

Antes de desarrollar cualquier funcionalidad se definirá completamente la arquitectura, la base de datos y la API.

---

# 🎯 Objetivos Técnicos

Este proyecto busca aplicar:

- Clean Code
- SOLID
- Arquitectura por Capas
- REST API
- DTO
- Mapper
- Validaciones
- Manejo Global de Excepciones
- Docker
- RabbitMQ
- PostgreSQL
- Swagger

---

# 🤝 Contribuciones

Actualmente este proyecto es de uso personal y forma parte de un portafolio profesional.

---

# 📄 Licencia

Proyecto desarrollado con fines educativos.
