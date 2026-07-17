# Casos de Uso

## Banking System

**Versión:** 1.0

**Estado:** Diseño

---

# Objetivo

Detallar los casos de uso del sistema derivados del documento `01_requirements.md`, describiendo actores, flujos principales, flujos alternativos y precondiciones.

---

# Actores

- **Admin:** gestiona usuarios, roles y supervisa el sistema.
- **Employee:** gestiona clientes, cuentas y aprueba préstamos.
- **Customer:** consulta sus cuentas, realiza transferencias y solicita préstamos.
- **Sistema (automático):** ejecuta detección de fraude y auditoría de forma asíncrona.

---

# CU-01 Registro de Usuario

**Actor:** Admin / Customer

**Requisito relacionado:** RF-01.1

**Precondición:** El correo electrónico no debe estar registrado previamente.

**Flujo principal:**
1. El actor envía sus datos de registro.
2. El sistema valida que el correo y usuario sean únicos.
3. El sistema crea el usuario con el rol correspondiente.
4. El sistema retorna confirmación.

**Flujo alternativo:**
- 2a. Si el correo ya existe, el sistema retorna error 409 (Conflict).

---

# CU-02 Inicio de Sesión

**Actor:** Admin / Employee / Customer

**Requisito relacionado:** RF-01.2, RF-01.3

**Precondición:** El usuario debe estar registrado y habilitado (`enabled = true`).

**Flujo principal:**
1. El actor envía usuario y contraseña.
2. El sistema valida las credenciales.
3. El sistema genera un token JWT y un refresh token.
4. El sistema retorna los tokens al actor.

**Flujo alternativo:**
- 2a. Si las credenciales son inválidas, el sistema retorna error 401 (Unauthorized).
- 2b. Si el usuario está deshabilitado, el sistema retorna error 403 (Forbidden).

---

# CU-03 Registro de Cliente

**Actor:** Employee / Admin

**Requisito relacionado:** RF-02.1, RF-02.5

**Precondición:** El actor debe estar autenticado con rol EMPLOYEE o ADMIN.

**Flujo principal:**
1. El actor envía los datos del cliente.
2. El sistema valida que el documento de identidad y el correo sean únicos.
3. El sistema crea el registro del cliente.
4. El sistema genera un registro de auditoría.

**Flujo alternativo:**
- 2a. Si el documento ya existe, el sistema retorna error 409 (Conflict).

---

# CU-04 Creación de Cuenta Bancaria

**Actor:** Employee

**Requisito relacionado:** RF-03.1, RF-03.6

**Precondición:** El cliente debe existir y estar activo.

**Flujo principal:**
1. El actor solicita la creación de una cuenta para un cliente.
2. El sistema genera un número de cuenta único.
3. El sistema crea la cuenta con saldo inicial en cero.
4. El sistema genera un registro de auditoría.

---

# CU-05 Consulta de Saldo

**Actor:** Customer / Employee

**Requisito relacionado:** RF-03.2

**Precondición:** La cuenta debe existir y pertenecer al cliente autenticado (si es Customer).

**Flujo principal:**
1. El actor solicita el saldo de una cuenta.
2. El sistema valida la propiedad/autorización sobre la cuenta.
3. El sistema retorna el saldo actual.

**Flujo alternativo:**
- 2a. Si la cuenta no pertenece al actor (rol Customer), el sistema retorna error 403 (Forbidden).

---

# CU-06 Bloqueo / Activación de Cuenta

**Actor:** Employee / Sistema (automático, en caso de fraude)

**Requisito relacionado:** RF-03.3, RF-03.4, RF-08.3

**Flujo principal:**
1. El actor (o el sistema automático) solicita bloquear/activar una cuenta.
2. El sistema actualiza el estado de la cuenta.
3. El sistema genera un registro de auditoría.

---

# CU-07 Transferencia entre Cuentas

**Actor:** Customer

**Requisito relacionado:** RF-04.1, RF-04.2, RF-04.3, RN-01, RN-02

**Precondición:** Ambas cuentas deben existir, estar activas y ser distintas entre sí.

