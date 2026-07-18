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






















