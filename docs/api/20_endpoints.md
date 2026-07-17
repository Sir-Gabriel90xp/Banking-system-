# API Endpoints

## Banking System

---

# Authentication

Base:

```
/api/v1/auth
```

---

## Login

POST

```
/login
```

Request:

```json
{
"email":"user@email.com",
"password":"123456"
}
```

Response:

```json
{
"token":"jwt-token"
}
```

---

# Customers

Base:

```
/api/v1/customers
```

---

GET

Obtener clientes

```
GET /customers
```

---

GET

Buscar cliente

```
GET /customers/{id}
```

---

POST

Crear cliente

```
POST /customers
```

---

PUT

Actualizar cliente

```
PUT /customers/{id}
```

---

# Accounts

Base:

```
/api/v1/accounts
```

---

GET

Consultar cuentas

```
GET /accounts
```

---

POST

Crear cuenta

```
POST /accounts
```

---

GET

Consultar saldo

```
GET /accounts/{id}/balance
```

---

PATCH

Bloquear cuenta

```
PATCH /accounts/{id}/block
```

---

# Transfers

Base:

```
/api/v1/transfers
```

---

POST

Crear transferencia

```
POST /transfers
```

Body:

```json
{
"sourceAccount":"uuid",
"destinationAccount":"uuid",
"amount":1000
}
```

---

GET

Historial

```
GET /transfers/history
```

---

# Loans

Base:

```
/api/v1/loans
```

POST

Solicitar préstamo

```
POST /loans
```

---

PATCH

Aprobar préstamo

```
PATCH /loans/{id}/approve
```

---

# Payments

Base:

```
/api/v1/payments
```

POST

Registrar pago

```
POST /payments
```

---

# Audit

Base:

```
/api/v1/audit
```

GET

Consultar registros

```
GET /audit
```

---

# Fraud

Base:

```
/api/v1/fraud
```

GET

Consultar alertas

```
GET /fraud/alerts
```

PATCH

Resolver alerta

```
PATCH /fraud/{id}/resolve
```

---

# Estado

Documento inicial.

Los endpoints pueden ampliarse durante la implementación.