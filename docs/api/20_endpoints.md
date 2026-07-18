# API Endpoints

## Banking System

**Versión:** 1.1

**Estado:** Aprobado

> Nota de renombrado: este documento reemplaza a `20_endpoints.md`. Se renombra a `11_endpoints.md` para coincidir con la referencia usada en `AI_HANDOFF.md`, `tareas.md` y `bitacora.md`.

---

# Objetivo

Definir el diseño final de endpoints de la API REST, en consistencia con los principios y formato de respuesta ya definidos en `04_api_design.md`.

---

# Formato estándar de respuesta

Todos los endpoints devuelven las respuestas en el formato definido en `04_api_design.md`. Los ejemplos de este documento muestran únicamente el contenido de `data`, salvo que se indique lo contrario.

Respuesta exitosa:

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": { },
  "timestamp": "2026-07-16T18:30:00Z"
}
```

Respuesta con error:

```json
{
  "success": false,
  "message": "Account not found.",
  "errors": [],
  "timestamp": "2026-07-16T18:30:00Z"
}
```

---

# Convenciones de listados

Todo endpoint `GET` que devuelva una colección soporta:

- Paginación: `?page=0&size=20`
- Ordenamiento: `?sort=lastName,asc`
- Filtros específicos por recurso (detallados en cada módulo)

---

# 1. Authentication

Base:

```
/api/v1/auth
```

## Registro

POST `/register`

Request:

```json
{
  "username": "gabriel",
  "email": "user@email.com",
  "password": "123456",
  "role": "ROLE_CUSTOMER"
}
```

data:

```json
{
  "id": "uuid",
  "username": "gabriel",
  "email": "user@email.com",
  "role": "ROLE_CUSTOMER"
}
```

## Login

POST `/login`

Request:

```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

data:

```json
{
  "token": "jwt-token",
  "refreshToken": "refresh-jwt-token",
  "expiresIn": 3600
}
```

## Refresh Token

POST `/refresh`

Request:

```json
{
  "refreshToken": "refresh-jwt-token"
}
```

data:

```json
{
  "token": "new-jwt-token",
  "expiresIn": 3600
}
```

## Perfil del usuario autenticado

GET `/me`

data: objeto `User` (DTO, sin password).

## Cambio de contraseña

PATCH `/me/password`

Request:

```json
{
  "currentPassword": "123456",
  "newPassword": "newPass789"
}
```

---

# 2. Users

Base:

```
/api/v1/users
```

Requiere rol `ROLE_ADMIN`, salvo donde se indique.

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/users` | Listar usuarios (paginado, filtro `?status=`) |
| GET | `/users/{id}` | Consultar usuario |
| POST | `/users` | Crear usuario |
| PUT | `/users/{id}` | Actualizar usuario |
| PATCH | `/users/{id}/status` | Activar / desactivar usuario |
| DELETE | `/users/{id}` | Eliminación lógica (soft delete) |

---

# 3. Customers

Base:

```
/api/v1/customers
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/customers` | Listar clientes (paginado, `?search=`, `?sort=`) |
| GET | `/customers/{id}` | Consultar cliente |
| POST | `/customers` | Crear cliente |
| PUT | `/customers/{id}` | Actualizar cliente |
| DELETE | `/customers/{id}` | Eliminación lógica (soft delete) |

---

# 4. Accounts

Base:

```
/api/v1/accounts
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/accounts` | Listar cuentas (paginado, `?status=`, `?customerId=`) |
| GET | `/accounts/{id}` | Consultar cuenta |
| POST | `/accounts` | Crear cuenta |
| GET | `/accounts/{id}/balance` | Consultar saldo |
| PATCH | `/accounts/{id}/block` | Bloquear cuenta |
| PATCH | `/accounts/{id}/activate` | Activar / desbloquear cuenta |
| DELETE | `/accounts/{id}` | Eliminación lógica (soft delete, solo si `balance = 0`) |

---

# 5. Transfers

Base:

```
/api/v1/transfers
```

## Crear transferencia

POST `/transfers`

Request:

```json
{
  "sourceAccount": "uuid",
  "destinationAccount": "uuid",
  "amount": 1000
}
```

data: objeto `Transfer` creado.

Validación: `sourceAccount` y `destinationAccount` no pueden ser iguales (regla de integridad, ver `03_database_model.md`). Responde `422 Unprocessable Entity` si se viola.

## Historial

GET `/transfers/history`

Query params: `?accountId=`, `?status=`, `?page=`, `?size=`, `?sort=`

---

# 6. Loans

Base:

```
/api/v1/loans
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/loans` | Listar préstamos (paginado, `?customerId=`, `?status=`) |
| GET | `/loans/{id}` | Consultar préstamo |
| POST | `/loans` | Solicitar préstamo |
| PATCH | `/loans/{id}/approve` | Aprobar préstamo |
| PATCH | `/loans/{id}/reject` | Rechazar préstamo |

---

# 7. Payments

Base:

```
/api/v1/payments
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/payments` | Listar pagos (paginado, `?loanId=`) |
| GET | `/payments/{id}` | Consultar pago |
| POST | `/payments` | Registrar pago |

---

# 8. Transactions

Base:

```
/api/v1/transactions
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/transactions` | Listar movimientos (paginado, `?accountId=`, `?type=`, `?status=`) |
| GET | `/transactions/{id}` | Consultar movimiento |

Este recurso es de solo lectura: las transacciones se generan internamente como consecuencia de operaciones en `Accounts`, `Transfers`, `Loans` y `Payments`, no se crean directamente vía API.

---

# 9. Audit

Base:

```
/api/v1/audit
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/audit` | Listar registros (paginado, `?userId=`, `?entity=`, `?from=`, `?to=`) |

Requiere rol `ROLE_ADMIN`. Solo lectura.

---

# 10. Fraud

Base:

```
/api/v1/fraud
```

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/fraud/alerts` | Listar alertas (paginado, `?severity=`, `?resolved=`) |
| PATCH | `/fraud/{id}/resolve` | Resolver alerta |

Requiere rol `ROLE_ADMIN` o `ROLE_EMPLOYEE`.

---

# Seguridad

Todos los endpoints, excepto `/auth/register` y `/auth/login`, requieren JWT (`Authorization: Bearer <token>`), según `04_api_design.md`.

---

# Cambios respecto a la versión anterior (`20_endpoints.md`)

- Se agregó el módulo **Users**, ausente en la versión anterior.
- Se agregó el módulo **Transactions** como recurso de solo lectura.
- Se completó **Auth** con Registro, Refresh Token, Perfil y Cambio de contraseña, ya prometidos en `04_api_design.md` pero no implementados en el diseño de endpoints.
- Se agregó `DELETE` (eliminación lógica) donde correspondía, siguiendo la tabla de métodos HTTP de `04_api_design.md`.
- Se agregó `PATCH /accounts/{id}/activate` como contraparte de `block`, alineado con "Activar cuentas" mencionado en `README.md`.
- Todos los ejemplos ahora se describen dentro del formato estándar de respuesta (`success/message/data/timestamp`).
- Se agregó paginación, orden y filtros explícitos a todos los listados.
- Se renombró el archivo de `20_endpoints.md` a `11_endpoints.md` para coincidir con la referencia usada en el resto de la documentación de gestión del proyecto.

---

# Estado

Documento consolidado y aprobado. Sirve como referencia directa para la implementación de los Controllers.