# Bitácora del Proyecto

## Banking System

Este documento registra el historial de desarrollo del proyecto.

---

# Sesión 1 - Planificación y Documentación Inicial

## Fecha

16/07/2026

---

## Objetivo de la sesión

Crear la base documental del proyecto antes de iniciar el desarrollo.

---

# Actividades realizadas

## Creación de estructura inicial

Se definió la estructura principal del repositorio:

- backend/
- frontend/
- database/
- docker/
- docs/
- screenshots/

---

# Documentación creada

Se completaron los siguientes documentos:

## Documentación principal

- PROJECT_CONTEXT.md
- README.md

## Arquitectura

- docs/architecture/03_architecture.md
- docs/architecture/05_conventions.md

## Base de datos

- docs/database/07_database_model.md
- docs/database/08_entities.md

## API

- docs/api/10_api_design.md
- docs/api/11_endpoints.md

## Requisitos

- docs/requirements/01_requirements.md
- docs/requirements/02_use_cases.md

## Gestión del proyecto

- docs/management/tareas.md
- docs/management/decisiones.md
- docs/management/bitacora.md

## Diagramas

- docs/diagrams/README.md

## Planificación

- docs/roadmap/13_roadmap.md

---

# Decisiones tomadas

## Documentación antes del código

Se decidió no iniciar programación hasta completar la planificación del sistema.

Motivo:

Reducir errores de arquitectura y permitir que cualquier IA o desarrollador pueda incorporarse al proyecto.

---

## Uso de múltiples IA

El proyecto será desarrollado utilizando documentación como fuente principal de contexto.

Las herramientas de IA podrán cambiarse sin perder el progreso.

---

## Arquitectura definida

Backend:

Java Spring Boot

Arquitectura:

Controller → Service → Repository → Database

Frontend:

Angular organizado por funcionalidades.

Base de datos:

PostgreSQL

Mensajería:

RabbitMQ

Contenedores:

Docker

Seguridad:

JWT + Spring Security

---

# Estado actual

## Desarrollo

No iniciado.

## Backend

Pendiente.

## Frontend

Pendiente.

## Base de datos

Diseñada, sin implementación.

## API

Diseñada, sin implementación.

---

# Próxima sesión

Inicio de la fase de desarrollo.

Orden:

1. Inicializar Git.
2. Crear repositorio GitHub.
3. Crear proyecto Spring Boot.
4. Configurar Maven.
5. Configurar PostgreSQL.
6. Configurar Docker.
7. Crear estructura inicial del Backend.

---

# Fin de sesión

La fase de documentación inicial fue completada correctamente.

---

# Sesión 2 - Requisitos, Casos de Uso y Control de Versiones

## Fecha

16/07/2026

---

## Objetivo de la sesión

Completar el documento de requisitos y casos de uso pendientes, y adelantar la configuración de control de versiones del proyecto.

---

# Actividades realizadas

## Documentación

Se completaron:

- docs/requirements/01_requirements.md (requisitos funcionales, no funcionales y reglas de negocio)
- docs/requirements/02_use_cases.md (12 casos de uso derivados de los requisitos)

## Control de Versiones

Se realizó lo siguiente:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub: `https://github.com/Sir-Gabriel90xp/Banking-system-.git`
- Creación de archivo `.gitignore` para stack Spring Boot + Angular + Docker.
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

---

# Decisiones tomadas

## Adelantar control de versiones (ADR-005)

Se decidió inicializar Git/GitHub antes de completar la documentación de entidades detalladas y diseño final de endpoints, para no perder el progreso documental existente y dejar preparado el entorno de trabajo colaborativo.

Ver detalle en `decisiones.md` (ADR-005).

---

# Estado actual

## Documentación

Requisitos y casos de uso: completados.

Entidades detalladas y diseño final de endpoints: pendientes.

## Control de Versiones

Completado.

## Desarrollo

No iniciado.

---

# Próxima sesión

Continuar con la documentación pendiente:

1. Completar entidades detalladas (08_entities.md).
2. Completar diseño final de endpoints (11_endpoints.md).
3. Preparación del entorno (Spring Boot, Maven, PostgreSQL, Docker).

---

# Fin de sesión

---

# Sesión 3 - Creación del Proyecto Spring Boot

## Fecha

16/07/2026

---

## Objetivo de la sesión

Crear el esqueleto del proyecto backend en Spring Boot y validar que compila correctamente en el entorno de desarrollo.

---

# Actividades realizadas

## Estructura del proyecto

Se creó la estructura completa del backend en `banking-system/backend/`, respetando el paquete definido en `02_architecture.md`:

```
com.bankingsystem/
├── config/
├── security/
├── controllers/
├── services/
├── repositories/
├── entities/
├── dto/
├── mapper/
├── exceptions/
├── validators/
├── events/
├── audit/
├── fraud/
└── utils/
```

## Incidencias técnicas y resolución

Durante la puesta en marcha surgieron varios problemas de entorno, resueltos en el orden siguiente:

1. **pom.xml vacío tras extracción de .tar.gz en Windows** — se solucionó entregando los archivos sueltos (`pom.xml`, clase principal, `application.yml`) en vez de un paquete comprimido.
2. **Incompatibilidad Lombok + JDK 25** (`ExceptionInInitializerError: TypeTag :: UNKNOWN`) — se evaluó bajar a Java 21 vs. actualizar todo el stack a una versión soportada de Spring Boot compatible con Java 25. Se decidió lo segundo (ver ADR-006).
3. **Spring Boot 3.3.4 resultó estar en EOL** — se migró a **Spring Boot 4.1.0** (rama activamente soportada), lo que implicó:
   - Actualizar Lombok a 1.18.44.
   - Renombrar starters (`spring-boot-starter-web` → `spring-boot-starter-webmvc`, Flyway ahora con starter dedicado).
   - Usar starters de test específicos por tecnología en vez de un `spring-boot-starter-test` genérico.
   - Actualizar springdoc-openapi a 3.0.3 (compatible con Spring Framework 7).
4. **Error de empaquetado "Unable to find main class"** — resuelto especificando `<mainClass>` explícitamente en `spring-boot-maven-plugin`.

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS**

El proyecto compila, empaqueta y se instala correctamente en el repositorio local Maven (`.m2`).

---

# Decisiones tomadas

## Java 25 + Spring Boot 4.1.0 (ADR-006)

Se decidió construir el backend sobre Java 25 (LTS, ya instalado) y Spring Boot 4.1.0 (rama activamente soportada), en lugar de forzar Java 21 o mantener Spring Boot 3.x ya en EOL. Ver detalle completo en `decisiones.md` (ADR-006).

---

# Estado actual

## Documentación

Entidades detalladas y diseño final de endpoints: pendientes.

## Backend

Proyecto Spring Boot creado y compilando correctamente. Sin código de negocio todavía (carpetas vacías con `.gitkeep`, a la espera de cerrar la documentación de entidades y endpoints).

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

Definir el camino a seguir: retomar documentación pendiente (entidades detalladas y endpoints) antes de escribir código de negocio, según el flujo original del proyecto.

---

# Fin de sesión

---

# Sesión 4 - Preparación del Entorno: PostgreSQL, Docker y Esquema Inicial

## Fecha

17-18/07/2026

---

## Objetivo de la sesión

Completar la documentación de entidades y endpoints (`08_entities.md`, `11_endpoints.md`), corregir el desfase existente en `tareas.md`/`bitacora.md`, y preparar el entorno de infraestructura (PostgreSQL + RabbitMQ vía Docker) para poder implementar el esquema de base de datos.

---

# Actividades realizadas

## Corrección de desfase documental

Se detectó que `AI_HANDOFF.md` indicaba `08_entities.md` y `11_endpoints.md` como completados, mientras que `tareas.md` y `bitacora.md` (Sesión 3) seguían marcándolos como pendientes. Se confirmó que ambos documentos ya estaban completos y se corrigió el desfase en esta sesión.

## Infraestructura Docker

Se creó `docker/docker-compose.yml` con los 4 servicios previstos en `02_architecture.md` (RNF-05): `postgres`, `rabbitmq`, `backend`, `frontend`. Se configuró `.env` con credenciales propias.

Se levantaron los servicios de infraestructura:

```
docker compose up -d postgres rabbitmq
```

Resultado: `banking-postgres` y `banking-rabbitmq` en estado `Up (healthy)`.

Se verificó el panel de administración de RabbitMQ en `http://localhost:15672` con éxito.

## Configuración de Spring Boot

Se crearon los perfiles de aplicación:

- `application.yml` (base, con Flyway y configuración JWT/Swagger)
- `application-dev.yml` (Postgres/RabbitMQ en localhost)
- `application-docker.yml` (Postgres/RabbitMQ vía nombres de servicio Docker)

Se creó `Dockerfile` multi-stage para el backend (Java 25 + Maven).

## Esquema de Base de Datos

Se creó la migración Flyway `V1__init_schema.sql` basada en `03_database_model.md`, implementando las 10 entidades principales (`role`, `app_user`, `customer`, `account`, `transaction`, `transfer`, `loan`, `payment`, `audit_log`, `fraud_alert`) con:

- Claves primarias UUID (`gen_random_uuid()`, extensión `pgcrypto`).
- Constraints de reglas de negocio a nivel de base de datos: `CHECK (balance >= 0)` (RN-01), `CHECK (origin_account_id <> destination_account_id)` (RN-02), `UNIQUE` en email/username/documento/número de cuenta (RN-03).
- Índices en columnas de búsqueda frecuente.
- Timestamps de auditoría (`created_at`, `updated_at`, `created_by`, `updated_by`) y soft delete donde aplica (RNF-07).
- Seed inicial de los 3 roles del sistema (ADMIN, EMPLOYEE, CUSTOMER).

La migración se ejecutó manualmente contra el contenedor `banking-postgres` (vía `docker cp` + `psql -f`) para validar el esquema antes de integrarlo al arranque automático de Flyway en el backend. Resultado: las 10 tablas se crearon correctamente y el seed de roles quedó insertado, verificado con `\dt` y `SELECT name FROM role;`.

## Incidencia técnica

Al intentar copiar la migración al contenedor, el primer archivo se copió vacío (0 bytes) porque se había creado localmente sin contenido real. Se resolvió descargando el archivo generado y verificando su tamaño (`dir`) antes de repetir la copia.

---

# Decisiones tomadas

## Nomenclatura `app_user` en vez de `user`

Se decidió nombrar la tabla de usuarios `app_user` en lugar de `user`, ya que `user` es palabra reservada en PostgreSQL. Es una decisión de nomenclatura sobre la implementación, no un cambio al modelo conceptual definido en `03_database_model.md`.

## Payment sin entidad de Servicio separada

`payment.loan_id` se definió como nullable para permitir pagos de servicios (RF-06.2) sin necesidad de una tabla de "Servicio" adicional, ya que `03_database_model.md` no la contemplaba explícitamente.

---

# Estado actual

## Documentación

Completa (incluye entidades detalladas y diseño final de endpoints).

## Backend

Esqueleto Spring Boot compilando. Perfiles de configuración (`dev`, `docker`) listos. Sin entidades JPA ni lógica de negocio todavía.

## Base de Datos

Implementada. Esquema inicial (`V1__init_schema.sql`) ejecutado con éxito sobre PostgreSQL en Docker. 10 tablas + seed de roles verificados.

