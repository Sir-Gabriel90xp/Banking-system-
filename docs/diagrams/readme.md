# Diagramas del Proyecto

Este directorio contiene todos los diagramas utilizados durante el diseño y desarrollo del Sistema Bancario.

Los diagramas tienen como objetivo facilitar la comprensión de la arquitectura, la base de datos y el flujo de negocio.

---

# Diagramas Planificados

## 1. Arquitectura General

Archivo:

architecture.drawio

Descripción:

Representa la arquitectura completa del sistema.

Angular

↓

Spring Boot

↓

PostgreSQL

↓

RabbitMQ

↓

Docker

---

## 2. Arquitectura Backend

Archivo:

backend-architecture.drawio

Descripción:

Organización interna del Backend.

Controller

↓

Service

↓

Repository

↓

Database

---

## 3. Arquitectura Frontend

Archivo:

frontend-architecture.drawio

Descripción:

Organización por módulos de Angular.

Core

↓

Shared

↓

Features

↓

Pages

↓

Components

---

## 4. Diagrama Entidad Relación (ERD)

Archivo:

database-erd.drawio

Descripción:

Modelo de Base de Datos completo.

---

## 5. Casos de Uso

Archivo:

use-cases.drawio

Descripción:

Interacción entre:

- Administrador
- Empleado
- Cliente

---

## 6. Flujo de Login

Archivo:

login-flow.drawio

Descripción:

Proceso completo de autenticación mediante JWT.

---

## 7. Flujo de Transferencia

Archivo:

transfer-flow.drawio

Descripción:

Proceso de una transferencia bancaria.

---

## 8. Flujo de Préstamos

Archivo:

loan-flow.drawio

Descripción:

Solicitud, aprobación y pago de préstamos.

---

## 9. Flujo de Detección de Fraude

Archivo:

fraud-flow.drawio

Descripción:

Proceso de detección y bloqueo automático.

---

## 10. Despliegue con Docker

Archivo:

docker-deployment.drawio

Descripción:

Relación entre:

- Angular
- Spring Boot
- PostgreSQL
- RabbitMQ
- Docker Compose

---

# Herramienta

Todos los diagramas serán desarrollados utilizando:

- Draw.io (diagrams.net)

Formato principal:

.drawio

También podrán exportarse a:

- PNG
- SVG
- PDF

---

# Convenciones

Todos los diagramas deberán:

- Utilizar nombres en inglés.
- Mantener una estructura limpia.
- Evitar cruces innecesarios de líneas.
- Incluir título y versión.
- Mantener consistencia visual.

---

# Estado

Pendiente de elaboración.

Los diagramas se crearán conforme avance el desarrollo del proyecto.