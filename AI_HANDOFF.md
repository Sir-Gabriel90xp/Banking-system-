# AI HANDOFF

## Proyecto

Banking System

---

# Estado del proyecto

El proyecto terminó la fase de análisis y documentación (requisitos, casos de uso, arquitectura, modelo de datos, entidades detalladas, diseño de API y diseño final de endpoints — todos completos).

Se completó la configuración de control de versiones (Git + repositorio remoto en GitHub) y se creó el esqueleto del proyecto Backend en Spring Boot, el cual compila y empaqueta correctamente (`mvn clean install` → BUILD SUCCESS). Todavía no existe código de negocio (entidades JPA, services, controllers).

Se preparó y verificó el entorno de infraestructura vía Docker: **PostgreSQL y RabbitMQ están levantados y operativos** (`docker compose up -d postgres rabbitmq`, ambos en estado `healthy`). Se creó `docker/docker-compose.yml`, `.env`, los perfiles de Spring Boot (`application.yml`, `application-dev.yml`, `application-docker.yml`) y el `Dockerfile` del backend.

Se implementó y ejecutó con éxito la migración inicial de base de datos `V1__init_schema.sql` (Flyway), creando las 10 tablas principales del modelo (`role`, `app_user`, `customer`, `account`, `transaction`, `transfer`, `loan`, `payment`, `audit_log`, `fraud_alert`) con sus relaciones, constraints de reglas de negocio (RN-01, RN-02, RN-03) e índices. Se insertó el seed de los 3 roles base (ADMIN, EMPLOYEE, CUSTOMER). Verificado con `\dt` y consulta directa sobre `role`.

**La base de datos ya está implementada, no solo diseñada.**

Se crearon e implementaron las **10 entidades JPA** (`Role`, `AppUser`, `Customer`, `Account`, `Transaction`, `Transfer`, `Loan`, `Payment`, `AuditLog`, `FraudAlert`) que mapean exactamente el esquema ya existente en PostgreSQL, junto con dos clases base reutilizables en `entities/base/` (`Auditable`, `SoftDeletableEntity`) y 6 enums de apoyo en `entities/enums/` (`AccountStatus`, `AccountType`, `CustomerStatus`, `FraudSeverity`, `LoanStatus`, `TransactionType`). Verificado con `mvn clean install` → BUILD SUCCESS.

**Las entidades JPA ya están implementadas, no solo la base de datos.** Todavía no existen DTOs, Mappers, Services ni Controllers.

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

## Infraestructura (nuevo)

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

Esqueleto del proyecto Spring Boot creado y compilando correctamente (Java 25 + Spring Boot 4.1.0, ver ADR-006 en decisiones.md). Perfiles `dev`/`docker` configurados. **Las 10 entidades JPA ya están implementadas** (`Role`, `AppUser`, `Customer`, `Account`, `Transaction`, `Transfer`, `Loan`, `Payment`, `AuditLog`, `FraudAlert`), junto con las clases base `Auditable`/`SoftDeletableEntity` y los enums de dominio cerrado. Compilación verificada (`mvn clean install` → BUILD SUCCESS). Todavía sin DTOs, sin Mappers, sin services, sin controllers.

Frontend:

No creado.

Base de datos:

**Implementada.** PostgreSQL corriendo en Docker (`banking-postgres`, healthy). Esquema `V1__init_schema.sql` ejecutado con éxito: 10 tablas + seed de roles verificados.

Mensajería:

RabbitMQ corriendo en Docker (`banking-rabbitmq`, healthy). Panel de administración verificado en `http://localhost:15672`. Sin consumidores ni productores implementados todavía (pendiente de Fase 4).

Docker:

`docker-compose.yml` con 4 servicios (`postgres`, `rabbitmq`, `backend`, `frontend`). Los dos primeros verificados y operativos. `backend`/`frontend` tienen `Dockerfile` listo pero no se han construido aún (no hay código de negocio que compilar en imagen).

Git:

Completado (repositorio local + remoto en GitHub, `.gitignore`, primer commit, ramas `main` y `develop`).

---

# Próxima fase

Crear DTOs y Mappers, y avanzar hacia seguridad JWT.

Orden obligatorio:

1. Crear DTOs y Mappers correspondientes a las 10 entidades JPA ya implementadas (nunca exponer entidades directamente, RNF-03/ADR-004), comenzando por `Role` y `AppUser` (mismo orden usado para las entidades). Mapper elegido: **MapStruct** (ya está en el `pom.xml` junto con `lombok-mapstruct-binding`, ver ADR-006).
2. Implementar seguridad JWT (Spring Security).
3. Implementar controllers y services de los módulos (según `11_endpoints.md`), comenzando por Auth, Users y Customers.

Ya completados (no repetir): inicialización de repositorio Git, esqueleto del proyecto Backend Spring Boot, entidades detalladas (`08_entities.md`), diseño final de endpoints (`11_endpoints.md`), Docker Compose con PostgreSQL y RabbitMQ operativos, esquema de base de datos implementado (`V1__init_schema.sql`), **10 entidades JPA implementadas y compilando** (`Role`, `AppUser`, `Customer`, `Account`, `Transaction`, `Transfer`, `Loan`, `Payment`, `AuditLog`, `FraudAlert`, más clases base y enums).

---

# Reglas para continuar

Antes de crear código:

Leer:

- PROJECT_CONTEXT.md
- README.md
- docs/architecture/03_architecture.md
- docs/management/tareas.md

No cambiar arquitectura sin registrar una decisión técnica.

Toda funcionalidad terminada debe actualizar:

- bitacora.md
- tareas.md
- AI_HANDOFF.md

Nota: la tabla de usuarios se implementó como `app_user` (no `user`) por ser palabra reservada en PostgreSQL. Respetar este nombre al mapear la entidad JPA (`@Table(name = "app_user")`).

---

# Rol de la IA

La IA debe actuar como asistente de desarrollo.

Debe:

- Explicar decisiones.
- Seguir la arquitectura definida.
- Mantener documentación actualizada.
- Evitar soluciones rápidas que comprometan escalabilidad.

---

# Punto exacto donde continuar

Crear los DTOs y Mappers correspondientes a las 10 entidades JPA ya implementadas (`Role`, `AppUser`, `Customer`, `Account`, `Transaction`, `Transfer`, `Loan`, `Payment`, `AuditLog`, `FraudAlert`), respetando ADR-004 (nunca exponer entidades directamente), usando MapStruct. Empezar por `Role` y `AppUser`, siguiendo el mismo orden usado para las entidades. Después: seguridad JWT (Fase 2 de `tareas.md`).

---

# Nota de sincronización

Este archivo estaba desfasado respecto a `bitacora.md` y `tareas.md` (v1.2): ambos ya reflejaban las 10 entidades JPA como completadas (Sesión 5, 18/07/2026), pero este documento seguía indicando "sin entidades JPA" como pendiente. Se corrigió en esta actualización (18/07/2026) para que los tres documentos de gestión queden sincronizados, según lo exigido en `PROJECT_CONTEXT.md` (sección 12, Gestión de Documentación).