## Docker

PostgreSQL y RabbitMQ operativos y verificados (`healthy`). Backend y Frontend con Dockerfile listo, pendiente de build real (sin código de negocio aún).

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

1. Crear las entidades JPA (`@Entity`) que mapeen el esquema ya implementado, comenzando por `Role` y `AppUser`.
2. Crear DTOs y Mappers correspondientes.
3. Iniciar la implementación de seguridad JWT (Fase 2 de `tareas.md`).

---

# Fin de sesión

---

# Sesión 5 - Entidades JPA Completas

## Fecha

18/07/2026

---

## Objetivo de la sesión

Crear las 10 entidades JPA (`@Entity`) que mapeen exactamente el esquema ya implementado en PostgreSQL (`V1__init_schema.sql`), respetando la arquitectura por capas y sin introducir lógica de negocio en las entidades.

---

# Actividades realizadas

## Diseño de clases base reutilizables

Se crearon dos clases `@MappedSuperclass` en `entities/base/` para evitar repetir código entre entidades (DRY):

- **`Auditable`**: clave primaria UUID (generada con `@UuidGenerator` de Hibernate, coherente con `gen_random_uuid()` del esquema) + campos `createdAt`, `updatedAt`, `createdBy`, `updatedBy` (RNF-07), con `equals`/`hashCode` basados en `id` (con manejo seguro de proxies de Hibernate, evitando el `@Data` de Lombok a propósito).
- **`SoftDeletableEntity`**: extiende `Auditable`, agrega `deleted`/`deletedAt` (RNF-07) para las entidades que sí tienen soft delete en el esquema.

## Entidades creadas

Se implementaron las 10 entidades principales, todas en `com.bankingsystem.entities`:

| Entidad | Extiende | Notas |
|---|---|---|
| `Role` | `Auditable` | Sin soft delete en el esquema |
| `AppUser` | `SoftDeletableEntity` | Mapeada a la tabla `app_user`; relación `@ManyToOne` con `Role` |
| `Customer` | `SoftDeletableEntity` | `user` (AppUser) es `@ManyToOne` opcional, sin `UNIQUE` en BD — ver decisión abajo |
| `Account` | `SoftDeletableEntity` | Balance con `BigDecimal`; la regla RN-01 (no negativos) ya está garantizada por el `CHECK` de la BD, no se repite en la entidad |
| `Transaction` | `Auditable` (sin soft delete) | `type` como enum, `status` como texto simple (sin dominio cerrado definido en el SQL) |
| `Transfer` | `Auditable` (sin soft delete) | RN-02 (cuentas distintas) ya garantizada por `CHECK` de la BD |
| `Loan` | `Auditable` (sin soft delete) | `status` como enum (`PENDING`, `APPROVED`, `REJECTED`, `PAID`, según RF-05.5) |
| `Payment` | `Auditable` (sin soft delete) | `loan` opcional (pagos de servicios, decisión de Sesión 4) |
| `AuditLog` | **Ninguna clase base** | La tabla no tiene `created_by`/`updated_by`; se maneja de forma independiente |
| `FraudAlert` | **Ninguna clase base** | La tabla tiene `created_at`/`updated_at` pero NO `created_by`/`updated_by`; timestamps manejados con `@PrePersist`/`@PreUpdate` propios |

También se crearon enums de apoyo en `entities/enums/`: `CustomerStatus`, `AccountType`, `AccountStatus`, `TransactionType`, `LoanStatus`, `FraudSeverity` — solo para los campos donde el SQL define explícitamente una lista cerrada de valores.

## Verificación

```
mvn clean install
```

➡️ **BUILD SUCCESS** con las 10 entidades ya integradas al proyecto.

---

# Decisiones tomadas

## Customer–AppUser sin restricción UNIQUE

Se confirmó dejar `customer.user_id` como `@ManyToOne` (no `@OneToOne`), reflejando exactamente lo que la base de datos permite hoy (sin `UNIQUE`), en vez de forzar la relación 1:1 del modelo conceptual con una nueva migración. Queda abierta la posibilidad de agregar el `UNIQUE` más adelante si se decide reforzar esa regla a nivel de base de datos.

## AuditLog y FraudAlert no heredan de Auditable

Se detectó que ambas tablas tienen un esquema de auditoría distinto al resto (`AuditLog` no tiene ninguno de los 4 campos estándar; `FraudAlert` tiene `created_at`/`updated_at` pero no `created_by`/`updated_by`). En vez de forzarlas a extender `Auditable` (lo que rompería el mapeo con columnas inexistentes), se implementaron como entidades independientes.

---

# Estado actual

## Documentación

Completa.

## Backend

Las 10 entidades JPA implementadas y compilando correctamente (`BUILD SUCCESS`). Sin DTOs, Mappers, Services ni Controllers todavía.

## Base de Datos

Sin cambios respecto a la Sesión 4 (implementada, sin migraciones nuevas).

## Docker

Sin cambios respecto a la Sesión 4.

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

1. Crear los DTOs y Mappers correspondientes a las 10 entidades (nunca exponer entidades directamente — ADR-004).
2. Iniciar la implementación de seguridad JWT (Fase 2 de `tareas.md`).

---

# Fin de sesión

---

# Sesión 6 - Subida de código a GitHub y Spring Security

## Fecha

18/07/2026

---

## Objetivo de la sesión

Subir a GitHub el código que hasta ahora solo existía localmente (backend, entidades, Docker, esquema BD) y comenzar la Fase 2 implementando Spring Security, según el orden redefinido en ADR-007.

---

# Actividades realizadas

## Corrección de .gitignore

Se detectó que el `.gitignore` del repo remoto no coincidía con lo documentado: era un `.gitignore` genérico (`node_modules/`, `dist/`, `build/`, `.env`, `__pycache__/`, `*.log`) sin ninguna exclusión real para Maven/Spring Boot (`target/`) ni para IDEs (`.idea/`, `*.iml`). Se reemplazó por un `.gitignore` correcto para el stack Spring Boot + Angular + Docker.

## Subida del backend a GitHub

Se subió por primera vez a GitHub todo el código que existía solo localmente desde la Sesión 3:

- Esqueleto Spring Boot (`pom.xml`, `Dockerfile`, clase principal).
- Las 10 entidades JPA + clases base + enums (Sesión 5).
- Perfiles de configuración (`application.yml`, `application-dev.yml`, `application-docker.yml`).
- Esquema de base de datos (`V1__init_schema.sql`).
- `docker/docker-compose.yml`.

Durante el proceso se limpiaron archivos que no debían subirse: `backend/target/` (compilado), un `.tar.gz` residual del incidente de la Sesión 3, y copias sueltas duplicadas de `BankingSystemApplication.java`/`application.yml` fuera de su ubicación real en `src/main/`.

Commits resultantes en `develop`: `39a806d` (código) y `ef350fd` (sincronización de documentación).

## ADR-007: adelantar Spring Security antes que DTOs/Mappers

Se decidió invertir el orden documentado en `AI_HANDOFF.md`: implementar primero Spring Security, registro, login, JWT y protección de endpoints (Fase 2 completa), y retomar después los DTOs/Mappers del resto de entidades. Detalle completo en `decisiones.md` (ADR-007).

## Implementación de Spring Security

Se crearon los componentes base de seguridad:

- `AppUserRepository` (`repositories/`): `findByEmail`, `findByUsername`, checks de unicidad.
- `AppUserPrincipal` (`security/`): adapta `AppUser` al contrato `UserDetails` sin mezclar código de Spring Security dentro de la entidad JPA. Aplica el prefijo `ROLE_` en tiempo de ejecución (en BD los roles se guardan sin prefijo: `ADMIN`, `EMPLOYEE`, `CUSTOMER`).
- `CustomUserDetailsService` (`security/`): carga el usuario por email (el login se hace por email, no por username, según `11_endpoints.md`).
- `SecurityConfig` (`config/`): `PasswordEncoder` (BCrypt), `AuthenticationProvider`, `AuthenticationManager` y el filter chain (rutas públicas: `/auth/register`, `/auth/login`, `/auth/refresh`, Swagger; el resto requiere autenticación). El filtro JWT queda para la siguiente tarea, con el punto de inserción ya marcado (`TODO`).

## Incidencias técnicas y resolución

1. **`BankingSystemApplication.java` vacío (0 bytes)** en `src/main/java/com/bankingsystem/`. Es probable que, durante el incidente del `.tar.gz` de la Sesión 3, el archivo real haya quedado sin contenido y el que sí tenía el `@SpringBootApplication` fuera el duplicado suelto (ya eliminado al limpiar el repo). Se reconstruyó con su contenido estándar.
2. **`DaoAuthenticationProvider` sin constructor vacío**: en la versión de Spring Security que trae Spring Boot 4.1.0, el constructor sin argumentos fue eliminado y el método `setUserDetailsService(...)` también dejó de existir. Se corrigió pasando `CustomUserDetailsService` directo en el constructor (`new DaoAuthenticationProvider(customUserDetailsService)`).

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS** con Spring Security integrado.

---

# Decisiones tomadas

## ADR-007 (detalle en `decisiones.md`)

Adelantar Spring Security/JWT antes que los DTOs y Mappers del resto de entidades. Es un reordenamiento de tareas, no un cambio de arquitectura ni de alcance.

---

# Estado actual

## Documentación

Completa, con ADR-007 registrado.

## Control de Versiones

Backend, entidades, Docker y esquema BD subidos a GitHub por primera vez (antes solo existían localmente). `.gitignore` corregido.

## Backend

Spring Security implementado (config, UserDetailsService, PasswordEncoder, filter chain) y compilando (`BUILD SUCCESS`). Sin JWT todavía, sin DTOs, sin controllers/services de negocio.

## Base de Datos / Docker

Sin cambios respecto a la Sesión 4.

---

# Próxima sesión

1. Crear DTOs mínimos de Auth (`RegisterRequest`, `LoginRequest`, `AuthResponse`).
2. Implementar `JwtService` (generación/validación de access y refresh token; configuración ya lista en `application.yml`).
3. Implementar `AuthController`/`AuthService` con `/api/v1/auth/register` y `/api/v1/auth/login` (`11_endpoints.md`).
4. Insertar el `JwtAuthenticationFilter` en `SecurityConfig` (punto ya marcado con `TODO`).

---

# Fin de sesión












---

# Sesión 7 - Registro, Login, JWT y Protección de Endpoints

## Fecha

18/07/2026

---

## Objetivo de la sesión

Cerrar la Fase 2 de Seguridad implementando lo que quedó pendiente al final de la Sesión 6: DTOs de Auth, `JwtService`, `AuthController`/`AuthService` (registro y login) y el `JwtAuthenticationFilter` insertado en `SecurityConfig`, según el orden fijado en ADR-007.

---

# Actividades realizadas

## DTOs de Auth

Se crearon en `dto/auth/`: `RegisterRequest`, `RegisterResponse`, `LoginRequest`, `AuthResponse`, `RefreshTokenRequest`, `TokenRefreshResponse`, `UserProfileResponse`. Se agregó también `dto/common/ApiResponse.java`, el envoltorio genérico de respuesta (`success/message/data/errors/timestamp`) definido en `04_api_design.md`, usado por todos los endpoints nuevos.

