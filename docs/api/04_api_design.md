# Diseño de API

## Endpoints principales
- POST /auth/login
- POST /users
- GET /accounts
- POST /transfers

## Consideraciones
- Autenticación por token.
- Respuestas JSON.
- Manejo de errores estándar.




# API Design

## Banking System REST API

**Versión:** 1.0.0

**Estado:** Diseño

---

# Objetivo

Este documento define el diseño de la API REST del Sistema Bancario.

Antes de implementar cualquier endpoint, este documento debe servir como referencia para garantizar consistencia, escalabilidad y mantenibilidad.

---

# Principios de Diseño

La API seguirá los siguientes principios:

- RESTful API
- Stateless
- Versionada
- JSON como formato de intercambio
- JWT para autenticación
- Respuestas consistentes
- Manejo global de errores
- Validaciones en Backend

---

# URL Base

```
/api/v1
```

Ejemplos:

```
/api/v1/auth/login
/api/v1/customers
/api/v1/accounts
/api/v1/transfers
```

---

# Autenticación

La autenticación se realizará mediante JWT.

Header requerido:

```
Authorization: Bearer <token>
```

---

# Módulos de la API

## 1. Authentication

Responsable de:

- Registro
- Login
- Refresh Token
- Perfil del usuario
- Cambio de contraseña

Ruta base

```
/api/v1/auth
```

---

## 2. Users

Administración de usuarios.

Ruta

```
/api/v1/users
```

---

## 3. Customers

Administración de clientes.

Ruta

```
/api/v1/customers
```

---

## 4. Accounts

Administración de cuentas bancarias.

Ruta

```
/api/v1/accounts
```

---

## 5. Transfers

Transferencias entre cuentas.

Ruta

```
/api/v1/transfers
```

---

## 6. Loans

Administración de préstamos.

Ruta

```
/api/v1/loans
```

---

## 7. Payments

Registro de pagos.

Ruta

```
/api/v1/payments
```

---

## 8. Transactions

Historial financiero.

Ruta

```
/api/v1/transactions
```

---

## 9. Audit

Registro de auditoría.

Ruta

```
/api/v1/audit
```

---

## 10. Fraud

Detección de fraude.

Ruta

```
/api/v1/fraud
```

---

# Métodos HTTP

| Método | Uso |
|----------|-------------------------------|
| GET | Consultar recursos |
| POST | Crear recursos |
| PUT | Actualizar completamente |
| PATCH | Actualización parcial |
| DELETE | Eliminación lógica |

---

# Convención de Respuestas

Todas las respuestas deberán seguir la misma estructura.

Respuesta exitosa

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": {},
  "timestamp": "2026-07-16T18:30:00Z"
}
```

---

Respuesta con error

```json
{
  "success": false,
  "message": "Account not found.",
  "errors": [],
  "timestamp": "2026-07-16T18:30:00Z"
}
```

---

# Códigos HTTP

| Código | Significado |
|---------|-------------|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

---

# Versionado

La API utilizará versionado mediante URL.

Ejemplo

```
/api/v1/accounts
```

En futuras versiones:

```
/api/v2/accounts
```

---

# Paginación

Todos los listados deberán soportar paginación.

Ejemplo

```
GET /customers?page=0&size=20
```

---

# Ordenamiento

Ejemplo

```
GET /customers?sort=lastName,asc
```

---

# Búsquedas

Ejemplo

```
GET /customers?search=Gabriel
```

---

# Filtros

Ejemplo

```
GET /transactions?status=COMPLETED
```

---

# Seguridad

Todos los endpoints (excepto login y registro) requerirán autenticación mediante JWT.

Se utilizarán Roles y Permisos.

Ejemplo

```
ROLE_ADMIN

ROLE_EMPLOYEE

ROLE_CUSTOMER
```

---

# Documentación

Toda la API será documentada utilizando Swagger / OpenAPI.

---

# Objetivos de Calidad

La API debe cumplir con:

- Escalabilidad
- Legibilidad
- Bajo acoplamiento
- Alta cohesión
- Seguridad
- Mantenibilidad
- Consistencia
- Fácil integración con Angular

---

# Estado

Documento en versión inicial.

Podrá ampliarse conforme se agreguen nuevos módulos al sistema.