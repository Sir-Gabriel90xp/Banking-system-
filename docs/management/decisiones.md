# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.



