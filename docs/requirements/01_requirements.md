# Documento de Requisitos

## Banking System

**Versión:** 1.0

**Estado:** Diseño

---

# Objetivo

Definir los requisitos funcionales y no funcionales del Sistema Bancario, sirviendo como base para el diseño de casos de uso, entidades detalladas y endpoints de la API.

Este documento debe mantenerse sincronizado con `PROJECT_CONTEXT.md`, `02_architecture.md`, `03_database_model.md` y `04_api_design.md`.

---

# Alcance

El sistema permitirá:

- Gestionar usuarios, roles y permisos.
- Gestionar clientes bancarios.
- Gestionar cuentas bancarias.
- Realizar transferencias entre cuentas.
- Gestionar préstamos y pagos.
- Registrar auditoría de operaciones.
- Detectar transacciones sospechosas.

Queda fuera del alcance de esta primera versión:

- Tarjetas de crédito y débito.
- Integración con cajeros automáticos.
- Multidivisa.
- Sucursales físicas.
- Inversiones y seguros.

(Estos ítems ya están previstos como líneas de escalabilidad futura en `03_database_model.md`.)

---

# Requisitos Funcionales

## RF-01 Autenticación

- RF-01.1 El sistema debe permitir el registro de usuarios.
- RF-01.2 El sistema debe permitir el inicio de sesión mediante credenciales (usuario/contraseña).
- RF-01.3 El sistema debe emitir un token JWT al autenticar exitosamente.
- RF-01.4 El sistema debe permitir renovar el token mediante refresh token.
- RF-01.5 El sistema debe permitir cambiar la contraseña de un usuario autenticado.
- RF-01.6 El sistema debe restringir el acceso a endpoints según el rol del usuario (ADMIN, EMPLOYEE, CUSTOMER).

## RF-02 Gestión de Clientes

- RF-02.1 El sistema debe permitir registrar un nuevo cliente.
- RF-02.2 El sistema debe permitir consultar los datos de un cliente.
- RF-02.3 El sistema debe permitir actualizar los datos de un cliente.
- RF-02.4 El sistema debe permitir eliminar lógicamente a un cliente (soft delete).
- RF-02.5 El sistema debe validar que el documento de identidad y el correo electrónico sean únicos.

## RF-03 Gestión de Cuentas Bancarias

- RF-03.1 El sistema debe permitir crear una cuenta bancaria asociada a un cliente.
- RF-03.2 El sistema debe permitir consultar el saldo de una cuenta.
- RF-03.3 El sistema debe permitir bloquear una cuenta.
- RF-03.4 El sistema debe permitir activar una cuenta previamente bloqueada.
- RF-03.5 El sistema no debe permitir balances negativos.
- RF-03.6 El número de cuenta debe ser único.

## RF-04 Transferencias

- RF-04.1 El sistema debe permitir transferir fondos entre dos cuentas.
- RF-04.2 El sistema debe validar que las cuentas de origen y destino sean distintas.
- RF-04.3 El sistema debe validar que la cuenta de origen tenga saldo suficiente.
- RF-04.4 El sistema debe registrar el historial de transferencias.
- RF-04.5 Toda transferencia debe generar un registro de auditoría.

## RF-05 Préstamos

- RF-05.1 El sistema debe permitir a un cliente solicitar un préstamo.
- RF-05.2 El sistema debe permitir aprobar o rechazar una solicitud de préstamo.
- RF-05.3 El sistema debe calcular la cuota mensual según monto, tasa de interés y plazo.
- RF-05.4 El sistema debe mantener el historial de pagos del préstamo.
- RF-05.5 El sistema debe reflejar los estados: Pending, Approved, Rejected, Paid.

## RF-06 Pagos

- RF-06.1 El sistema debe permitir registrar el pago de una cuota de préstamo.
- RF-06.2 El sistema debe permitir registrar el pago de servicios.
- RF-06.3 Todo pago debe quedar asociado a un método de pago y una fecha.

## RF-07 Auditoría

- RF-07.1 El sistema debe registrar toda acción relevante (login, transferencias, creación de cuentas, préstamos, bloqueo de cuentas).
- RF-07.2 Cada registro de auditoría debe incluir usuario, acción, entidad afectada, IP y timestamp.
- RF-07.3 El registro de auditoría debe procesarse de forma asíncrona mediante RabbitMQ.

## RF-08 Detección de Fraude

- RF-08.1 El sistema debe detectar transacciones sospechosas según reglas definidas.
- RF-08.2 El sistema debe generar alertas de fraude con nivel de severidad.
- RF-08.3 El sistema debe permitir el bloqueo automático de una cuenta ante fraude detectado.
- RF-08.4 El procesamiento de detección de fraude debe ser asíncrono mediante RabbitMQ.

---

# Requisitos No Funcionales

## RNF-01 Seguridad

- Autenticación basada en JWT.
- Autorización basada en roles gestionada por Spring Security.
- Los Controllers no deben validar permisos manualmente.

## RNF-02 Escalabilidad

- El modelo de datos y la arquitectura deben permitir agregar módulos futuros sin romper la estructura existente.

## RNF-03 Mantenibilidad

- El código debe seguir SOLID, Clean Code y Separation of Concerns.
- Nunca se deben exponer entidades directamente; siempre a través de DTOs.

## RNF-04 Disponibilidad de Documentación

- Toda la API debe documentarse con Swagger/OpenAPI.
- Toda decisión técnica relevante debe registrarse en `decisiones.md`.

## RNF-05 Contenerización

- Todo el sistema debe poder ejecutarse mediante Docker Compose (backend, frontend, PostgreSQL, RabbitMQ).

## RNF-06 Rendimiento

- Los listados deben soportar paginación, ordenamiento y filtros para evitar sobrecarga de datos.

## RNF-07 Trazabilidad

- Todas las tablas principales deben mantener `createdAt`, `updatedAt`, `createdBy`, `updatedBy`.
- Debe preferirse Soft Delete sobre eliminación física.

---

# Reglas de Negocio Clave

- RN-01: No se permiten balances negativos en ninguna cuenta.
- RN-02: No pueden existir transferencias entre la misma cuenta.
- RN-03: Un correo electrónico y un documento de identidad deben ser únicos en el sistema.
- RN-04: Todo préstamo debe estar asociado a un cliente existente.
- RN-05: Toda operación de auditoría debe registrar al usuario responsable.
- RN-06: Una cuenta bloqueada no puede recibir ni emitir transferencias.

---

# Trazabilidad con Otros Documentos

| Requisito | Documento relacionado |
|-----------|------------------------|
| RF-01 a RF-08 | `04_api_design.md` (endpoints) |
| RF-02 a RF-08 | `03_database_model.md` (entidades) |
| RNF-01 a RNF-07 | `02_architecture.md` (arquitectura) |

---

# Estado

Documento inicial de requisitos.

Pendiente de derivar:

- Casos de uso (`02_use_cases.md`).
- Entidades detalladas (`08_entities.md`).
- Diseño final de endpoints (`11_endpoints.md`).

---

Versión: 1.0
Estado: En revisión
Última actualización: 2026-07-16

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de requisitos |