## RoleRepository (nuevo)

No existía. Fue necesario para resolver el `role` recibido en `RegisterRequest` (ej. `"ROLE_CUSTOMER"`) contra la tabla `role` ya sembrada en `V1__init_schema.sql`.

## JwtService y JwtAuthenticationFilter

En `security/jwt/`:

- `JwtService`: generación y validación de access token y refresh token, usando la API de `jjwt` 0.12.6 (`Jwts.builder()`/`Jwts.parser()` con `SecretKey`). El subject del token es el `id` (UUID) del `AppUser`, no el email. Se agregó un claim `type` (`access`/`refresh`) para que ningún endpoint acepte el tipo de token equivocado.
- `JwtAuthenticationFilter`: puebla el `SecurityContext` a partir de un access token válido en el header `Authorization`.

## AuthService y AuthController

`services/AuthService.java` implementa `register`, `login`, `refresh` y `getProfile` (CU-01, CU-02, RF-01.1 a RF-01.4). `controllers/AuthController.java` expone `/api/v1/auth/register`, `/login`, `/refresh` y `/me`, según `11_endpoints.md`.

## GlobalExceptionHandler (nuevo)

Se creó `exceptions/GlobalExceptionHandler.java` para traducir errores al formato estándar de `04_api_design.md`. Ver ADR-008: se usó `ResponseStatusException` de Spring en vez de crear ya la jerarquía completa de excepciones de negocio de `02_architecture.md`, por ser prematura antes de Fase 3.

## SecurityConfig actualizado

Se insertó `.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)` en el punto que ya estaba marcado con `TODO` desde la Sesión 6, junto con el campo `jwtAuthenticationFilter` inyectado por `@RequiredArgsConstructor`. Se confirmó que `/api/v1/auth/me` no estaba en `PUBLIC_ENDPOINTS`, por lo que ya quedaba protegido correctamente sin modificar esa lista.

## Incidencias técnicas y resolución

1. **Paquetes en singular en vez de plural**: los primeros borradores de `AuthController.java`/`AuthService.java` se generaron con `package com.bankingsystem.controller;` / `.service;` (singular), inconsistente con la convención real del resto del proyecto (`controllers`, `services`, `repositories`, `entities`, plural — `02_architecture.md`). Corregido.
2. **Archivo `AuthController` perdido / duplicado mal ubicado**: al corregir el punto anterior manualmente, `AuthController.java` terminó convertido en un archivo llamado `AuthService.java` dentro de la carpeta `controllers/` (con `package com.bankingsystem.services;` adentro, desalineado de su carpeta física — "Incorrect Package" en el IDE). El proyecto quedó momentáneamente sin ningún `AuthController` real. Se detectó revisando el árbol de archivos completo y se corrigió eliminando el archivo espurio y recreando `AuthController.java` en su ubicación correcta.
3. **`security/jwt/` nunca se creó en el proyecto real**: `JwtService.java` y `JwtAuthenticationFilter.java` habían sido generados en la sesión de trabajo pero nunca se copiaron al proyecto, causando `JwtService cannot be resolved to a type`. Se recrearon y ubicaron correctamente.

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS**, confirmado por el usuario sin subrayados de error en el IDE.

**Pendiente**: no se verificó todavía la ejecución en runtime de los 4 endpoints (`mvn spring-boot:run` + pruebas `curl` contra PostgreSQL real). Queda como primer punto de la próxima sesión.

---

# Decisiones tomadas

## ADR-008 (detalle en `decisiones.md`)

Usar `ResponseStatusException` en el módulo Auth en vez de crear ya la jerarquía de excepciones de negocio de `02_architecture.md`, por ser prematura antes de conocer las necesidades de Fase 3. Migrar cuando corresponda.

## Convención de paquetes confirmada

Se reafirmó (no es una decisión nueva, sino una corrección de implementación) que la convención real del proyecto es **plural**: `controllers`, `services`, `repositories`, `entities`, `exceptions`. Debe respetarse en todo código nuevo.

---

# Estado actual

## Documentación

Completa, con ADR-008 registrado.

## Backend

Módulo Auth completo a nivel de código: DTOs, `RoleRepository`, `JwtService`, `JwtAuthenticationFilter`, `AuthService`, `AuthController`, `GlobalExceptionHandler`, y `SecurityConfig` con el filtro JWT insertado. Compilando (`BUILD SUCCESS`). Sin verificación en runtime todavía. Sin DTOs/Mappers/Services/Controllers del resto de entidades (`Customer`, `Account`, `Transfer`, etc.).

## Control de Versiones

Sin cambios respecto a la Sesión 6 (backend subido hasta Spring Security). **Pendiente subir el módulo Auth de esta sesión.**

## Base de Datos / Docker

Sin cambios respecto a la Sesión 4.

---

# Próxima sesión

1. Verificar en runtime el flujo completo de Auth (`register` → `login` → `me` con token → `refresh`) con `mvn spring-boot:run` y PostgreSQL/RabbitMQ en Docker.
2. Si todo funciona: commit/push del módulo Auth a GitHub y cerrar formalmente la Fase 2 en `tareas.md`.
3. Iniciar Fase 3: DTOs y Mappers (MapStruct) del resto de entidades, comenzando por `Customer`, y luego `CustomerRepository`/`CustomerService`/`CustomerController` según `11_endpoints.md`.

---

# Fin de 












---

# Sesión 8 — 06/08/2026

## Objetivo de la sesión

Iniciar Fase 4 (funcionalidades avanzadas) con el módulo de **Préstamos** (RF-05, CU-08, CU-09).

## Verificación previa

Antes de escribir código nuevo, se confirmó en runtime (vía `dir /s /b` sobre `controllers/`, `services/`, `repositories/`) que los módulos de **Customer, Account y Transfer** (marcados como pendientes en la última sincronización de `AI_HANDOFF.md`) ya estaban implementados y correctamente ubicados en el proyecto real, y que compilaban (`mvn clean install` → BUILD SUCCESS, 55 archivos fuente). Esto no estaba reflejado en `tareas.md`/`bitacora.md` hasta ahora.

## Trabajo realizado

Se implementó el módulo de Préstamos completo a nivel de código, siguiendo el mismo patrón ya establecido por `CustomerController`/`AccountService`:

- `dto/loan/LoanRequest.java`, `dto/loan/LoanResponse.java`.
- `mapper/LoanMapper.java` (MapStruct; sin `toEntity`, ya que crear un `Loan` requiere lógica de negocio — cálculo de cuota, resolución del customer autenticado — que corresponde al Service, no al Mapper).
- `repositories/LoanRepository.java`.
- `services/LoanService.java`: `requestLoan` (CU-08), `list`/`getById` con restricción de propiedad para rol CUSTOMER (mismo criterio que `AccountService.checkOwnership`), `approve`/`reject` (CU-09).
- `controllers/LoanController.java`: `POST /loans`, `GET /loans`, `GET /loans/{id}`, `PATCH /loans/{id}/approve`, `PATCH /loans/{id}/reject`, según `11_endpoints.md`.

Ver ADR-009 en `decisiones.md`: tasa de interés fija y global vía `application.yml` (`loan.interest-rate.annual: 0.12`).

## 






Resumen de archivos nuevos/modificados en esta ronda

Nuevos: config/RabbitMQConfig.java, events/audit/AuditEvent.java, events/audit/AuditEventPublisher.java, events/audit/AuditEventListener.java, repositories/AuditLogRepository.java, dto/audit/AuditLogResponse.java, mapper/AuditLogMapper.java, services/AuditService.java, controllers/AuditController.java, common/AuditContext.java, dto/account/AccountRequest.java

Modificados: TransferService.java, AuthService.java, AccountService.java, AccountController.java, LoanService.


















# Bitácora del Proyecto

## Banking System

Este documento registra el historial de desarrollo del proyecto.

---

# Sesión 1 - Planificación y Documentación Inicial

## Fecha

16/07/2026

---

## Objetivo de la sesión

Crear la base documental del proyecto antes de iniciar el desarrollo.

---

# Actividades realizadas

## Creación de estructura inicial

Se definió la estructura principal del repositorio:

- backend/
- frontend/
- database/
- docker/
- docs/
- screenshots/

---

# Documentación creada

Se completaron los siguientes documentos:

## Documentación principal

- PROJECT_CONTEXT.md
- README.md

## Arquitectura

- docs/architecture/03_architecture.md
- docs/architecture/05_conventions.md

## Base de datos

- docs/database/07_database_model.md
- docs/database/08_entities.md

## API

- docs/api/10_api_design.md
- docs/api/11_endpoints.md

## Requisitos

- docs/requirements/01_requirements.md
- docs/requirements/02_use_cases.md

## Gestión del proyecto

- docs/management/tareas.md
- docs/management/decisiones.md
- docs/management/bitacora.md

## Diagramas

- docs/diagrams/README.md

## Planificación

- docs/roadmap/13_roadmap.md

---

# Decisiones tomadas

## Documentación antes del código

Se decidió no iniciar programación hasta completar la planificación del sistema.

Motivo:

Reducir errores de arquitectura y permitir que cualquier IA o desarrollador pueda incorporarse al proyecto.

---

## Uso de múltiples IA

El proyecto será desarrollado utilizando documentación como fuente principal de contexto.

Las herramientas de IA podrán cambiarse sin perder el progreso.

---

## Arquitectura definida

Backend:

Java Spring Boot

Arquitectura:

Controller → Service → Repository → Database

Frontend:

Angular organizado por funcionalidades.

Base de datos:

PostgreSQL

Mensajería:

RabbitMQ

Contenedores:

Docker

Seguridad:

JWT + Spring Security

---

# Estado actual

## Desarrollo

No iniciado.

## Backend

Pendiente.

## Frontend

Pendiente.

## Base de datos

Diseñada, sin implementación.

## API

Diseñada, sin implementación.

---

# Próxima sesión

Inicio de la fase de desarrollo.

Orden:

1. Inicializar Git.
2. Crear repositorio GitHub.
3. Crear proyecto Spring Boot.
4. Configurar Maven.
5. Configurar PostgreSQL.
6. Configurar Docker.
7. Crear estructura inicial del Backend.

---

# Fin de sesión

La fase de documentación inicial fue completada correctamente.

---

# Sesión 2 - Requisitos, Casos de Uso y Control de Versiones

## Fecha

16/07/2026

---

## Objetivo de la sesión

Completar el documento de requisitos y casos de uso pendientes, y adelantar la configuración de control de versiones del proyecto.

---

# Actividades realizadas

## Documentación

Se completaron:

- docs/requirements/01_requirements.md (requisitos funcionales, no funcionales y reglas de negocio)
- docs/requirements/02_use_cases.md (12 casos de uso derivados de los requisitos)

## Control de Versiones

Se realizó lo siguiente:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub: `https://github.com/Sir-Gabriel90xp/Banking-system-.git`
- Creación de archivo `.gitignore` para stack Spring Boot + Angular + Docker.
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

---

# Decisiones tomadas

## Adelantar control de versiones (ADR-005)

Se decidió inicializar Git/GitHub antes de completar la documentación de entidades detalladas y diseño final de endpoints, para no perder el progreso documental existente y dejar preparado el entorno de trabajo colaborativo.

Ver detalle en `decisiones.md` (ADR-005).

---

# Estado actual

## Documentación

