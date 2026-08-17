# Actualizacion - Sesion 21 (17/08/2026)

Se corrigio el arranque local del backend para terminales con JDK 21.

Problema detectado:

- Las clases existentes estaban compiladas con Java 25 (`class file version 69`).
- El `java` disponible en este entorno es JDK 21 (`class file version 65`).
- Eso provoca `UnsupportedClassVersionError` si se intenta arrancar con JDK 21.

Cambios:

- `backend/pom.xml`: `<java.version>` paso de `25` a `21`.
- `backend/pom.xml`: se agrego `-parameters` al `maven-compiler-plugin`.
- `RabbitMQConfig` y `FraudRabbitMQConfig`: se agregaron `@Qualifier` explicitos para evitar ambiguedad entre `auditQueue` y `fraudCheckQueue`.
- `login.md`: backend ahora se levanta con `mvn clean spring-boot:run`.
- Se agregaron comandos separados para PowerShell y CMD.

Verificacion:

- Compilacion manual con `javac --release 21` contra el classpath Maven: OK, 1 warning por `Jackson2JsonMessageConverter` deprecado.
- Arranque con `java -cp ... com.bankingsystem.BankingSystemApplication`: OK, llego a `Started BankingSystemApplication`.

Siguiente punto exacto:

1. Ejecutar desde la terminal del usuario:
   `cd banking-system\backend`
   `mvn clean spring-boot:run`
2. Si aparece otro error distinto, copiar el bloque `APPLICATION FAILED TO START`.

---

# AI HANDOFF

## Proyecto

Banking System

---

# Estado del proyecto

El proyecto terminó la fase de análisis y documentación (requisitos, casos de uso, arquitectura, modelo de datos, entidades detalladas, diseño de API y diseño final de endpoints — todos completos).

Se completó la configuración de control de versiones (Git + repositorio remoto en GitHub) y el esqueleto del proyecto Backend en Spring Boot, que compila y empaqueta correctamente (`mvn clean install` → BUILD SUCCESS, última confirmación conocida: 55 archivos fuente a inicio de Sesión 8).

Infraestructura vía Docker: **PostgreSQL y RabbitMQ están levantados y operativos** (`docker compose up -d postgres rabbitmq`, ambos `healthy`). Migración inicial `V1__init_schema.sql` (Flyway) ejecutada con éxito: 10 tablas, constraints de RN-01/02/03, índices y seed de roles.

**Módulo Auth completo a nivel de código** (registro, login, JWT access+refresh, protección de endpoints vía `JwtAuthenticationFilter`), compilando desde Sesión 7. **Su verificación en runtime y el push a GitHub seguían pendientes al cierre de la Sesión 7; no hay confirmación explícita de que se hayan hecho desde entonces — revisar antes de dar la Fase 2 por cerrada.**

**Nuevo en Sesión 8 (06/08/2026) — Ronda 1: módulo de Préstamos.** Antes de escribir código se confirmó en runtime que **Customer, Account y Transfer (Fase 3)**, dados por pendientes al cierre de Sesión 7, ya estaban implementados y correctamente ubicados en el proyecto real (`mvn clean install` → BUILD SUCCESS). Esto no estaba reflejado en `tareas.md`/`bitacora.md` hasta esta sesión.

Se implementó el módulo de Préstamos completo a nivel de código (RF-05, CU-08, CU-09): `dto/loan/*`, `mapper/LoanMapper.java`, `repositories/LoanRepository.java`, `services/LoanService.java` (`requestLoan`, `list`/`getById` con restricción de propiedad para CUSTOMER, `approve`/`reject`), `controllers/LoanController.java`. Ver ADR-009 en `bitacora.md` (tasa de interés fija 0.12 vía `application.yml`) — **pendiente trasladar formalmente a `decisiones.md`, donde todavía no aparece**.

**Nuevo en Sesión 8 — Ronda 2: módulo de Auditoría vía RabbitMQ.** Se implementó el registro asíncrono de auditoría (RF-07, CU-12):

- `config/RabbitMQConfig.java` (exchange/queue/binding de auditoría).
- `events/audit/AuditEvent.java`, `AuditEventPublisher.java`, `AuditEventListener.java` (consumidor que persiste `AuditLog`).
- `repositories/AuditLogRepository.java`, `dto/audit/AuditLogResponse.java`, `mapper/AuditLogMapper.java`.
- `services/AuditService.java`, `controllers/AuditController.java`.
- `common/AuditContext.java` (usuario autenticado + IP de la petición).
- `dto/account/AccountRequest.java` (deuda técnica de Fase 3, resuelta en esta ronda).

Se modificaron `TransferService.java`, `AuthService.java`, `AccountService.java`, `AccountController.java` y `LoanService.java` para publicar `AuditEvent` tras sus operaciones sensibles.

**Importante — pendiente de confirmar, no incluido en el resumen recibido para esta ronda:**

- Resultado de `mvn clean install` tras integrar el módulo de Auditoría (¿BUILD SUCCESS?).
- Verificación en runtime: que un login o una transferencia real generen un `AuditLog` consultable vía `AuditController`.
- Estado real del módulo de **Pagos** (no mencionado en el resumen de esta ronda; no queda claro si ya existe o sigue pendiente).
- Endpoints exactos de `AuditController` (para reflejarlos en `11_endpoints.md` si no estaban contemplados).
- Commit/push de las Rondas 1 y 2 de esta sesión a GitHub.

---

# Documentos principales disponibles

## Contexto

PROJECT_CONTEXT.md

## Presentación

README.md

## Requisitos

docs/requirements/01_requirements.md
docs/requirements/02_use_cases.md

## Arquitectura

docs/architecture/03_architecture.md
docs/architecture/05_conventions.md

## Base de datos

docs/database/07_database_model.md
docs/database/08_entities.md

## API

docs/api/10_api_design.md
docs/api/11_endpoints.md

## Gestión

docs/management/bitacora.md
docs/management/tareas.md
docs/management/decisiones.md

## Infraestructura

docker/docker-compose.yml
docker/.env (no versionar; usar .env.example como plantilla)
backend/src/main/resources/application.yml
backend/src/main/resources/application-dev.yml
backend/src/main/resources/application-docker.yml
backend/src/main/resources/db/migration/V1__init_schema.sql
backend/Dockerfile

---

# Estado técnico actual

Backend:

Esqueleto Spring Boot (Java 25 + Spring Boot 4.1.0, ADR-006) compilando. Perfiles `dev`/`docker` configurados. 10 entidades JPA + clases base + enums. Spring Security completo con JWT. **Módulo Auth completo** (`register`, `login`, `refresh`, `me`). **Customer, Account y Transfer completos** (Entity/Repository/Service/Controller, confirmados en runtime en Sesión 8). **Módulo de Préstamos completo** a nivel de código (Sesión 8, Ronda 1). **Módulo de Auditoría completo** a nivel de código, con consumidor RabbitMQ (Sesión 8, Ronda 2). Estado de **Pagos** y **Detección de Fraude**: sin confirmar / pendiente.

Frontend:

No creado.

Base de datos:

**Implementada.** PostgreSQL corriendo en Docker (`banking-postgres`, healthy). Esquema `V1__init_schema.sql` ejecutado con éxito.

Mensajería:

RabbitMQ corriendo en Docker (`banking-rabbitmq`, healthy). **Desde Sesión 8, Ronda 2: en uso real** — flujo de auditoría publica y consume `AuditEvent`. Pendiente confirmar manejo de fallos (reintentos / dead-letter queue).

Docker:

`docker-compose.yml` con 4 servicios. `postgres`/`rabbitmq` verificados y operativos. `backend`/`frontend` tienen `Dockerfile` listo pero no se han construido aún.

Git:

Repositorio local + remoto en GitHub, ramas `main` y `develop`. Backend subido hasta Spring Security (commits `39a806d`, `ef350fd`). **Pendiente confirmar si se subieron el módulo Auth (Sesión 7) y los módulos de Préstamos/Auditoría (Sesión 8) — no hay evidencia de push para ninguno de los tres en la información disponible.**

---

# Próxima fase

## Paso inmediato

1. Confirmar resultado de build (`mvn clean install`) tras la Ronda 2 (Auditoría) de Sesión 8.
2. Verificar en runtime el flujo de Auditoría: ejecutar un login o transferencia y confirmar que se genera un `AuditLog` real, consultable vía `AuditController`.
3. Confirmar si el módulo Auth (Sesión 7) llegó a verificarse en runtime, y si el flujo de Préstamos (Sesión 8, Ronda 1) también.
4. Aclarar el estado real del módulo de **Pagos** (RF-06): no aparece mencionado en las últimas rondas de trabajo.
5. Hacer commit/push de todo lo pendiente (Auth, Préstamos, Auditoría) a GitHub.
6. Trasladar ADR-009 (tasa de interés del préstamo) de `bitacora.md` a `decisiones.md`, donde todavía no aparece registrada.

## Fase 4 — cierre

Una vez resuelto lo anterior, la única pieza pendiente de Fase 4 es **Detección de Fraude** (RF-08, CU-11): reglas de detección, `FraudAlert`, bloqueo automático de cuenta y consumidor RabbitMQ.

## Fase 5 — Frontend Angular

No iniciada. Es el siguiente bloque de trabajo una vez cerrada la Fase 4.

Ya completados (no repetir): documentación completa, Git, esqueleto Backend, Docker (postgres/rabbitmq), esquema de base de datos, 10 entidades JPA, Spring Security + JWT, módulo Auth, Customer/Account/Transfer, módulo de Préstamos, módulo de Auditoría con RabbitMQ.

---

# Reglas para continuar

Antes de crear código:

Leer:

- PROJECT_CONTEXT.md
- README.md
- docs/architecture/03_architecture.md
- docs/management/tareas.md
- **bitacora.md, Sesión 8** — para el contexto de Préstamos y Auditoría, y los puntos pendientes de confirmar.

No cambiar arquitectura sin registrar una decisión técnica.

Toda funcionalidad terminada debe actualizar:

- bitacora.md
- tareas.md
- AI_HANDOFF.md

Nota: la tabla de usuarios se implementó como `app_user` (no `user`) por ser palabra reservada en PostgreSQL. Respetar este nombre al mapear la entidad JPA (`@Table(name = "app_user")`).

Nota (Sesión 7): la convención de paquetes del proyecto es **plural** (`controllers`, `services`, `repositories`, `entities`, `exceptions`), no singular.

Nota nueva (Sesión 8): al recibir un resumen de archivos nuevos/modificados sin detalle del resultado de build, runtime o push a GitHub, la IA debe registrar esos puntos explícitamente como pendientes de confirmar en la documentación, en vez de asumir que se completaron.

---

# Rol de la IA

La IA debe actuar como asistente de desarrollo.

Debe:

- Explicar decisiones.
- Seguir la arquitectura definida.
- Mantener documentación actualizada.
- Evitar soluciones rápidas que comprometan escalabilidad.
- Cuando entregue archivos para que el usuario los copie manualmente a su proyecto, no asumir que la copia se hizo correctamente — pedir confirmación explícita antes de dar una tarea por completamente cerrada.
- **Nuevo (Sesión 8):** cuando el usuario reporte trabajo mediante un resumen de archivos (sin detalle de build/runtime/push), no inventar esos resultados en la documentación — dejarlos marcados como pendientes de confirmar.

---

# Punto exacto donde continuar

1. Confirmar build y runtime del módulo de Auditoría (Sesión 8, Ronda 2).
2. Aclarar el estado del módulo de Pagos.
3. Commit/push de Auth, Préstamos y Auditoría a GitHub.
4. Trasladar ADR-009 a `decisiones.md`.
5. Implementar Detección de Fraude para cerrar formalmente la Fase 4.

---

# Nota de sincronización

Este archivo se actualizó el 06/08/2026 (Sesión 8) para reflejar: (1) confirmación de que Customer, Account y Transfer (Fase 3) están implementados y compilando; (2) módulo de Préstamos completo a nivel de código (Ronda 1); (3) módulo de Auditoría completo a nivel de código, con `RabbitMQConfig` y flujo evento/publicador/consumidor vía RabbitMQ, más ajustes en `TransferService`, `AuthService`, `AccountService`, `AccountController` y `LoanService` para publicar eventos de auditoría (Ronda 2); y (4) una lista explícita de puntos no confirmados en el resumen recibido (resultado de build, verificación en runtime, estado de Pagos, y push a GitHub), que quedan como primer punto de la próxima sesión. Detalle completo en `bitacora.md` (Sesión 8).
---

# Actualizacion - Sesion 11 (16/08/2026)

Se continuo la Fase 5 para hacer funcional el flujo pedido por el usuario: desde Clientes, un ADMIN puede crear clientes de prueba con usuario CUSTOMER, cuenta inicial y saldo real.

Cambios principales:

- Backend:
  - `AccountRequest` ahora acepta `initialBalance`.
  - `AccountService.createAccount` guarda el saldo inicial, validando que no sea negativo.
  - `AccountController.listAccounts` y `AccountService.listAccounts` usan `Authentication`; si el rol es `ROLE_CUSTOMER`, se filtra automaticamente por el customer enlazado al usuario autenticado.
  - `CustomerRequest` acepta campos opcionales de onboarding: `createLoginUser`, `username`, `password`, `createInitialAccount`, `initialAccountType`, `initialBalance`.
  - `CustomerService.create` puede crear `AppUser` con rol `CUSTOMER`, enlazarlo al `Customer`, y abrir cuenta inicial con saldo.
  - `CustomerController.getById` queda protegido para `ADMIN`/`EMPLOYEE`.

- Frontend:
  - `customer-form` permite crear usuario de acceso y cuenta inicial con saldo.
  - `account-form` usa los tipos reales `SAVINGS`/`CHECKING` y envia `initialBalance`.
  - Dashboard conecta los botones rapidos a rutas reales.
  - Se agrego modulo Fraude conectado a `GET /api/v1/fraud-alerts` y `PATCH /api/v1/fraud-alerts/{id}/resolve`.
  - `app.routes.ts` declara `/fraud`.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.

---

# Actualizacion - Sesion 24 (17/08/2026)

Se dejo registrada una regla permanente de documentacion continua.

Cambio:

- `PROJECT_CONTEXT.md` ahora exige que todo avance quede guardado en `docs/` antes de cerrar sesion.
- La regla especifica actualizar `AI_HANDOFF.md`, `docs/management/tareas.md`, `docs/management/bitacora.md`, documentos de `docs/api/` y decisiones tecnicas cuando aplique.

Motivo:

- El usuario pidio recordar guardar todos los progresos en docs.

Siguiente punto exacto:

- En los proximos cambios, documentar siempre el progreso antes del cierre.
- Backend no se pudo compilar desde esta terminal porque no existe `mvn`, `mvn.cmd`, `MAVEN_HOME` ni Maven Wrapper. No asumir build backend hasta ejecutar Maven en el entorno correcto.

Siguiente punto exacto:

1. Ejecutar `mvn test` o `mvn clean install` en backend con Maven disponible.
2. Levantar backend + frontend y probar: admin crea cliente con usuario/cuenta/saldo, luego login como ese CUSTOMER y confirmar que ve solo sus cuentas.
3. Continuar auditoria de autorizacion en Transfer, Loan y Payment.

---

# Actualizacion - Sesion 12 (16/08/2026)

Continuacion de hardening funcional:

- `TransferController.getHistory` recibe `Authentication`.
- `TransferService.getHistory` valida permiso sobre `accountId` cuando se filtra y restringe automaticamente el historial a cuentas del customer autenticado cuando el rol es `ROLE_CUSTOMER`.
- `TransferRepository.findHistory` acepta `customerId` opcional y usa `@Param` explicitos.
- Dashboard muestra acciones rapidas por rol:
  - ADMIN/EMPLOYEE: Nuevo cliente, Nueva cuenta, Ver transferencias.
  - CUSTOMER: Transferir, Pagar prestamo, Solicitar prestamo.
- Se reviso Pagos: el contrato actual solo registra pagos de prestamos aprobados (`loanId`, `amount`, `paymentMethod`). No descuenta cuenta bancaria porque no existe `accountId` en `PaymentRequest`; no simular desde frontend.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend sigue sin build local por falta de Maven/Maven Wrapper.

Siguiente punto exacto:

1. Decidir si el modulo Pagos debe debitar una cuenta real; si si, ampliar `PaymentRequest` con `accountId` y usar `AccountService.debit`.
2. Compilar backend con Maven disponible.
3. Probar runtime completo de cliente admin -> cliente CUSTOMER -> cuenta -> transferencia/historial.

---

# Actualizacion - Sesion 13 (16/08/2026)

Se implemento debito real en Pagos:

- Nueva migracion `backend/src/main/resources/db/migration/V3__add_payment_account.sql`.
- `Payment` mapea `account_id`.
- `PaymentRequest` ahora exige `accountId`.
- `PaymentResponse` devuelve `accountId` y `accountNumber`.
- `PaymentService.registerPayment` valida prestamo aprobado, valida propiedad/acceso de prestamo y cuenta, exige que la cuenta pertenezca al mismo customer del prestamo, debita con `AccountService.debit` y guarda el pago en la misma transaccion.
- `payment-form` carga prestamos aprobados y cuentas activas, filtra cuentas por customer del prestamo y envia `ACCOUNT_DEBIT`.
- `payment-list` muestra cuenta origen.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend todavia sin build local por falta de Maven/Maven Wrapper.

Siguiente punto exacto:

1. Ejecutar backend build con Maven disponible.
2. Levantar backend y verificar que Flyway aplique `V3__add_payment_account.sql`.
3. Probar pago real: seleccionar prestamo aprobado + cuenta con saldo, registrar pago, confirmar descuento de balance y nuevo registro en `/payments`.

---

# Actualizacion - Sesion 14 (16/08/2026)

Se corrigio el problema de arranque del frontend en Windows:

- Causa: `ng serve` en PowerShell intenta ejecutar `ng.ps1`, bloqueado por la politica de ejecucion.
- `ng.cmd --version` funciona correctamente.
- `frontend/package.json` ahora tiene:
  - `start`: `ng serve --host localhost --port 4200`
  - `start:4202`: `ng serve --host localhost --port 4202`
- Se agrego `frontend/start-frontend.cmd`.
- `login.md` fue reescrito con comandos correctos (`npm.cmd run start`, `ng.cmd serve ...`, o `start-frontend.cmd`).

Verificacion:

- `npm.cmd run build`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.
- Puerto 4200 escuchando en PID 27388.

Nota para continuar:

- Si el usuario ve "Port 4200 is already in use", probablemente el frontend ya esta levantado. Abrir `http://localhost:4200/login`.
- Si quiere reiniciarlo manualmente, cerrar la terminal donde corre Angular o finalizar el PID que escuche en 4200.

---

# Actualizacion - Sesion 15 (16/08/2026)

Se implemento apertura de cuentas por CUSTOMER y limite por tipo:

- `POST /accounts` ahora permite `ROLE_CUSTOMER`.
- Para `ROLE_CUSTOMER`, el backend resuelve el cliente desde el usuario autenticado y fuerza `initialBalance = 0`.
- Para ADMIN/EMPLOYEE, `customerId` sigue siendo obligatorio y el saldo inicial se acepta para pruebas.
- Nueva regla: un cliente solo puede tener una cuenta no eliminada por tipo (`SAVINGS` y `CHECKING`).
- Nueva migracion `V4__limit_one_account_per_type.sql` crea indice unico parcial por `(customer_id, account_type)` donde `deleted = false`.
- Frontend permite a CUSTOMER entrar a `/accounts/new`; oculta selector de cliente y saldo inicial.
- Se agrego `MoneyInputDirective` para formatear montos con comas mientras se escribe en cuentas, clientes, transferencias, prestamos y pagos.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.

---

# Actualizacion - Sesion 22 (17/08/2026)

Se implemento el modulo Trading completo.

Backend:

- Nueva migracion `V5__add_trading.sql`.
- Nuevas tablas: `trading_wallet`, `trading_position`, `trading_order`.
- Nuevas entidades/repositorios/DTOs para wallet, posiciones y ordenes.
- Nuevo `TradingService`.
- Nuevo `TradingController` en `/api/v1/trading`.
- Endpoints: `GET /portfolio`, `POST /deposit`, `POST /withdraw`, `POST /buy`, `POST /sell`, `GET /orders`.
- El deposito debita una cuenta bancaria del cliente autenticado.
- El retiro acredita la cuenta bancaria del cliente autenticado.
- Buy/sell operan contra el efectivo del wallet.
- Si Finnhub no tiene API key o falla, el servicio usa precio demo deterministico.

Frontend:

- Nuevo `TradingService`.
- Nuevo modelo `trading.model.ts`.
- Nueva ruta `/trading`.
- Sidebar actualizado con `Trading`.
- Nueva pantalla con resumen de efectivo/equity, TradingView Advanced Chart, ticket de ordenes, ingreso/retiro, posiciones y ordenes.

Documentacion:

- Nuevo `docs/api/24_trading_api.md`.

Verificacion:

- Compilacion manual backend con `javac --release 21`: OK.
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS sin warnings.
- Prueba de arranque backend en puerto aleatorio: OK.
- Flyway valido 5 migraciones y aplico V5 en PostgreSQL local.

Nota operativa:

- `localhost:8080` ya estaba ocupado por un backend anterior durante la prueba. Para que `/trading` funcione contra la API nueva, reiniciar el backend con el codigo actualizado.

---

# Actualizacion - Sesion 23 (17/08/2026)

Se activo y rehizo el flujo de transferencias.

Backend:

- `TransferRequest` ahora acepta `destinationAccount` para cuentas propias o `destinationAccountNumber` para otros usuarios registrados.
- `TransferService` valida cuenta origen activa, destino activo y propiedad del origen para usuarios `CUSTOMER`.
- Se agrego limite configurable de transferencias por cuenta origen en ventana de 24 horas:
  - `TRANSFER_DAILY_LIMIT`
  - `app.transfer.daily-limit`
  - valor default: `10`.
- `TransferResponse` ahora devuelve tambien `sourceAccountNumber` y `destinationAccountNumber`.

Frontend:

- `/transfers/new` ya no pide UUID destino.
- El usuario puede elegir `Cuenta propia` o `Otro usuario`.
- Cuenta propia usa dropdown de cuentas activas.
- Otro usuario usa numero de cuenta destino.
- Historial muestra numeros de cuenta cuando estan disponibles.

Documentacion:

- `docs/api/15_transfers_api.md` actualizado con el contrato real.

Verificacion:

- Compilacion manual backend con `javac --release 21`: OK.
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend pendiente de build local por falta de Maven/Maven Wrapper.

Nota:

- Si Flyway falla aplicando `V4__limit_one_account_per_type.sql`, revisar datos duplicados existentes en `account` para el mismo `customer_id` + `account_type` con `deleted = false`.

---

# Actualizacion - Sesion 16 (17/08/2026)

Se integro la API publica de Frankfurter para tasas de cambio y conversion de divisas.

Cambios principales:

- Backend:
  - Nueva configuracion `app.exchange-rates.frankfurter-base-url`, con default `https://api.frankfurter.dev` y override por `FRANKFURTER_BASE_URL`.
  - Nuevos DTOs en `dto/exchange`: tasas, contribuciones de proveedores, monedas, proveedores y conversiones.
  - Nuevo `ExchangeRateService`, que llama a Frankfurter con `RestClient`, normaliza monedas/listas/fechas y traduce errores externos a `ResponseStatusException`.
  - Nuevo `ExchangeRateController` con base `/api/v1/exchange-rates`:
    - `GET /rates`
    - `GET /rate/{base}/{quote}`
    - `GET /convert`
    - `GET /currencies`
    - `GET /currencies/{code}`
    - `GET /providers`

- Frontend:
  - Nuevo modelo `exchange-rate.model.ts`.
  - Nuevo `CurrencyService`, consumiendo el backend en vez de llamar al proveedor externo directo.
  - Nueva pantalla `Divisas` en `/exchange-rates`, con conversor, consulta de tasas y filtro por proveedor.
  - Sidebar actualizado con acceso a Divisas.
  - `account-list` ahora resuelve equivalentes USD usando `/exchange-rates/convert`.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- El primer build sin permisos elevados fallo con `spawn EPERM`; al repetirlo fuera del sandbox compilo correctamente.
- Dev server Angular levantado en `http://localhost:4200/`; `http://localhost:4200/login` respondio HTTP 200.
- Backend no se pudo compilar localmente porque sigue sin existir `mvn`, `mvn.cmd` ni `backend/mvnw.cmd`.

Siguiente punto exacto:

1. Ejecutar `mvn test` o `mvn clean install` en backend con Maven disponible.
2. Levantar backend y probar `GET /api/v1/exchange-rates/convert?amount=100&base=USD&quote=DOP` con JWT.
3. Probar en frontend `/exchange-rates` y confirmar que las tasas cargan con backend levantado e internet disponible.

---

# Actualizacion - Sesion 17 (17/08/2026)

Se integraron las APIs del Banco Popular Dominicano provistas por Swagger:

- `BPDConfirmarCuenta` version 2.5.1.
- `BPDUbicacionesATM` version 2.2.1.

Cambios principales:

- Backend:
  - Nueva configuracion `app.bpd.*` con variables `BPD_API_BASE_URL`, `BPD_CONFIRM_ACCOUNT_PATH`, `BPD_ATM_LOCATIONS_PATH`, `BPD_TOKEN_URL`, `BPD_CLIENT_ID`, `BPD_CLIENT_SECRET` y `BPD_OAUTH_SCOPE`.
  - Nuevos DTOs en `dto/bpd`.
  - Nuevo `BpdApiService`, que solicita token OAuth `client_credentials`, cachea el access token y llama los endpoints externos.
  - Nuevo `BpdController` con base `/api/v1/bpd`:
    - `POST /confirm-account`
    - `GET /atm-locations?page=0`

- Frontend:
  - Nuevo modelo `bpd.model.ts`.
  - Nuevo `BpdService`.
  - Nueva pantalla `/bpd` con formulario de confirmacion de titularidad y listado paginado de ATMs.
  - Sidebar actualizado con `Banco Popular`.

- Documentacion:
  - Nuevo `docs/api/22_bpd_api.md`.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/bpd`: HTTP 200.
- Backend no se pudo compilar localmente porque no existe `mvn`, `mvn.cmd` ni `backend/mvnw.cmd`.
- Runtime contra BPD no probado porque faltan credenciales reales `BPD_CLIENT_ID` y `BPD_CLIENT_SECRET`.

Siguiente punto exacto:

1. Configurar `BPD_CLIENT_ID` y `BPD_CLIENT_SECRET`.
2. Ejecutar build backend con Maven disponible.
3. Probar `POST /api/v1/bpd/confirm-account` y `GET /api/v1/bpd/atm-locations?page=0` con JWT valido.

---

# Actualizacion - Sesion 18 (17/08/2026)

Se integro la API de Finnhub provista por el usuario para datos de mercado.

Cambios principales:

- Backend:
  - Nueva configuracion `app.finnhub.*` con `FINNHUB_BASE_URL` y `FINNHUB_API_KEY`.
  - Nuevos DTOs en `dto/market`.
  - Nuevo `FinnhubMarketService`, que llama a Finnhub usando header `X-Finnhub-Token` y traduce errores externos.
  - Nuevo `MarketController` con base `/api/v1/markets`:
    - `GET /symbols/search`
    - `GET /quote`
    - `GET /company-profile`
    - `GET /status`
    - `GET /holidays`
    - `GET /news`
    - `GET /company-news`
    - `GET /basic-financials`
    - `GET /recommendations`

- Frontend:
  - Nuevo modelo `market.model.ts`.
  - Nuevo `MarketService`.
  - Nueva pantalla `/markets` con buscador de simbolos, cotizacion, perfil, estado de mercado, feriados, noticias, indicadores y recomendaciones.
  - Sidebar actualizado con `Mercados`.

- Documentacion:
  - Nuevo `docs/api/23_finnhub_market_api.md`.

Verificacion:

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/markets`: HTTP 200.
- Backend no se pudo compilar localmente porque no existe `mvn`, `mvn.cmd` ni `backend/mvnw.cmd`.
- Runtime contra Finnhub no probado porque falta `FINNHUB_API_KEY`.

