# Arquitectura del sistema

## Visión general
- Backend y frontend separados.
- Base de datos relacional.
- Comunicación a través de API REST.

## Componentes
- API backend.
- Frontend web.
- Base de datos.
- Servicios de autenticación.



# Arquitectura del Sistema

## Banking System

**Versión:** 1.0

**Estado:** Diseño

---

# Objetivo

Definir la arquitectura de software del proyecto para garantizar escalabilidad, mantenibilidad, seguridad y facilidad de evolución.

Esta arquitectura deberá ser respetada durante todo el desarrollo.

---

# Estilo Arquitectónico

El proyecto utilizará una **Arquitectura por Capas (Layered Architecture)** con separación clara de responsabilidades.

Cada capa tendrá una única responsabilidad y no podrá acceder directamente a capas que no le correspondan.

---

# Arquitectura General

```text
                 Angular Frontend
                        │
                        ▼
                REST API (HTTP)
                        │
                        ▼
                 Spring Controllers
                        │
                        ▼
                    Services
                        │
                        ▼
                 Business Rules
                        │
                        ▼
                 Repositories
                        │
                        ▼
                  PostgreSQL
```

---

# Capas del Backend

## 1. Controller

Responsabilidades:

- Recibir solicitudes HTTP.
- Validar parámetros básicos.
- Invocar los Services.
- Retornar respuestas HTTP.

No debe contener lógica de negocio.

---

## 2. Service

Responsabilidades:

- Implementar la lógica de negocio.
- Validar reglas del sistema.
- Coordinar operaciones entre entidades.
- Gestionar transacciones.

Es el núcleo del sistema.

---

## 3. Repository

Responsabilidades:

- Acceder a la base de datos.
- Ejecutar consultas.
- Persistir entidades.

No debe contener reglas de negocio.

---

## 4. Database

Responsabilidades:

- Almacenar la información.
- Garantizar integridad.
- Mantener relaciones.

---

# Arquitectura del Frontend

Angular se organizará por funcionalidades (Feature Modules).

Ejemplo:

```text
src/

app/

core/

shared/

features/

auth/

customers/

accounts/

transfers/

loans/

payments/

audit/

fraud/
```

Cada módulo será independiente.

---

# Arquitectura del Backend

La estructura será similar a:

```text
src/main/java/

com.bankingsystem/

config/

security/

controllers/

services/

repositories/

entities/

dto/

mapper/

exceptions/

validators/

events/

audit/

fraud/

utils/
```

---

# Principios SOLID

Todo el proyecto deberá seguir:

- Single Responsibility Principle
- Open / Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

---

# Clean Code

Se seguirán las siguientes reglas:

- Métodos pequeños.
- Clases con una sola responsabilidad.
- Nombres descriptivos.
- Sin código duplicado.
- Sin números mágicos.
- Sin lógica compleja en Controllers.

---

# DTO

Nunca se devolverán entidades directamente.

Siempre se utilizarán DTOs.

Ejemplo:

```
Entity

↓

Mapper

↓

DTO

↓

JSON
```

---

# Mappers

Toda conversión entre Entity y DTO se realizará mediante Mappers.

No se permitirá mezclar conversiones dentro de los Services.

---

# Manejo Global de Excepciones

Se utilizará un manejador global.

Ejemplos:

- ResourceNotFoundException
- ValidationException
- UnauthorizedException
- BusinessException

---

# Seguridad

La seguridad estará basada en:

- Spring Security
- JWT
- Roles
- Permisos

Los Controllers no validarán permisos manualmente.

Toda la autorización será gestionada por Spring Security.

---

# Auditoría

Todas las operaciones importantes deberán generar registros de auditoría.

Ejemplos:

- Login
- Transferencias
- Creación de cuentas
- Préstamos
- Bloqueo de cuentas

---

# RabbitMQ

RabbitMQ será utilizado para procesos asíncronos.

Ejemplos:

- Auditoría
- Detección de fraude
- Notificaciones
- Logs

Esto permitirá desacoplar procesos pesados del flujo principal.

---

# Base de Datos

PostgreSQL será la base de datos principal.

Se utilizarán:

- Relaciones normalizadas
- Claves foráneas
- Restricciones
- Índices

---

# Docker

Todo el sistema deberá ejecutarse mediante Docker Compose.

Servicios previstos:

- Backend
- Frontend
- PostgreSQL
- RabbitMQ

---

# Swagger

Toda la API deberá documentarse automáticamente mediante OpenAPI.

Cada endpoint deberá incluir:

- Descripción
- Parámetros
- Respuestas
- Códigos HTTP

---

# Convenciones de Desarrollo

- Código en inglés.
- Documentación en español.
- CamelCase para variables.
- PascalCase para clases.
- REST para endpoints.

---

# Flujo de una Solicitud

```text
Cliente

↓

Angular

↓

Controller

↓

Service

↓

Repository

↓

PostgreSQL

↓

Service

↓

DTO

↓

Controller

↓

JSON

↓

Angular
```

---

# Objetivos de Calidad

La arquitectura debe garantizar:

- Escalabilidad.
- Seguridad.
- Modularidad.
- Bajo acoplamiento.
- Alta cohesión.
- Facilidad para pruebas.
- Fácil mantenimiento.

---

# Estado

Documento base de arquitectura.

Cualquier cambio importante deberá registrarse también en el documento de decisiones técnicas (ADR).


Versión: 1.0
Estado: Aprobado
Última actualización: 2026-07-16
Autor: Gabriel Mejía


## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial |

