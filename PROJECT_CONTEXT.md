# Project Context

# PROJECT_CONTEXT.md

# Sistema Bancario - Contexto General del Proyecto

> Documento maestro del proyecto.
>
> Este archivo contiene toda la información necesaria para comprender el propósito, arquitectura, tecnologías, reglas de desarrollo y flujo de trabajo del proyecto.
>
> Cualquier desarrollador o Inteligencia Artificial debe leer este documento antes de realizar cualquier modificación.

---

# Regla permanente de documentacion

Todo avance del proyecto debe quedar guardado en `docs/` antes de cerrar una sesion de trabajo.

Minimo esperado por cada cambio relevante:

- actualizar `AI_HANDOFF.md` con resumen, verificacion y siguiente paso;
- actualizar `docs/management/tareas.md` con completado/pendiente;
- actualizar `docs/management/bitacora.md` cuando se implemente, corrija o cambie comportamiento;
- crear o actualizar el documento de API correspondiente en `docs/api/` si se toca un contrato backend/frontend;
- registrar una decision en `docs/management/decisiones.md` cuando se tome una decision tecnica que afecte comportamiento, arquitectura o configuracion.

---

# 1. Información General

## Nombre del Proyecto

Banking System

## Tipo de proyecto

Proyecto Full Stack empresarial.

## Estado

En planificación.

## Objetivo principal

Desarrollar un sistema bancario moderno utilizando tecnologías actuales, aplicando buenas prácticas de desarrollo de software, arquitectura limpia y principios SOLID.

Este proyecto tiene fines educativos y servirá como proyecto principal del portafolio profesional en GitHub.

---

# 2. Objetivos del Proyecto

El proyecto busca demostrar conocimientos en:

- Desarrollo Backend
- Desarrollo Frontend
- Arquitectura de Software
- Bases de Datos Relacionales
- Seguridad
- Mensajería Asíncrona
- Contenedores
- Documentación
- Buenas prácticas
- Clean Code
- SOLID
- Diseño de APIs REST

---

# 3. Stack Tecnológico

## Frontend

- Angular

## Backend

- Java
- Spring Boot

## Base de Datos

- PostgreSQL

## Mensajería

- RabbitMQ

## Seguridad

- Spring Security
- JWT

## Contenedores

- Docker
- Docker Compose

## Documentación

- Swagger / OpenAPI

## Control de Versiones

- Git
- GitHub

## IDE

- Visual Studio Code

---

# 4. Objetivos de Aprendizaje

Durante el desarrollo se busca aprender y aplicar:

- Arquitectura por capas
- DTOs
- Mappers
- Validaciones
- Manejo global de excepciones
- Relaciones entre entidades
- Eventos asíncronos
- Auditoría
- Registro de logs
- Autenticación
- Autorización
- Optimización de consultas
- Dockerización
- Documentación profesional

---

# 5. Arquitectura General

El sistema seguirá una arquitectura en capas.

Controller

↓

Service

↓

Repository

↓

Database

Cada capa tendrá una única responsabilidad.

No se permitirá lógica de negocio dentro de los Controllers.

---

# 6. Principios de Desarrollo

El proyecto deberá seguir:

- SOLID
- Clean Code
- Separation of Concerns
- DRY
- KISS
- RESTful API

---

# 7. Convenciones

## Idioma del código

Inglés

## Idioma de la documentación

Español

## Variables

camelCase

## Clases

PascalCase

## Endpoints

REST

Ejemplo:

/api/v1/auth/login

/api/v1/accounts

/api/v1/transfers

---

# 8. Estructura General del Proyecto

banking-system/

docs/

backend/

frontend/

database/

docker/

diagrams/

screenshots/

README.md

PROJECT_CONTEXT.md

AI_HANDOFF.md

---

# 9. Módulos del Sistema

El sistema estará compuesto por los siguientes módulos:

- Autenticación
- Usuarios
- Roles
- Clientes
- Cuentas Bancarias
- Transferencias
- Historial
- Préstamos
- Pagos
- Auditoría
- Detección de Fraude
- Dashboard Administrativo

Cada módulo deberá ser independiente y mantener responsabilidades claramente separadas.

---

# 10. Flujo General del Sistema

Cliente

↓

Angular

↓

REST API

↓

Spring Boot

↓

PostgreSQL

↓

RabbitMQ

↓

Auditoría

↓

Logs

---

# 11. Flujo de Desarrollo

Todo cambio deberá seguir el siguiente proceso:

1. Planificar
2. Documentar
3. Diseñar
4. Implementar
5. Probar
6. Actualizar documentación
7. Actualizar bitácora
8. Actualizar AI_HANDOFF
9. Commit en Git

Nunca se debe comenzar escribiendo código sin haber documentado previamente la funcionalidad.

---

# 12. Gestión de Documentación

La documentación es parte del proyecto.

No debe quedar desactualizada.

Los siguientes archivos deben mantenerse sincronizados:

PROJECT_CONTEXT.md

README.md

docs/12_bitacora.md

docs/13_tareas.md

AI_HANDOFF.md

---

# 13. Cómo debe trabajar cualquier IA

Antes de escribir código debe:

1. Leer PROJECT_CONTEXT.md
2. Leer AI_HANDOFF.md
3. Leer docs/12_bitacora.md
4. Leer docs/13_tareas.md

Nunca debe asumir información inexistente.

No debe cambiar la arquitectura sin autorización.

Debe respetar las convenciones del proyecto.

Debe explicar las decisiones importantes.

Debe priorizar código limpio sobre código corto.

---

# 14. Filosofía del Proyecto

Este proyecto no busca únicamente funcionar.

Debe parecer un sistema desarrollado por un equipo profesional.

Cada decisión debe priorizar:

- Escalabilidad
- Mantenibilidad
- Legibilidad
- Modularidad
- Seguridad
- Buenas prácticas

---

# 15. Estado Actual

Fase actual:

Planificación.

No existe código implementado.

No existe base de datos.

No existen APIs.

La prioridad actual es completar toda la documentación antes de comenzar el desarrollo.

---

# 16. Próximo Paso

Finalizar la documentación inicial.

Una vez completada:

- Definir requisitos funcionales.
- Diseñar la arquitectura.
- Diseñar la base de datos.
- Diseñar la API REST.
- Crear el roadmap.
- Iniciar el desarrollo del backend.