Requisitos y casos de uso: completados.

Entidades detalladas y diseño final de endpoints: pendientes.

## Control de Versiones

Completado.

## Desarrollo

No iniciado.

---

# Próxima sesión

Continuar con la documentación pendiente:

1. Completar entidades detalladas (08_entities.md).
2. Completar diseño final de endpoints (11_endpoints.md).
3. Preparación del entorno (Spring Boot, Maven, PostgreSQL, Docker).

---

# Fin de sesión

---

# Sesión 3 - Creación del Proyecto Spring Boot

## Fecha

16/07/2026

---

## Objetivo de la sesión

Crear el esqueleto del proyecto backend en Spring Boot y validar que compila correctamente en el entorno de desarrollo.

---

# Actividades realizadas

## Estructura del proyecto

Se creó la estructura completa del backend en `banking-system/backend/`, respetando el paquete definido en `02_architecture.md`:

```
com.bankingsystem/
├── config/
├── security/
├── controllers/
├── services/
├── repositories/
├── entities/
├── dto/
├── mapper/
├── exceptions/
├── validators/
├── events/
├── audit/
├── fraud/
└── utils/
```

## Incidencias técnicas y resolución

Durante la puesta en marcha surgieron varios problemas de entorno, resueltos en el orden siguiente:

1. **pom.xml vacío tras extracción de .tar.gz en Windows** — se solucionó entregando los archivos sueltos (`pom.xml`, clase principal, `application.yml`) en vez de un paquete comprimido.
2. **Incompatibilidad Lombok + JDK 25** (`ExceptionInInitializerError: TypeTag :: UNKNOWN`) — se evaluó bajar a Java 21 vs. actualizar todo el stack a una versión soportada de Spring Boot compatible con Java 25. Se decidió lo segundo (ver ADR-006).
3. **Spring Boot 3.3.4 resultó estar en EOL** — se migró a **Spring Boot 4.1.0** (rama activamente soportada), lo que implicó:
   - Actualizar Lombok a 1.18.44.
   - Renombrar starters (`spring-boot-starter-web` → `spring-boot-starter-webmvc`, Flyway ahora con starter dedicado).
   - Usar starters de test específicos por tecnología en vez de un `spring-boot-starter-test` genérico.
   - Actualizar springdoc-openapi a 3.0.3 (compatible con Spring Framework 7).
4. **Error de empaquetado "Unable to find main class"** — resuelto especificando `<mainClass>` explícitamente en `spring-boot-maven-plugin`.

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS**

El proyecto compila, empaqueta y se instala correctamente en el repositorio local Maven (`.m2`).

---

# Decisiones tomadas

## Java 25 + Spring Boot 4.1.0 (ADR-006)

Se decidió construir el backend sobre Java 25 (LTS, ya instalado) y Spring Boot 4.1.0 (rama activamente soportada), en lugar de forzar Java 21 o mantener Spring Boot 3.x ya en EOL. Ver detalle completo en `decisiones.md` (ADR-006).

---

# Estado actual

## Documentación

Entidades detalladas y diseño final de endpoints: pendientes.

## Backend

Proyecto Spring Boot creado y compilando correctamente. Sin código de negocio todavía (carpetas vacías con `.gitkeep`, a la espera de cerrar la documentación de entidades y endpoints).

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

Definir el camino a seguir: retomar documentación pendiente (entidades detalladas y endpoints) antes de escribir código de negocio, según el flujo original del proyecto.

---

# Fin de sesión

---

# Sesión 4 - Preparación del Entorno: PostgreSQL, Docker y Esquema Inicial

## Fecha

17-18/07/2026

---

## Objetivo de la sesión

Completar la documentación de entidades y endpoints (`08_entities.md`, `11_endpoints.md`), corregir el desfase existente en `tareas.md`/`bitacora.md`, y preparar el entorno de infraestructura (PostgreSQL + RabbitMQ vía Docker) para poder implementar el esquema de base de datos.

---

# Actividades realizadas

## Corrección de desfase documental

Se detectó que `AI_HANDOFF.md` indicaba `08_entities.md` y `11_endpoints.md` como completados, mientras que `tareas.md` y `bitacora.md` (Sesión 3) seguían marcándolos como pendientes. Se confirmó que ambos documentos ya estaban completos y se corrigió el desfase en esta sesión.

## Infraestructura Docker

Se creó `docker/docker-compose.yml` con los 4 servicios previstos en `02_architecture.md` (RNF-05): `postgres`, `rabbitmq`, `backend`, `frontend`. Se configuró `.env` con credenciales propias.

Se levantaron los servicios de infraestructura:

```
docker compose up -d postgres rabbitmq
```

Resultado: `banking-postgres` y `banking-rabbitmq` en estado `Up (healthy)`.

Se verificó el panel de administración de RabbitMQ en `http://localhost:15672` con éxito.

## Configuración de Spring Boot

Se crearon los perfiles de aplicación:

- `application.yml` (base, con Flyway y configuración JWT/Swagger)
- `application-dev.yml` (Postgres/RabbitMQ en localhost)
- `application-docker.yml` (Postgres/RabbitMQ vía nombres de servicio Docker)

Se creó `Dockerfile` multi-stage para el backend (Java 25 + Maven).

## Esquema de Base de Datos

Se creó la migración Flyway `V1__init_schema.sql` basada en `03_database_model.md`, implementando las 10 entidades principales (`role`, `app_user`, `customer`, `account`, `transaction`, `transfer`, `loan`, `payment`, `audit_log`, `fraud_alert`) con:

- Claves primarias UUID (`gen_random_uuid()`, extensión `pgcrypto`).
- Constraints de reglas de negocio a nivel de base de datos: `CHECK (balance >= 0)` (RN-01), `CHECK (origin_account_id <> destination_account_id)` (RN-02), `UNIQUE` en email/username/documento/número de cuenta (RN-03).
- Índices en columnas de búsqueda frecuente.
- Timestamps de auditoría (`created_at`, `updated_at`, `created_by`, `updated_by`) y soft delete donde aplica (RNF-07).
- Seed inicial de los 3 roles del sistema (ADMIN, EMPLOYEE, CUSTOMER).

La migración se ejecutó manualmente contra el contenedor `banking-postgres` (vía `docker cp` + `psql -f`) para validar el esquema antes de integrarlo al arranque automático de Flyway en el backend. Resultado: las 10 tablas se crearon correctamente y el seed de roles quedó insertado, verificado con `\dt` y `SELECT name FROM role;`.

## Incidencia técnica

Al intentar copiar la migración al contenedor, el primer archivo se copió vacío (0 bytes) porque se había creado localmente sin contenido real. Se resolvió descargando el archivo generado y verificando su tamaño (`dir`) antes de repetir la copia.

---

# Decisiones tomadas

## Nomenclatura `app_user` en vez de `user`

Se decidió nombrar la tabla de usuarios `app_user` en lugar de `user`, ya que `user` es palabra reservada en PostgreSQL. Es una decisión de nomenclatura sobre la implementación, no un cambio al modelo conceptual definido en `03_database_model.md`.

## Payment sin entidad de Servicio separada

`payment.loan_id` se definió como nullable para permitir pagos de servicios (RF-06.2) sin necesidad de una tabla de "Servicio" adicional, ya que `03_database_model.md` no la contemplaba explícitamente.

---

# Estado actual

## Documentación

Completa (incluye entidades detalladas y diseño final de endpoints).

## Backend

Esqueleto Spring Boot compilando. Perfiles de configuración (`dev`, `docker`) listos. Sin entidades JPA ni lógica de negocio todavía.

## Base de Datos

Implementada. Esquema inicial (`V1__init_schema.sql`) ejecutado con éxito sobre PostgreSQL en Docker. 10 tablas + seed de roles verificados.

## Docker

PostgreSQL y RabbitMQ operativos y verificados (`healthy`). Backend y Frontend con Dockerfile listo, pendiente de build real (sin código de negocio aún).

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

1. Crear las entidades JPA (`@Entity`) que mapeen el esquema ya implementado, comenzando por `Role` y `AppUser`.
2. Crear DTOs y Mappers correspondientes.
3. Iniciar la implementación de seguridad JWT (Fase 2 de `tareas.md`).

---

# Fin de sesión

---

# Sesión 5 - Entidades JPA Completas

## Fecha

18/07/2026

---

## Objetivo de la sesión

Crear las 10 entidades JPA (`@Entity`) que mapeen exactamente el esquema ya implementado en PostgreSQL (`V1__init_schema.sql`), respetando la arquitectura por capas y sin introducir lógica de negocio en las entidades.

---

# Actividades realizadas

## Diseño de clases base reutilizables

Se crearon dos clases `@MappedSuperclass` en `entities/base/` para evitar repetir código entre entidades (DRY):

- **`Auditable`**: clave primaria UUID (generada con `@UuidGenerator` de Hibernate, coherente con `gen_random_uuid()` del esquema) + campos `createdAt`, `updatedAt`, `createdBy`, `updatedBy` (RNF-07), con `equals`/`hashCode` basados en `id` (con manejo seguro de proxies de Hibernate, evitando el `@Data` de Lombok a propósito).
- **`SoftDeletableEntity`**: extiende `Auditable`, agrega `deleted`/`deletedAt` (RNF-07) para las entidades que sí tienen soft delete en el esquema.

## Entidades creadas

Se implementaron las 10 entidades principales, todas en `com.bankingsystem.entities`:

| Entidad | Extiende | Notas |
|---|---|---|
| `Role` | `Auditable` | Sin soft delete en el esquema |
| `AppUser` | `SoftDeletableEntity` | Mapeada a la tabla `app_user`; relación `@ManyToOne` con `Role` |
| `Customer` | `SoftDeletableEntity` | `user` (AppUser) es `@ManyToOne` opcional, sin `UNIQUE` en BD — ver decisión abajo |
| `Account` | `SoftDeletableEntity` | Balance con `BigDecimal`; la regla RN-01 (no negativos) ya está garantizada por el `CHECK` de la BD, no se repite en la entidad |
| `Transaction` | `Auditable` (sin soft delete) | `type` como enum, `status` como texto simple (sin dominio cerrado definido en el SQL) |
| `Transfer` | `Auditable` (sin soft delete) | RN-02 (cuentas distintas) ya garantizada por `CHECK` de la BD |
| `Loan` | `Auditable` (sin soft delete) | `status` como enum (`PENDING`, `APPROVED`, `REJECTED`, `PAID`, según RF-05.5) |
| `Payment` | `Auditable` (sin soft delete) | `loan` opcional (pagos de servicios, decisión de Sesión 4) |
| `AuditLog` | **Ninguna clase base** | La tabla no tiene `created_by`/`updated_by`; se maneja de forma independiente |
| `FraudAlert` | **Ninguna clase base** | La tabla tiene `created_at`/`updated_at` pero NO `created_by`/`updated_by`; timestamps manejados con `@PrePersist`/`@PreUpdate` propios |

También se crearon enums de apoyo en `entities/enums/`: `CustomerStatus`, `AccountType`, `AccountStatus`, `TransactionType`, `LoanStatus`, `FraudSeverity` — solo para los campos donde el SQL define explícitamente una lista cerrada de valores.

## Verificación

```
mvn clean install
```

➡️ **BUILD SUCCESS** con las 10 entidades ya integradas al proyecto.

---

# Decisiones tomadas

## Customer–AppUser sin restricción UNIQUE

Se confirmó dejar `customer.user_id` como `@ManyToOne` (no `@OneToOne`), reflejando exactamente lo que la base de datos permite hoy (sin `UNIQUE`), en vez de forzar la relación 1:1 del modelo conceptual con una nueva migración. Queda abierta la posibilidad de agregar el `UNIQUE` más adelante si se decide reforzar esa regla a nivel de base de datos.

## AuditLog y FraudAlert no heredan de Auditable

Se detectó que ambas tablas tienen un esquema de auditoría distinto al resto (`AuditLog` no tiene ninguno de los 4 campos estándar; `FraudAlert` tiene `created_at`/`updated_at` pero no `created_by`/`updated_by`). En vez de forzarlas a extender `Auditable` (lo que rompería el mapeo con columnas inexistentes), se implementaron como entidades independientes.

---

# Estado actual

## Documentación

Completa.

## Backend

Las 10 entidades JPA implementadas y compilando correctamente (`BUILD SUCCESS`). Sin DTOs, Mappers, Services ni Controllers todavía.

## Base de Datos

Sin cambios respecto a la Sesión 4 (implementada, sin migraciones nuevas).

## Docker

Sin cambios respecto a la Sesión 4.

## Control de Versiones

Completado (sesión 2).

---

# Próxima sesión

1. Crear los DTOs y Mappers correspondientes a las 10 entidades (nunca exponer entidades directamente — ADR-004).
2. Iniciar la implementación de seguridad JWT (Fase 2 de `tareas.md`).

---

# Fin de sesión

---

# Sesión 6 - Subida de código a GitHub y Spring Security

## Fecha

18/07/2026

---

## Objetivo de la sesión

Subir a GitHub el código que hasta ahora solo existía localmente (backend, entidades, Docker, esquema BD) y comenzar la Fase 2 implementando Spring Security, según el orden redefinido en ADR-007.

---

# Actividades realizadas

## Corrección de .gitignore

Se detectó que el `.gitignore` del repo remoto no coincidía con lo documentado: era un `.gitignore` genérico (`node_modules/`, `dist/`, `build/`, `.env`, `__pycache__/`, `*.log`) sin ninguna exclusión real para Maven/Spring Boot (`target/`) ni para IDEs (`.idea/`, `*.iml`). Se reemplazó por un `.gitignore` correcto para el stack Spring Boot + Angular + Docker.

## Subida del backend a GitHub

Se subió por primera vez a GitHub todo el código que existía solo localmente desde la Sesión 3:

- Esqueleto Spring Boot (`pom.xml`, `Dockerfile`, clase principal).
- Las 10 entidades JPA + clases base + enums (Sesión 5).
- Perfiles de configuración (`application.yml`, `application-dev.yml`, `application-docker.yml`).
- Esquema de base de datos (`V1__init_schema.sql`).
- `docker/docker-compose.yml`.

Durante el proceso se limpiaron archivos que no debían subirse: `backend/target/` (compilado), un `.tar.gz` residual del incidente de la Sesión 3, y copias sueltas duplicadas de `BankingSystemApplication.java`/`application.yml` fuera de su ubicación real en `src/main/`.

Commits resultantes en `develop`: `39a806d` (código) y `ef350fd` (sincronización de documentación).

## ADR-007: adelantar Spring Security antes que DTOs/Mappers

Se decidió invertir el orden documentado en `AI_HANDOFF.md`: implementar primero Spring Security, registro, login, JWT y protección de endpoints (Fase 2 completa), y retomar después los DTOs/Mappers del resto de entidades. Detalle completo en `decisiones.md` (ADR-007).

## Implementación de Spring Security

Se crearon los componentes base de seguridad:

- `AppUserRepository` (`repositories/`): `findByEmail`, `findByUsername`, checks de unicidad.
- `AppUserPrincipal` (`security/`): adapta `AppUser` al contrato `UserDetails` sin mezclar código de Spring Security dentro de la entidad JPA. Aplica el prefijo `ROLE_` en tiempo de ejecución (en BD los roles se guardan sin prefijo: `ADMIN`, `EMPLOYEE`, `CUSTOMER`).
- `CustomUserDetailsService` (`security/`): carga el usuario por email (el login se hace por email, no por username, según `11_endpoints.md`).
- `SecurityConfig` (`config/`): `PasswordEncoder` (BCrypt), `AuthenticationProvider`, `AuthenticationManager` y el filter chain (rutas públicas: `/auth/register`, `/auth/login`, `/auth/refresh`, Swagger; el resto requiere autenticación). El filtro JWT queda para la siguiente tarea, con el punto de inserción ya marcado (`TODO`).

## Incidencias técnicas y resolución

1. **`BankingSystemApplication.java` vacío (0 bytes)** en `src/main/java/com/bankingsystem/`. Es probable que, durante el incidente del `.tar.gz` de la Sesión 3, el archivo real haya quedado sin contenido y el que sí tenía el `@SpringBootApplication` fuera el duplicado suelto (ya eliminado al limpiar el repo). Se reconstruyó con su contenido estándar.
2. **`DaoAuthenticationProvider` sin constructor vacío**: en la versión de Spring Security que trae Spring Boot 4.1.0, el constructor sin argumentos fue eliminado y el método `setUserDetailsService(...)` también dejó de existir. Se corrigió pasando `CustomUserDetailsService` directo en el constructor (`new DaoAuthenticationProvider(customUserDetailsService)`).

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS** con Spring Security integrado.

---

# Decisiones tomadas

## ADR-007 (detalle en `decisiones.md`)

Adelantar Spring Security/JWT antes que los DTOs y Mappers del resto de entidades. Es un reordenamiento de tareas, no un cambio de arquitectura ni de alcance.

---

# Estado actual

## Documentación

Completa, con ADR-007 registrado.

## Control de Versiones

Backend, entidades, Docker y esquema BD subidos a GitHub por primera vez (antes solo existían localmente). `.gitignore` corregido.

## Backend

Spring Security implementado (config, UserDetailsService, PasswordEncoder, filter chain) y compilando (`BUILD SUCCESS`). Sin JWT todavía, sin DTOs, sin controllers/services de negocio.

## Base de Datos / Docker

Sin cambios respecto a la Sesión 4.

---

# Próxima sesión

1. Crear DTOs mínimos de Auth (`RegisterRequest`, `LoginRequest`, `AuthResponse`).
2. Implementar `JwtService` (generación/validación de access y refresh token; configuración ya lista en `application.yml`).
3. Implementar `AuthController`/`AuthService` con `/api/v1/auth/register` y `/api/v1/auth/login` (`11_endpoints.md`).
4. Insertar el `JwtAuthenticationFilter` en `SecurityConfig` (punto ya marcado con `TODO`).

---

# Fin de sesión












---

# Sesión 7 - Registro, Login, JWT y Protección de Endpoints

## Fecha

18/07/2026

---

## Objetivo de la sesión

Cerrar la Fase 2 de Seguridad implementando lo que quedó pendiente al final de la Sesión 6: DTOs de Auth, `JwtService`, `AuthController`/`AuthService` (registro y login) y el `JwtAuthenticationFilter` insertado en `SecurityConfig`, según el orden fijado en ADR-007.

---

# Actividades realizadas

## DTOs de Auth

Se crearon en `dto/auth/`: `RegisterRequest`, `RegisterResponse`, `LoginRequest`, `AuthResponse`, `RefreshTokenRequest`, `TokenRefreshResponse`, `UserProfileResponse`. Se agregó también `dto/common/ApiResponse.java`, el envoltorio genérico de respuesta (`success/message/data/errors/timestamp`) definido en `04_api_design.md`, usado por todos los endpoints nuevos.

## RoleRepository (nuevo)

No existía. Fue necesario para resolver el `role` recibido en `RegisterRequest` (ej. `"ROLE_CUSTOMER"`) contra la tabla `role` ya sembrada en `V1__init_schema.sql`.

## JwtService y JwtAuthenticationFilter

En `security/jwt/`:

- `JwtService`: generación y validación de access token y refresh token, usando la API de `jjwt` 0.12.6 (`Jwts.builder()`/`Jwts.parser()` con `SecretKey`). El subject del token es el `id` (UUID) del `AppUser`, no el email. Se agregó un claim `type` (`access`/`refresh`) para que ningún endpoint acepte el tipo de token equivocado.
- `JwtAuthenticationFilter`: puebla el `SecurityContext` a partir de un access token válido en el header `Authorization`.

## AuthService y AuthController

`services/AuthService.java` implementa `register`, `login`, `refresh` y `getProfile` (CU-01, CU-02, RF-01.1 a RF-01.4). `controllers/AuthController.java` expone `/api/v1/auth/register`, `/login`, `/refresh` y `/me`, según `11_endpoints.md`.

## GlobalExceptionHandler (nuevo)

Se creó `exceptions/GlobalExceptionHandler.java` para traducir errores al formato estándar de `04_api_design.md`. Ver ADR-008: se usó `ResponseStatusException` de Spring en vez de crear ya la jerarquía completa de excepciones de negocio de `02_architecture.md`, por ser prematura antes de Fase 3.

## SecurityConfig actualizado

Se insertó `.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)` en el punto que ya estaba marcado con `TODO` desde la Sesión 6, junto con el campo `jwtAuthenticationFilter` inyectado por `@RequiredArgsConstructor`. Se confirmó que `/api/v1/auth/me` no estaba en `PUBLIC_ENDPOINTS`, por lo que ya quedaba protegido correctamente sin modificar esa lista.

## Incidencias técnicas y resolución

1. **Paquetes en singular en vez de plural**: los primeros borradores de `AuthController.java`/`AuthService.java` se generaron con `package com.bankingsystem.controller;` / `.service;` (singular), inconsistente con la convención real del resto del proyecto (`controllers`, `services`, `repositories`, `entities`, plural — `02_architecture.md`). Corregido.
2. **Archivo `AuthController` perdido / duplicado mal ubicado**: al corregir el punto anterior manualmente, `AuthController.java` terminó convertido en un archivo llamado `AuthService.java` dentro de la carpeta `controllers/` (con `package com.bankingsystem.services;` adentro, desalineado de su carpeta física — "Incorrect Package" en el IDE). El proyecto quedó momentáneamente sin ningún `AuthController` real. Se detectó revisando el árbol de archivos completo y se corrigió eliminando el archivo espurio y recreando `AuthController.java` en su ubicación correcta.
3. **`security/jwt/` nunca se creó en el proyecto real**: `JwtService.java` y `JwtAuthenticationFilter.java` habían sido generados en la sesión de trabajo pero nunca se copiaron al proyecto, causando `JwtService cannot be resolved to a type`. Se recrearon y ubicaron correctamente.

## Resultado final

```
mvn clean install
```

➡️ **BUILD SUCCESS**, confirmado por el usuario sin subrayados de error en el IDE.

**Pendiente**: no se verificó todavía la ejecución en runtime de los 4 endpoints (`mvn spring-boot:run` + pruebas `curl` contra PostgreSQL real). Queda como primer punto de la próxima sesión.

---

# Decisiones tomadas

## ADR-008 (detalle en `decisiones.md`)

Usar `ResponseStatusException` en el módulo Auth en vez de crear ya la jerarquía de excepciones de negocio de `02_architecture.md`, por ser prematura antes de conocer las necesidades de Fase 3. Migrar cuando corresponda.

## Convención de paquetes confirmada

Se reafirmó (no es una decisión nueva, sino una corrección de implementación) que la convención real del proyecto es **plural**: `controllers`, `services`, `repositories`, `entities`, `exceptions`. Debe respetarse en todo código nuevo.

---

# Estado actual

## Documentación

Completa, con ADR-008 registrado.

## Backend

Módulo Auth completo a nivel de código: DTOs, `RoleRepository`, `JwtService`, `JwtAuthenticationFilter`, `AuthService`, `AuthController`, `GlobalExceptionHandler`, y `SecurityConfig` con el filtro JWT insertado. Compilando (`BUILD SUCCESS`). Sin verificación en runtime todavía. Sin DTOs/Mappers/Services/Controllers del resto de entidades (`Customer`, `Account`, `Transfer`, etc.).

## Control de Versiones

Sin cambios respecto a la Sesión 6 (backend subido hasta Spring Security). **Pendiente subir el módulo Auth de esta sesión.**

## Base de Datos / Docker

Sin cambios respecto a la Sesión 4.

---

# Próxima sesión

1. Verificar en runtime el flujo completo de Auth (`register` → `login` → `me` con token → `refresh`) con `mvn spring-boot:run` y PostgreSQL/RabbitMQ en Docker.
2. Si todo funciona: commit/push del módulo Auth a GitHub y cerrar formalmente la Fase 2 en `tareas.md`.
3. Iniciar Fase 3: DTOs y Mappers (MapStruct) del resto de entidades, comenzando por `Customer`, y luego `CustomerRepository`/`CustomerService`/`CustomerController` según `11_endpoints.md`.

---

# Fin de 












---

# Sesión 8 — 06/08/2026

## Objetivo de la sesión

Iniciar Fase 4 (funcionalidades avanzadas) con el módulo de **Préstamos** (RF-05, CU-08, CU-09).

## Verificación previa

Antes de escribir código nuevo, se confirmó en runtime (vía `dir /s /b` sobre `controllers/`, `services/`, `repositories/`) que los módulos de **Customer, Account y Transfer** (marcados como pendientes en la última sincronización de `AI_HANDOFF.md`) ya estaban implementados y correctamente ubicados en el proyecto real, y que compilaban (`mvn clean install` → BUILD SUCCESS, 55 archivos fuente). Esto no estaba reflejado en `tareas.md`/`bitacora.md` hasta ahora.

## Trabajo realizado

Se implementó el módulo de Préstamos completo a nivel de código, siguiendo el mismo patrón ya establecido por `CustomerController`/`AccountService`:

- `dto/loan/LoanRequest.java`, `dto/loan/LoanResponse.java`.
- `mapper/LoanMapper.java` (MapStruct; sin `toEntity`, ya que crear un `Loan` requiere lógica de negocio — cálculo de cuota, resolución del customer autenticado — que corresponde al Service, no al Mapper).
- `repositories/LoanRepository.java`.
- `services/LoanService.java`: `requestLoan` (CU-08), `list`/`getById` con restricción de propiedad para rol CUSTOMER (mismo criterio que `AccountService.checkOwnership`), `approve`/`reject` (CU-09).
- `controllers/LoanController.java`: `POST /loans`, `GET /loans`, `GET /loans/{id}`, `PATCH /loans/{id}/approve`, `PATCH /loans/{id}/reject`, según `11_endpoints.md`.

Ver ADR-009 en `decisiones.md`: tasa de interés fija y global vía `application.yml` (`loan.interest-rate.annual: 0.12`).

## Resultado

*(Nota de sincronización: esta sección quedó incompleta en la bitácora original — no hay registro del resultado del build ni de verificación en runtime para el módulo de Préstamos. Pendiente de confirmar por el usuario.)*

---

## Ronda 2 — Módulo de Auditoría (RabbitMQ) y ajustes en módulos existentes

### Objetivo de la ronda

Cerrar los puntos de Fase 4 "Auditoría" y "RabbitMQ Events" (RF-07, CU-12): registrar de forma asíncrona, vía RabbitMQ, las acciones relevantes del sistema (login, transferencias, creación/bloqueo de cuentas, préstamos).

### Trabajo realizado

**Nuevos:**

- `config/RabbitMQConfig.java` — configuración del exchange/queue/binding usados para publicar y consumir eventos de auditoría.
- `events/audit/AuditEvent.java` — evento de dominio que representa una acción auditable.
- `events/audit/AuditEventPublisher.java` — publica el `AuditEvent` a RabbitMQ.
- `events/audit/AuditEventListener.java` — consumidor asíncrono que persiste el evento como `AuditLog` (RF-07.3).
- `repositories/AuditLogRepository.java`.
- `dto/audit/AuditLogResponse.java` y `mapper/AuditLogMapper.java`.
- `services/AuditService.java` y `controllers/AuditController.java` — consulta de auditoría.
- `common/AuditContext.java` — captura el usuario autenticado y la IP de la petición actual, usado por los Services al construir el `AuditEvent`.
- `dto/account/AccountRequest.java` — DTO de request para Account que faltaba (deuda técnica de Fase 3).

**Modificados** (para publicar `AuditEvent` desde las operaciones sensibles ya existentes, según RF-07.1):

- `TransferService.java`
- `AuthService.java`
- `AccountService.java`
- `AccountController.java`
- `LoanService.java`

### Verificación previa (heredada de la Ronda 1 de esta sesión)

Se confirmó en runtime que los módulos de **Customer, Account y Transfer** ya estaban implementados y correctamente ubicados en el proyecto real, compilando (`mvn clean install` → BUILD SUCCESS, 55 archivos fuente). Esto no estaba reflejado en `tareas.md` hasta la actualización de hoy.

### Pendiente de confirmar

No incluido en el resumen recibido para esta ronda — queda como primer punto de la próxima sesión:

- Resultado de `mvn clean install` tras integrar el módulo de Auditoría (¿BUILD SUCCESS?).
- Verificación en runtime: confirmar que un login o una transferencia real generan un `AuditLog` visible vía `AuditController`.
- Endpoints exactos expuestos por `AuditController` (para reflejarlos en `11_endpoints.md` si no estaban contemplados).
- Manejo de fallos del `AuditEventListener` (reintentos / dead-letter queue), dado que RabbitMQ ya está en uso.
- Commit/push de esta ronda a GitHub.

### Decisiones relacionadas

- ADR-009 (Loan, Ronda 1) — pendiente de trasladar a `decisiones.md`, donde todavía no aparece registrada.
- El patrón evento + listener asíncrono para auditoría es consistente con lo ya definido en `02_architecture.md` (RabbitMQ para procesos desacoplados); no se identifica, con la información disponible, una decisión de arquitectura nueva que amerite un ADR adicional. A confirmar si `AuditContext` introduce algún criterio (p. ej. uso de un `RequestScope` o `ThreadLocal`) que sí valga la pena documentar.

---

# Fin de Sesión 8 (parcial — pendiente cierre con verificación en runtime y push a GitHub)
















## Sesión 10 — Frontend Angular: arranque completo + Clientes funcional

**Fecha:** 07-08 de agosto, 2026

### Contexto

Primera sesión de Frontend (Fase 5). Se partió de cero: proyecto Angular no existía.

### Lo que se hizo

1. **Setup del proyecto**
   - `ng new frontend` con Angular CLI 22.1.3 — se detectó que esta versión ya no usa sufijos (`login.ts` en vez de `login.component.ts`, clase `Login` en vez de `LoginComponent`). Documentado para no repetir el error de mezclar convenciones.
   - Estructura core/shared/features creada según `02_architecture.md`.
   - HttpClient + interceptor JWT (agrega `Authorization: Bearer` a cada request) + `authGuard` (protege rutas si no hay token en memoria).

2. **Incidencias de arranque de infraestructura (no relacionadas al frontend, pero bloqueaban las pruebas):**
   - Docker Desktop no estaba corriendo → error `dockerDesktopLinuxEngine`. Solución: abrir Docker Desktop antes de `docker compose up`.
   - `docker compose up -d` sin especificar servicios intentó buildear `frontend`/`backend` (que no tienen Dockerfile listo) → usar `docker compose up -d postgres rabbitmq` explícitamente mientras se desarrolla local.
   - Desfase de contraseña de Postgres: el volumen ya existente tenía una contraseña vieja distinta a la de `.env` actual (`Gabriel90xp`). Se resolvió con `docker compose down -v` (recrear volumen) + `set DB_PASSWORD=Gabriel90xp` en cada terminal antes de `mvn spring-boot:run` (la variable no persiste entre sesiones de terminal).
   - `AuditLogMapperImpl` con método `toResponse` duplicado (`ClassFormatError`) — causado por un `target/` con clases viejas no regeneradas; se resolvió con `mvn clean` antes de `spring-boot:run`.
   - Error de Hibernate 7 con `FraudAlert`/`Auditable` reespecificando la estrategia de generación del `@Id` heredado — resuelto (no se documentaron los detalles exactos del fix, confirmar en el código si se repite).
   - Puerto 8080 ocupado por instancias previas colgadas — recordatorio: `netstat -ano | findstr :8080` + `taskkill /PID <pid> /F`.

3. **Login conectado al backend real**
   - Desajuste de contrato: el backend devuelve `{ token, refreshToken, expiresIn }`, no `{ accessToken, refreshToken }` como se había asumido inicialmente. Corregido en `AuthResponse` y `AuthService`.
   - `UserProfile` también corregido: el backend devuelve `role` con prefijo (`"ROLE_ADMIN"`, no `"ADMIN"`) y un campo `enabled` adicional.
   - Confirmado con `curl` contra `/auth/register`, `/auth/login` y `/auth/me` — los tres funcionan.

4. **Rediseño visual completo**
   - Se abandonó el estilo default de Angular por una identidad propia: paleta "ledger/libreta contable" — tinta oscura (`#0B1220`) + papel claro (`#F6F4EF`) + acento dorado (`#B8923F`) + esmeralda para créditos (`#1B6B4A`) + terracota-ladrillo para débitos/alertas (`#A8432A`). Tipografías: Fraunces (display), Inter (body), IBM Plex Mono (cifras, con `tabular-nums`).
   - Tokens centralizados en `src/styles.scss` (`:root` con variables CSS), usados en todos los componentes.
   - `Shell` (sidebar + topbar) creado como layout compartido con `router-outlet`, para no repetir el sidebar en cada página.
   - Login y Register restyleados con la misma identidad (`login-card` centrada sobre fondo tinta con gradiente radial sutil).

5. **Módulo Clientes (CRUD completo)**
   - Se descubrió que `GET /customers` devuelve `Page<CustomerResponse>` de Spring Data (`content`, `totalElements`, etc.), no un array plano — se creó `Page<T>` genérico en `shared/models/page.model.ts` para reutilizar en todos los módulos futuros.
   - `customer-list` (tabla con paginación y búsqueda) + `customer-form` (crear/editar con reactive forms) construidos y conectados a `CustomerService` real.
   - DTOs reales confirmados leyendo `CustomerRequest.java` / `CustomerResponse.java` / `CustomerController.java` directamente del backend — se evitó adivinar campos esta vez.

