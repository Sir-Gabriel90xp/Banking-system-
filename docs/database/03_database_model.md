# Modelo de base de datos

## Entidades principales
- Usuario
- Cuenta
- Movimiento
- Transferencia

## Relaciones
- Un usuario tiene muchas cuentas.
- Una cuenta tiene muchos movimientos.




# Modelo de Base de Datos

## Banking System

**Versión:** 1.0

**Estado:** Diseño

---

# Objetivo

Definir el modelo de datos del Sistema Bancario, identificando las entidades principales, sus atributos, relaciones y reglas de integridad.

Este documento servirá como base para la implementación en PostgreSQL y para las entidades de Spring Boot.

---

# Motor de Base de Datos

- PostgreSQL

---

# Convenciones

- Nombres de tablas en singular.
- Claves primarias UUID.
- Claves foráneas con restricciones.
- Timestamps automáticos.
- Soft Delete cuando aplique.

---

# Entidades Principales

## User

Representa un usuario autenticado del sistema.

Campos principales:

- id
- username
- email
- password
- enabled
- createdAt
- updatedAt

Relaciones:

- Un User tiene un Role.
- Un User puede estar asociado a un Customer o a un Employee.

---

## Role

Define los permisos del usuario.

Campos:

- id
- name
- description

Ejemplos:

- ADMIN
- EMPLOYEE
- CUSTOMER

---

## Customer

Información del cliente bancario.

Campos:

- id
- firstName
- lastName
- documentNumber
- birthDate
- phone
- email
- address
- status

Relaciones:

- Un Customer puede tener múltiples Accounts.
- Un Customer puede solicitar múltiples Loans.

---

## Account

Representa una cuenta bancaria.

Campos:

- id
- accountNumber
- accountType
- balance
- currency
- status
- createdAt

Relaciones:

- Pertenece a un Customer.
- Tiene múltiples Transactions.
- Puede recibir y enviar Transfers.

---

## Transaction

Registro financiero general.

Campos:

- id
- type
- amount
- description
- date
- status

Tipos:

- Deposit
- Withdrawal
- Transfer
- Loan Payment

---

## Transfer

Transferencias entre cuentas.

Campos:

- id
- originAccount
- destinationAccount
- amount
- status
- transferDate

---

## Loan

Préstamos bancarios.

Campos:

- id
- amount
- interestRate
- termMonths
- monthlyPayment
- status
- createdAt

Estados:

- Pending
- Approved
- Rejected
- Paid

---

## Payment

Pagos registrados.

Campos:

- id
- amount
- paymentDate
- paymentMethod
- status

---

## AuditLog

Registro de auditoría.

Campos:

- id
- user
- action
- entity
- entityId
- ipAddress
- timestamp

---

## FraudAlert

Alertas de fraude.

Campos:

- id
- account
- reason
- severity
- detectedAt
- resolved

---

# Relaciones

Role

↓

1 ------ N

↓

User

↓

1 ------ 1

↓

Customer

↓

1 ------ N

↓

Account

↓

1 ------ N

↓

Transaction

↓

Transfer

---

Customer

↓

1 ------ N

↓

Loan

↓

1 ------ N

↓

Payment

---

Account

↓

1 ------ N

↓

FraudAlert

---

User

↓

1 ------ N

↓

AuditLog

---

# Reglas de Integridad

- Un correo electrónico debe ser único.
- El número de cuenta debe ser único.
- Un documento de identidad debe ser único.
- No se permiten balances negativos.
- No pueden existir transferencias entre la misma cuenta.
- Todo préstamo debe pertenecer a un cliente.
- Toda auditoría debe registrar el usuario responsable.

---

# Estrategia de Claves

Todas las tablas utilizarán:

UUID

Como clave primaria.

Ejemplo:

```
550e8400-e29b-41d4-a716-446655440000
```

---

# Índices

Se crearán índices para:

- email
- username
- accountNumber
- documentNumber
- createdAt

---

# Eliminación

Siempre que sea posible se utilizará:

Soft Delete

mediante:

- deleted
- deletedAt

para conservar el historial.

---

# Auditoría

Todas las tablas principales tendrán:

- createdAt
- updatedAt
- createdBy
- updatedBy

---

# Escalabilidad

El modelo fue diseñado para permitir agregar posteriormente:

- Tarjetas de crédito.
- Tarjetas de débito.
- Cajeros automáticos.
- Sucursales.
- Divisas múltiples.
- Inversiones.
- Seguros.

Sin modificar significativamente la estructura existente.

---

# Estado

Documento inicial del modelo de datos.

Posteriormente se complementará con:

- Diagrama Entidad-Relación (ERD).
- Scripts SQL.
- Migraciones con Flyway.