Siguiente punto exacto:

1. Configurar `FINNHUB_API_KEY`.
2. Ejecutar build backend con Maven disponible.
3. Probar `/api/v1/markets/quote?symbol=AAPL` y `/api/v1/markets/symbols/search?q=apple&exchange=US` con JWT valido.

---

# Actualizacion - Sesion 19 (17/08/2026)

Se corrigio el fallo de arranque del backend reportado por el usuario.

Error:

- Spring no encontraba un bean de tipo `org.springframework.web.client.RestClient$Builder`.
- El fallo bloqueaba la creacion de `BpdApiService` y tambien afectaba las integraciones de Frankfurter y Finnhub.

Cambio aplicado:

- Nuevo `backend/src/main/java/com/bankingsystem/config/RestClientConfig.java`.
- Se registra `RestClient.Builder` como bean `prototype`, para que cada servicio reciba un builder limpio.

Verificacion:

- En el entorno de Codex no existe `mvn`, `mvn.cmd` ni `backend/mvnw.cmd`.
- El usuario si tiene Maven disponible en su consola, segun el log pegado.

Siguiente punto exacto:

1. En la consola del usuario, ejecutar:
   `cd banking-system\backend`
   `set DB_PASSWORD=Gabriel90xp`
   `set RABBITMQ_PASSWORD=Gabriel90xp`
   `mvn spring-boot:run`
2. Confirmar que ya no aparece el error de `RestClient$Builder`.

---

# Actualizacion - Sesion 20 (17/08/2026)

Se corrigio el bloqueo visual al iniciar sesion en el frontend.

Causa probable:

- `AuthService` hacia una llamada HTTP a `/auth/me` desde el constructor cuando encontraba un token en `localStorage`.
- El interceptor tambien inyecta `AuthService`, lo que podia dejar el estado de autenticacion inconsistente durante el arranque.
- El login navegaba al dashboard antes de terminar de cargar el perfil del usuario.

Cambios:

- `AuthService.login()` ahora guarda tokens, carga `/auth/me` y solo completa cuando el perfil esta disponible.
- `AuthService.ensureAuthenticated()` centraliza la restauracion de sesion.
- Ajuste final: el login ya no bloquea la navegacion esperando `/auth/me`; decodifica el JWT para poblar `currentUser` inmediatamente y refresca `/auth/me` en segundo plano.
- `authGuard` y `roleGuard` esperan el perfil antes de permitir o redirigir.
- `authInterceptor` no agrega `Authorization` a `/auth/login`, `/auth/register` ni `/auth/refresh`.
- Se agrego timeout de 15 segundos para evitar que el boton quede indefinidamente en "Ingresando...".

Verificacion:

- API directa OK:
  - `POST http://localhost:8080/api/v1/auth/login`
  - `GET http://localhost:8080/api/v1/auth/me`
  - `GET http://localhost:8080/api/v1/accounts?page=0&size=10`
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.