6. **Hallazgo de seguridad real (no solo cosmético)**
   - Se registró un usuario público (`Gerardo`, `ROLE_CUSTOMER`) y se comprobó que podía navegar a `/customers` y ver la tabla de clientes — el sidebar no distinguía roles, y **el backend tampoco**: `CustomerController.list()` no tenía `@PreAuthorize`, solo `create`/`update`/`delete`.
   - Se corrigió en el frontend: sidebar condicional por rol (`isAdminOrEmployee` / `isAdmin` computed signals en `Shell`) + `roleGuard` reutilizable que bloquea la ruta si el rol no matchea, redirigiendo a `/dashboard`.
   - **Pendiente en el backend**: agregar `@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")` al método `list()` de `CustomerController`, y auditar el resto de controllers (Account, Transfer, Loan, Payment) por el mismo patrón — el frontend nunca debe ser la única barrera de autorización.

### Decisión de flujo de trabajo

Se estableció explícitamente: en vez de generar módulos completos "a ciegas" adivinando la forma de las respuestas del backend, cada módulo nuevo se construye confirmando primero con `curl` + lectura directa de los DTOs/Controller reales del backend, para evitar los desajustes de contrato que costaron varias vueltas en el login (accessToken vs token, role sin prefijo, etc.).

### Estado al cierre

Login, Registro y Clientes funcionando end-to-end contra el backend real, con estética propia aplicada. Cuentas es el siguiente módulo, ya con el `AccountService` arrancado (pendiente de corregir a `Page<Account>`).
---

# Sesion 11 - Frontend conectado + alta admin de clientes de prueba

## Fecha

16/08/2026

## Objetivo

Permitir que un ADMIN, desde el apartado de Clientes, cree clientes utilizables para pruebas: con usuario de acceso CUSTOMER, cuenta inicial y saldo real persistido por backend.

## Trabajo realizado

- Backend:
  - `AccountRequest` acepta `initialBalance` opcional.
  - `AccountService.createAccount` usa el saldo inicial validado y mantiene moneda `USD`.
  - `AccountController.listAccounts` pasa `Authentication` al service.
  - `AccountService.listAccounts` filtra automaticamente por el customer enlazado cuando el usuario autenticado tiene rol `ROLE_CUSTOMER`.
  - `CustomerRequest` acepta campos opcionales para onboarding: `createLoginUser`, `username`, `password`, `createInitialAccount`, `initialAccountType`, `initialBalance`.
  - `CustomerService.create` puede crear un `AppUser` con rol `CUSTOMER`, enlazarlo al `Customer`, y abrir una cuenta inicial con saldo.
  - `CustomerController.getById` queda restringido a `ADMIN`/`EMPLOYEE`, igual que la lista.

- Frontend:
  - `customer-form` permite crear usuario de acceso y cuenta inicial al registrar un cliente.
  - `account-form` reemplaza el texto libre por selector real `SAVINGS`/`CHECKING` y agrega saldo inicial.
  - `account.model.ts` queda sincronizado con `AccountRequest`, `AccountResponse` y `BalanceResponse`.
  - Dashboard: los botones rapidos ahora navegan a transferencias, pagos y prestamos.
  - Se agrego modulo de Fraude: modelo, service, ruta `/fraud`, lista conectada a `GET /fraud-alerts` y accion `PATCH /fraud-alerts/{id}/resolve`.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend no pudo compilarse desde esta terminal porque no existe `mvn`, `mvn.cmd`, `MAVEN_HOME` ni Maven Wrapper en el repo. Queda pendiente ejecutar `mvn test` o `mvn clean install` en un entorno con Maven.

## Pendiente

- Verificar en runtime el flujo admin: crear cliente con usuario + cuenta inicial, iniciar sesion con ese usuario CUSTOMER y confirmar que ve solo sus cuentas.
- Ejecutar build backend con Maven instalado o agregar Maven Wrapper al proyecto.

---

# Sesion 12 - Hardening de Transferencias y acciones por rol

## Fecha

16/08/2026

## Objetivo

Continuar la auditoria funcional del frontend/backend y cerrar huecos de autorizacion en modulos que exponen movimientos o historiales.

## Trabajo realizado

- Backend:
  - `TransferController.getHistory` ahora recibe `Authentication`.
  - `TransferService.getHistory` valida acceso a `accountId` cuando viene por query param.
  - `TransferService.getHistory` restringe automaticamente el historial a cuentas del customer autenticado cuando el rol es `ROLE_CUSTOMER`.
  - `TransferRepository.findHistory` agrega filtro opcional por `customerId` con `@Param` explicitos.

- Frontend:
  - Dashboard ajusta acciones rapidas por rol:
    - ADMIN/EMPLOYEE: Nuevo cliente, Nueva cuenta, Ver transferencias.
    - CUSTOMER: Transferir, Pagar prestamo, Solicitar prestamo.
  - Se mantiene Pagos alineado al contrato actual: registra pagos de prestamos aprobados; no simula debito de cuenta porque `PaymentRequest` todavia no tiene `accountId`.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend sigue pendiente de build por falta de Maven (`mvn`/`mvn.cmd` no disponibles y no existe Maven Wrapper).

## Pendiente

- Agregar Maven Wrapper o instalar Maven para validar backend.
- Probar runtime completo con backend levantado.
- Decidir si Pagos debe debitar una cuenta bancaria real; si se requiere, ampliar backend con `accountId` en `PaymentRequest` y usar `AccountService.debit`.

---

# Sesion 13 - Pagos con debito real de cuenta

## Fecha

16/08/2026

## Objetivo

Convertir el modulo de Pagos en una operacion bancaria real: registrar el pago de un prestamo y descontar el monto desde una cuenta activa del cliente.

## Trabajo realizado

- Base de datos:
  - Nueva migracion `V3__add_payment_account.sql`.
  - Agrega `payment.account_id` como FK nullable hacia `account(id)`.
  - Agrega indice `idx_payment_account_id`.

- Backend:
  - `Payment` ahora tiene relacion opcional con `Account`.
  - `PaymentRequest` exige `accountId`.
  - `PaymentResponse` devuelve `accountId` y `accountNumber`.
  - `PaymentMapperImpl` mapea los datos de la cuenta origen.
  - `PaymentService.registerPayment`:
    - valida prestamo existente y aprobado,
    - valida propiedad/acceso del prestamo,
    - valida propiedad/acceso de la cuenta,
    - exige que la cuenta pertenezca al mismo customer del prestamo,
    - debita saldo real con `AccountService.debit`,
    - registra el pago en la misma transaccion.

- Frontend:
  - `payment-form` carga prestamos aprobados y cuentas activas.
  - Filtra cuentas por el customer del prestamo seleccionado.
  - Envia `loanId`, `accountId`, `amount` y `paymentMethod = ACCOUNT_DEBIT`.
  - `payment-list` muestra la cuenta origen del pago.
  - `payment.model.ts` actualizado al nuevo contrato real.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- Backend pendiente de build por falta de Maven/Maven Wrapper en el entorno.

## Pendiente

- Ejecutar migraciones Flyway y validar runtime con Postgres.
- Probar pago real: cuenta con saldo suficiente -> pago aprobado -> saldo descontado -> pago listado con cuenta origen.

---

# Sesion 14 - Correccion de arranque Frontend en Windows

## Fecha

16/08/2026

## Problema

El frontend no levantaba cuando se seguia `login.md` porque indicaba ejecutar `ng serve` desde PowerShell. En este entorno `ng` se resuelve como `ng.ps1`, y PowerShell bloquea scripts `.ps1` por politica de ejecucion.

## Trabajo realizado

- `frontend/package.json`:
  - `start` ahora ejecuta `ng serve --host localhost --port 4200`.
  - se agrego `start:4202`.
- Se agrego `frontend/start-frontend.cmd` para arrancar el frontend sin depender de `ng.ps1`.
- Se reescribio `login.md` con comandos correctos:
  - `npm.cmd run start`,
  - `ng.cmd serve --host localhost --port 4200`,
  - o `start-frontend.cmd`.

## Verificacion

- `ng --version` falla por `ng.ps1` bloqueado.
- `ng.cmd --version` funciona.
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.
- Puerto 4200 escuchando en PID 27388.

---

# Sesion 15 - Cuentas por cliente y ayuda de montos

## Fecha

16/08/2026

## Objetivo

Permitir que cada cliente abra sus propias cuentas de ahorro/corriente con limite de una por tipo, y mejorar la captura de montos con separadores automaticos.

## Trabajo realizado

- Backend:
  - `POST /accounts` acepta `ROLE_CUSTOMER`.
  - `AccountRequest.customerId` deja de ser obligatorio en validacion DTO.
  - `AccountService.createAccount` resuelve el customer autenticado para `ROLE_CUSTOMER`.
  - `ROLE_CUSTOMER` no puede asignarse saldo inicial; el backend lo fuerza a `0`.
  - ADMIN/EMPLOYEE mantienen `customerId` obligatorio y saldo inicial para pruebas.
  - Se valida una sola cuenta no eliminada por tipo (`SAVINGS`/`CHECKING`) por cliente.
  - Migracion `V4__limit_one_account_per_type.sql` agrega indice unico parcial.

- Frontend:
  - `/accounts/new` permite ADMIN, EMPLOYEE y CUSTOMER.
  - `account-list` muestra `+ Nueva cuenta` tambien a CUSTOMER.
  - `account-form` oculta selector de cliente y saldo inicial cuando el usuario es CUSTOMER.
  - Nueva directiva `appMoneyInput` para mostrar comas automaticamente al escribir montos.
  - Se aplico a montos en cuentas, clientes, transferencias, prestamos y pagos.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.
- Backend pendiente de build por falta de Maven/Maven Wrapper en el entorno.

## Pendiente

- Ejecutar build backend con Maven disponible.
- Levantar backend y validar que Flyway aplique `V4__limit_one_account_per_type.sql`.
- Probar runtime: CUSTOMER crea ahorro, luego corriente, y recibe error al intentar una segunda de cualquiera de esos tipos.

---

# Sesion 16 - Integracion de Frankfurter para divisas

## Fecha

17/08/2026

## Objetivo

Integrar las APIs de Frankfurter (`api.frankfurter.dev`) para consultar tasas de cambio, monedas, proveedores y conversiones desde el sistema bancario.

## Trabajo realizado

- Backend:
  - Se agrego `app.exchange-rates.frankfurter-base-url` en `application.yml`.
  - Se crearon DTOs en `backend/src/main/java/com/bankingsystem/dto/exchange/`.
  - Se creo `ExchangeRateService` usando `RestClient` para consumir:
    - `/v2/rates`
    - `/v2/rate/{base}/{quote}`
    - `/v2/currencies`
    - `/v2/currency/{code}`
    - `/v2/providers`
  - Se agrego conversion local en backend multiplicando `amount * rate`.
  - Se creo `ExchangeRateController` bajo `/api/v1/exchange-rates`.

- Frontend:
  - Se creo `exchange-rate.model.ts`.
  - Se creo `CurrencyService`, consumiendo los endpoints internos del backend.
  - Se creo la pantalla `/exchange-rates` (`CurrencyDashboard`) con conversor y consulta de tasas.
  - Se agrego opcion `Divisas` al sidebar.
  - Se corrigio `account-list`, que ya importaba un `CurrencyService` inexistente, para convertir saldos a USD via backend.