**Flujo principal:**
1. El actor solicita transferir un monto de la cuenta origen a la cuenta destino.
2. El sistema valida que origen y destino sean diferentes.
3. El sistema valida que la cuenta origen tenga saldo suficiente.
4. El sistema descuenta el monto de la cuenta origen y lo acredita en la cuenta destino.
5. El sistema registra la transferencia y genera auditoría.
6. El sistema envía el evento a RabbitMQ para evaluación de fraude.

**Flujo alternativo:**
- 2a. Si origen y destino son iguales, el sistema retorna error 422 (Unprocessable Entity).
- 3a. Si el saldo es insuficiente, el sistema retorna error 422 (Unprocessable Entity).
- 6a. Si el sistema de fraude detecta un patrón sospechoso, se genera una `FraudAlert` y puede bloquearse la cuenta (ver CU-11).

---

# CU-08 Solicitud de Préstamo

**Actor:** Customer

**Requisito relacionado:** RF-05.1, RN-04

**Precondición:** El cliente debe existir y estar activo.

**Flujo principal:**
1. El actor solicita un préstamo indicando monto y plazo.
2. El sistema calcula la cuota mensual estimada.
3. El sistema crea la solicitud en estado `Pending`.

---

# CU-09 Aprobación / Rechazo de Préstamo

**Actor:** Employee

**Requisito relacionado:** RF-05.2, RF-05.5

**Precondición:** La solicitud debe estar en estado `Pending`.

**Flujo principal:**
1. El actor revisa la solicitud de préstamo.
2. El actor aprueba o rechaza la solicitud.
3. El sistema actualiza el estado a `Approved` o `Rejected`.
4. El sistema genera un registro de auditoría.

---

# CU-10 Pago de Préstamo / Servicios

**Actor:** Customer

**Requisito relacionado:** RF-06.1, RF-06.2, RF-06.3

**Precondición:** El préstamo debe estar en estado `Approved`.

**Flujo principal:**
1. El actor registra un pago indicando monto y método de pago.
2. El sistema valida el monto contra el saldo pendiente del préstamo.
3. El sistema registra el pago.
4. El sistema actualiza el estado del préstamo a `Paid` si corresponde.

---

# CU-11 Detección de Fraude

**Actor:** Sistema (automático)

**Requisito relacionado:** RF-08.1, RF-08.2, RF-08.3, RF-08.4

**Disparador:** Evento recibido vía RabbitMQ tras una transferencia u operación sensible.

**Flujo principal:**
1. El sistema evalúa la operación contra las reglas de detección de fraude.
2. Si se detecta un patrón sospechoso, el sistema crea una `FraudAlert` con nivel de severidad.
3. Si la severidad lo amerita, el sistema bloquea automáticamente la cuenta (ver CU-06).
4. El sistema genera un registro de auditoría del evento.

---

# CU-12 Registro de Auditoría

**Actor:** Sistema (automático)

**Requisito relacionado:** RF-07.1, RF-07.2, RF-07.3

**Disparador:** Cualquier operación relevante (login, transferencia, creación de cuenta, préstamo, bloqueo).

**Flujo principal:**
1. El sistema captura el evento relevante.
2. El sistema publica el evento a RabbitMQ.
3. Un consumidor asíncrono persiste el registro en `AuditLog` (usuario, acción, entidad, IP, timestamp).

---

# Matriz de Trazabilidad

| Caso de Uso | Requisito(s) |
|-------------|---------------|
| CU-01 | RF-01.1 |
| CU-02 | RF-01.2, RF-01.3 |
| CU-03 | RF-02.1, RF-02.5 |
| CU-04 | RF-03.1, RF-03.6 |
| CU-05 | RF-03.2 |
| CU-06 | RF-03.3, RF-03.4, RF-08.3 |
| CU-07 | RF-04.1 a RF-04.5 |
| CU-08 | RF-05.1 |
| CU-09 | RF-05.2, RF-05.5 |
| CU-10 | RF-06.1 a RF-06.3 |
| CU-11 | RF-08.1 a RF-08.4 |
| CU-12 | RF-07.1 a RF-07.3 |

---

# Estado

Documento inicial de casos de uso.

Pendiente de derivar:

- Entidades detalladas (`08_entities.md`).
- Diseño final de endpoints (`11_endpoints.md`).

---

Versión: 1.0
Estado: En revisión
Última actualización: 2026-07-16

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de casos de uso |