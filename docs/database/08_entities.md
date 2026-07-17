# 08. Entidades

## Entidades principales

### Usuario
- Identifica a los usuarios del sistema.
- Puede tener roles como administrador, operador o cliente.

### Cliente
- Representa a la persona o empresa que utiliza los productos bancarios.

### Cuenta
- Contiene información de una cuenta bancaria.
- Incluye saldo, moneda y estado.

### Movimiento
- Registra operaciones de crédito o débito sobre una cuenta.

### Transferencia
- Representa un movimiento entre dos cuentas.

### Préstamo
- Registra un préstamo asociado a un cliente.

### Pago
- Representa un pago realizado por un cliente o cuenta.




# Entidades del Sistema

## Banking System

---

# User

Representa usuarios del sistema.

Campos:

- id
- username
- email
- password
- role
- status
- createdAt

---

# Role

Define permisos.

Campos:

- id
- name
- description

Valores:

- ADMIN
- EMPLOYEE
- CUSTOMER

---

# Customer

Representa clientes bancarios.

Campos:

- id
- firstName
- lastName
- documentNumber
- phone
- email
- address

Relaciones:

Customer 1:N Account

Customer 1:N Loan

---

# Account

Representa una cuenta bancaria.

Campos:

- id
- accountNumber
- balance
- type
- status

Relaciones:

Account N:1 Customer

Account 1:N Transaction

---

# Transaction

Movimiento financiero.

Campos:

- id
- amount
- type
- status
- date

Tipos:

- TRANSFER
- PAYMENT
- DEPOSIT

---

# Transfer

Transferencia bancaria.

Campos:

- id
- sourceAccount
- destinationAccount
- amount
- status
- createdAt

---

# Loan

Préstamo.

Campos:

- id
- amount
- interestRate
- term
- status

---

# Payment

Pago.

Campos:

- id
- loan
- amount
- date

---

# AuditLog

Auditoría.

Campos:

- id
- user
- action
- entity
- timestamp

---

# FraudAlert

Alerta de fraude.

Campos:

- id
- account
- reason
- severity
- status