- Documentacion:
  - Se agrego `docs/api/21_exchange_rates_api.md`.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- El primer intento de build fallo con `spawn EPERM`; al repetirlo con permisos elevados, compilo correctamente.
- Dev server Angular levantado en `http://localhost:4200/`; `http://localhost:4200/login` respondio HTTP 200.
- Backend pendiente de build por falta de Maven/Maven Wrapper en el entorno.

## Pendiente

- Ejecutar build backend con Maven disponible.
- Probar endpoints `/api/v1/exchange-rates/*` con JWT y backend levantado.
- Probar `/exchange-rates` en runtime contra backend real e internet disponible.

---

# Sesion 17 - Integracion de APIs Banco Popular

## Fecha

17/08/2026

## Objetivo

Integrar las APIs del Banco Popular Dominicano subidas por el usuario:

- BPDConfirmarCuenta 2.5.1.
- BPDUbicacionesATM 2.2.1.

## Trabajo realizado

- Backend:
  - Se agrego configuracion `app.bpd.*` en `application.yml`.
  - Se crearon DTOs en `backend/src/main/java/com/bankingsystem/dto/bpd/`.
  - Se creo `BpdApiService` con:
    - OAuth `client_credentials`.
    - Header `X-IBM-Client-Id`.
    - Cache temporal del access token.
    - Transformacion de request interno a `ConfirmarCuentaReq`.
    - Mapeo de campos crudos de ATM (`Dir_Fis`, `nombre`, `title`, `latitude`, `longitude`) a DTO interno.
  - Se creo `BpdController` bajo `/api/v1/bpd` con:
    - `POST /confirm-account`.
    - `GET /atm-locations?page=0`.

- Frontend:
  - Se creo `bpd.model.ts`.
  - Se creo `BpdService`.
  - Se creo pantalla `/bpd` con formulario de titularidad y listado paginado de ATMs.
  - Se agrego `Banco Popular` al sidebar.

- Documentacion:
  - Se agrego `docs/api/22_bpd_api.md`.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/bpd`: HTTP 200.
- Backend pendiente de build por falta de Maven/Maven Wrapper en el entorno.
- No se probo runtime contra BPD porque faltan credenciales reales `BPD_CLIENT_ID` y `BPD_CLIENT_SECRET`.

## Pendiente

- Configurar credenciales BPD reales.
- Ejecutar `mvn test` o `mvn clean install` cuando Maven este disponible.
- Probar confirmacion de cuenta y ubicaciones ATM contra IBM API Connect.

---

# Sesion 18 - Integracion Finnhub para mercados

## Fecha

17/08/2026

## Objetivo

Integrar la API de Finnhub subida por el usuario para mostrar informacion de mercado dentro del sistema bancario.

## Trabajo realizado

- Backend:
  - Se agrego configuracion `app.finnhub.*` en `application.yml`.
  - Se crearon DTOs en `backend/src/main/java/com/bankingsystem/dto/market/`.
  - Se creo `FinnhubMarketService`, usando `RestClient` y header `X-Finnhub-Token`.
  - Se creo `MarketController` bajo `/api/v1/markets`.
  - Endpoints internos:
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
  - Se creo `market.model.ts`.
  - Se creo `MarketService`.
  - Se creo pantalla `/markets` con:
    - Busqueda de simbolos.
    - Cotizacion actual.
    - Perfil de empresa.
    - Estado de mercado.
    - Feriados.
    - Noticias generales y por simbolo.
    - Indicadores basicos y tendencias de recomendacion.
  - Se agrego `Mercados` al sidebar.

- Documentacion:
  - Se agrego `docs/api/23_finnhub_market_api.md`.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/markets`: HTTP 200.
- Backend pendiente de build por falta de Maven/Maven Wrapper en el entorno.
- No se probo runtime contra Finnhub porque falta `FINNHUB_API_KEY`.

## Pendiente

- Configurar `FINNHUB_API_KEY`.
- Ejecutar backend build con Maven disponible.
- Probar endpoints `/api/v1/markets/*` con JWT valido.

---

# Sesion 19 - Correccion de build backend por RestClient.Builder

## Fecha

17/08/2026

## Objetivo

Corregir el fallo de arranque reportado al ejecutar `mvn spring-boot:run` en `backend`.

## Trabajo realizado

- Se identifico que Spring no tenia registrado un bean `RestClient.Builder`.
- Se agrego `RestClientConfig` en `backend/src/main/java/com/bankingsystem/config/`.
- El bean se declaro con scope `prototype` porque `RestClient.Builder` es mutable y lo usan varias integraciones externas.

## Verificacion

- No fue posible ejecutar Maven desde el entorno de Codex: no existe `mvn`, `mvn.cmd` ni `backend/mvnw.cmd`.
- El log del usuario muestra que Maven si esta disponible en su consola y que el fallo ocurria despues de compilar, durante el arranque del contexto Spring.

## Pendiente

- Reejecutar `mvn spring-boot:run` desde la terminal del usuario.
- Confirmar que el backend inicia sin el error `RestClient$Builder`.

---

# Sesion 20 - Correccion de login cargando indefinidamente

## Fecha

17/08/2026

## Objetivo

Resolver el problema reportado por el usuario: al iniciar sesion el frontend quedaba cargando y no entraba al dashboard.

## Trabajo realizado

- Se verifico que el backend responde correctamente:
  - `POST /api/v1/auth/login`
  - `GET /api/v1/auth/me`
  - `GET /api/v1/accounts`
- Se rehizo `AuthService` para evitar requests HTTP desde el constructor.
- El login ahora espera a que `/auth/me` cargue el perfil antes de navegar.
- Ajuste final tras persistir el problema: el login ya no espera `/auth/me`; decodifica el JWT y navega con el usuario minimo (`sub`, `email`, `role`) mientras `/auth/me` refresca en segundo plano.
- `authGuard` y `roleGuard` ahora restauran el perfil si hay token guardado antes de decidir.
- `authInterceptor` omite `Authorization` en endpoints publicos de auth.
- Se agrego timeout de 15 segundos a login/perfil para evitar cargas infinitas.

## Verificacion

- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.
- `http://localhost:4200/login`: HTTP 200.

## Pendiente

- Probar manualmente en navegador con `gabriel@test.com / Test1234!` y confirmar entrada al dashboard.
# Sesion 21 - Correccion de version Java para arranque backend

## Fecha

17/08/2026

## Objetivo

Resolver el error al ejecutar `mvn spring-boot:run` en backend desde una terminal con JDK 21.

## Trabajo realizado

- Se confirmo que el `java` disponible reporta JDK 21.
- Se confirmo que las clases existentes estaban compiladas como Java 25 (`class file version 69`).
- Se cambio `backend/pom.xml` para compilar a Java 21.
- Se agrego `-parameters` al `maven-compiler-plugin`.
- Se corrigio el segundo fallo de arranque: `FraudRabbitMQConfig` no podia elegir entre `fraudCheckQueue` y `auditQueue`.
- Se agregaron `@Qualifier` explicitos en los bindings de RabbitMQ de auditoria y fraude.
- Se actualizo `login.md` para usar `mvn clean spring-boot:run` y comandos separados para PowerShell/CMD.

## Verificacion

- Compilacion manual con `javac --release 21` contra dependencias Maven: OK.
- Arranque manual con `java -cp ... com.bankingsystem.BankingSystemApplication`: OK, llego a `Started BankingSystemApplication`.
- No se pudo ejecutar Maven desde el entorno Codex porque `mvn.cmd` no esta en PATH.

## Pendiente

- Ejecutar `mvn clean spring-boot:run` desde la terminal del usuario.

---

# Sesion 22 - Modulo Trading con wallet, posiciones y TradingView

## Fecha

17/08/2026

## Objetivo

Agregar un apartado `Trading` donde el cliente pueda ingresar dinero desde su cuenta bancaria, operar simbolos de mercado y retirar efectivo nuevamente a la cuenta.

## Trabajo realizado

- Se agrego `V5__add_trading.sql`.
- Se modelaron `TradingWallet`, `TradingPosition` y `TradingOrder`.
- Se agregaron repositorios, DTOs, `TradingService` y `TradingController`.
- El modulo valida usuario autenticado vinculado a `Customer` y propiedad de cuenta bancaria antes de debitar/acreditar.
- Se reutiliza `AccountService.debit/credit` para mover dinero real de la cuenta bancaria.
- Se usa Finnhub para precio de ejecucion cuando esta configurado; si no, se usa un precio demo deterministico.
- Se agrego `TradingService` de Angular, modelos, ruta `/trading`, link en sidebar y dashboard con widget TradingView.
- Se documento el contrato en `docs/api/24_trading_api.md`.

## Verificacion

- Backend compila manualmente con `javac --release 21`.
- Frontend compila con `npm.cmd run build` sin warnings.
- Arranque backend de prueba en puerto aleatorio:
  - Flyway valido 5 migraciones.
  - Flyway aplico V5.
  - Spring llego a `Started BankingSystemApplication`.

## Pendiente

- Reiniciar el backend real que esta escuchando en `localhost:8080`.
- Probar el flujo completo en `/trading` con un usuario CUSTOMER vinculado a Customer y una cuenta bancaria activa.

---

# Sesion 23 - Transferencias por cuenta propia y a otros usuarios

## Fecha

17/08/2026

## Objetivo

Activar la transferencia a otros usuarios y ajustar el flujo para que usuarios registrados puedan transferir desde cualquiera de sus cuentas activas, con limite de transacciones.

## Trabajo realizado

- `TransferRequest` acepta destino por UUID (`destinationAccount`) o por numero de cuenta (`destinationAccountNumber`).
- `TransferService` resuelve el destino por numero de cuenta para otros usuarios registrados.
- Se valida que origen y destino esten activos y no eliminados.
- Usuarios `CUSTOMER` solo pueden transferir desde cuentas propias.
- Se agrego limite de 10 transferencias por cuenta origen en las ultimas 24 horas, configurable con `TRANSFER_DAILY_LIMIT`.
- `TransferResponse` incluye numeros de cuenta origen/destino.
- La pantalla `/transfers/new` permite seleccionar cuenta propia o escribir numero de cuenta de otro usuario.
- El historial muestra numeros de cuenta cuando el backend los entrega.
- `docs/api/15_transfers_api.md` se actualizo con el contrato real.

## Verificacion

- Backend compila manualmente con `javac --release 21`.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Reiniciar el backend real en `localhost:8080`.
- Probar una transferencia a cuenta propia y una a otro usuario desde el navegador.

---

# Sesion 24 - Regla permanente de documentacion continua

## Fecha

17/08/2026

## Objetivo

Registrar como regla del proyecto que todos los progresos deben guardarse en docs.

## Trabajo realizado

- Se actualizo `PROJECT_CONTEXT.md` con una regla permanente de documentacion.
- La regla indica que cada avance debe quedar en `docs/` antes de cerrar la sesion.
- Se dejo explicito que deben actualizarse `AI_HANDOFF.md`, `docs/management/tareas.md`, `docs/management/bitacora.md`, documentos de `docs/api/` y decisiones tecnicas cuando aplique.

## Verificacion

- Cambio documental aplicado.

## Pendiente

- Aplicar esta regla en cada implementacion futura.

---
