# Actualizacion 17/08/2026 - Sesion 21

## Completado

- Backend configurado para compilar a Java 21.
- `maven-compiler-plugin` configurado con `-parameters`.
- RabbitMQ bindings corregidos con `@Qualifier` para colas/exchanges de auditoria y fraude.
- `login.md` actualizado con comandos PowerShell/CMD.
- Comando recomendado de backend cambiado a `mvn clean spring-boot:run` para limpiar clases antiguas Java 25.
- Compilacion manual `javac --release 21`: OK.
- Arranque manual con `java -cp ...`: OK, `Started BankingSystemApplication`.

## Pendiente

- Ejecutar `mvn clean spring-boot:run` con Maven disponible en la terminal del usuario para confirmar el flujo Maven.

---

# Tareas

## Pendientes
- Completar requisitos detallados.
- Definir modelo de datos final.
- Preparar diseño de API.



# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 1 - Preparación del entorno

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto

✅ Crear README

✅ Definir requisitos

✅ Definir casos de uso

✅ Diseñar arquitectura

✅ Diseñar modelo de datos

✅ Diseñar API

✅ Crear roadmap

✅ Crear convenciones

✅ Crear decisiones técnicas

✅ Crear bitácora

---

# Fase 1 - Configuración Inicial

Estado:

🟡 Próxima fase

---

## Control de Versiones

⬜ Inicializar repositorio Git

⬜ Crear repositorio remoto en GitHub

⬜ Crear archivo .gitignore

⬜ Realizar primer commit

⬜ Configurar ramas principales

## Listo el control de verisones ##


---

## Backend Spring Boot

⬜ Crear proyecto Spring Boot

⬜ Configurar Maven

⬜ Configurar estructura de paquetes

⬜ Configurar variables de entorno

⬜ Configurar perfiles de aplicación

⬜ Configurar conexión PostgreSQL

---

## Base de Datos

⬜ Instalar PostgreSQL

⬜ Crear base de datos inicial

⬜ Configurar usuario y permisos

⬜ Preparar migraciones con Flyway

---

## Docker

⬜ Crear Docker Compose

⬜ Configurar PostgreSQL en Docker

⬜ Configurar RabbitMQ en Docker

⬜ Configurar Backend en Docker

---

# Fase 2 - Seguridad

⬜ Implementar Spring Security

⬜ Crear entidad User

⬜ Crear entidad Role

⬜ Implementar registro

⬜ Implementar Login

⬜ Implementar JWT

⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

⬜ Crear Customer Entity

⬜ Crear Customer Repository

⬜ Crear Customer Service

⬜ Crear Customer Controller


## Cuentas

⬜ Crear Account Entity

⬜ Crear gestión de balances

⬜ Crear consultas de cuentas


## Transferencias

⬜ Crear Transfer Entity

⬜ Implementar transferencia bancaria

⬜ Implementar validación de saldo

⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

⬜ Préstamos

⬜ Pagos

⬜ Auditoría

⬜ Logs

⬜ RabbitMQ Events

⬜ Detección de fraude

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular

⬜ Configurar estructura

⬜ Crear autenticación

⬜ Crear dashboard

⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias

⬜ Pruebas integración

⬜ Documentación Swagger completa

⬜ Dockerización final

⬜ Deploy

---

# Próxima tarea exacta

Crear el proyecto Backend Spring Boot y preparar la estructura inicial siguiendo la arquitectura definida.



# Tareas

## Pendientes
- Completar requisitos detallados. ✅ (ver docs/requirements/01_requirements.md)
- Definir modelo de datos final.
- Preparar diseño de API.

---

# Tareas Detalladas

## Documentación

- ✅ PROJECT_CONTEXT.md
- ✅ README.md
- ✅ AI_HANDOFF.md
- ✅ Arquitectura (02_architecture.md)
- ✅ Modelo de datos (03_database_model.md)
- ✅ Diseño de API inicial (04_api_design.md)
- ✅ Documento de requisitos (01_requirements.md)
- ✅ Casos de uso (02_use_cases.md)
- ⬜ Entidades detalladas (08_entities.md)
- ⬜ Diseño final de endpoints (11_endpoints.md)

## Control de Versiones

- ✅ Inicializar repositorio Git
- ✅ Crear repositorio remoto en GitHub
- ✅ Crear archivo .gitignore
- ✅ Realizar primer commit
- ✅ Configurar ramas principales (main + develop)

## Próximo paso

- ⬜ Completar entidades detalladas.
- ⬜ Completar diseño final de endpoints.
- ⬜ Preparación del entorno (Spring Boot, Maven, PostgreSQL, Docker).











# Tareas

## Pendientes
- Completar requisitos detallados. ✅ (ver docs/requirements/01_requirements.md)
- Definir modelo de datos final.
- Preparar diseño de API.

---

# Tareas Detalladas

## Documentación

- ✅ PROJECT_CONTEXT.md
- ✅ README.md
- ✅ AI_HANDOFF.md
- ✅ Arquitectura (02_architecture.md)
- ✅ Modelo de datos (03_database_model.md)
- ✅ Diseño de API inicial (04_api_design.md)
- ✅ Documento de requisitos (01_requirements.md)
- ✅ Casos de uso (02_use_cases.md)
- ⬜ Entidades detalladas (08_entities.md)
- ⬜ Diseño final de endpoints (11_endpoints.md)

## Control de Versiones

- ✅ Inicializar repositorio Git
- ✅ Crear repositorio remoto en GitHub
- ✅ Crear archivo .gitignore
- ✅ Realizar primer commit
- ✅ Configurar ramas principales (main + develop)

## Backend - Spring Boot

- ✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
- ✅ Configurar Maven (verificar build limpio end-to-end — ya validado con `mvn clean install` = BUILD SUCCESS)
- ⬜ Configurar PostgreSQL
- ⬜ Configurar Docker
- ⬜ Crear estructura inicial del Backend (paquetes ya creados, pendiente código real)

## Próximo paso

- ⬜ Completar entidades detalladas.
- ⬜ Completar diseño final de endpoints.
- ⬜ Configurar PostgreSQL y Docker.















# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 1 - Configuración Inicial (en progreso)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

🟡 En progreso

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
⬜ Configurar estructura de paquetes con código real (paquetes creados, vacíos)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, pendiente build real con código de negocio)

---

# Fase 2 - Seguridad

⬜ Implementar Spring Security
⬜ Crear entidad User (JPA, sobre tabla `app_user` ya existente en BD)
⬜ Crear entidad Role (JPA, sobre tabla `role` ya existente en BD)
⬜ Implementar registro
⬜ Implementar Login
⬜ Implementar JWT
⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

⬜ Crear Customer Entity
⬜ Crear Customer Repository
⬜ Crear Customer Service
⬜ Crear Customer Controller

## Cuentas

⬜ Crear Account Entity
⬜ Crear gestión de balances
⬜ Crear consultas de cuentas

## Transferencias

⬜ Crear Transfer Entity
⬜ Implementar transferencia bancaria
⬜ Implementar validación de saldo
⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

⬜ Préstamos
⬜ Pagos
⬜ Auditoría
⬜ Logs
⬜ RabbitMQ Events
⬜ Detección de fraude

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

Crear las entidades JPA (`@Entity`) que mapeen el esquema ya implementado en PostgreSQL (`V1__init_schema.sql`), comenzando por `Role` y `AppUser`, siguiendo la arquitectura por capas definida en `02_architecture.md`.

---

Versión: 1.1
Estado: En progreso
Última actualización: 2026-07-18

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |















# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 2 - Seguridad (en progreso, Spring Security implementado, falta registro/login/JWT)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

✅ Completada

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
✅ Configurar estructura de paquetes con código real (entidades JPA ya implementadas)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, sigue pendiente el build real de imagen — tiene sentido retomarlo cuando haya al menos Services/Controllers, no solo entidades)

---

# Fase 2 - Seguridad

Estado:

🟡 En progreso

✅ Crear entidad AppUser (JPA, sobre tabla `app_user` ya existente en BD)
✅ Crear entidad Role (JPA, sobre tabla `role` ya existente en BD)
✅ Implementar Spring Security (`SecurityConfig`, `CustomUserDetailsService`, `AppUserPrincipal`, `AppUserRepository`)
⬜ Implementar registro
⬜ Implementar Login
⬜ Implementar JWT
⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

✅ Crear Customer Entity (JPA)
⬜ Crear Customer Repository
⬜ Crear Customer Service
⬜ Crear Customer Controller

## Cuentas

✅ Crear Account Entity (JPA)
⬜ Crear gestión de balances
⬜ Crear consultas de cuentas

## Transferencias

✅ Crear Transfer Entity (JPA)
⬜ Implementar transferencia bancaria
⬜ Implementar validación de saldo
⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

✅ Crear Loan Entity (JPA)
✅ Crear Payment Entity (JPA)
✅ Crear AuditLog Entity (JPA)
✅ Crear FraudAlert Entity (JPA)
⬜ Préstamos (lógica de negocio: Service/Controller)
⬜ Pagos (lógica de negocio: Service/Controller)
⬜ Auditoría (lógica de negocio: Service/Controller + consumidor RabbitMQ)
⬜ Logs
⬜ RabbitMQ Events
⬜ Detección de fraude (lógica de negocio: Service/Controller + consumidor RabbitMQ)

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

Implementar el flujo de registro y login: crear los DTOs mínimos de Auth (`RegisterRequest`, `LoginRequest`, `AuthResponse`), el `JwtService` (generación/validación de access y refresh token, ya hay configuración lista en `application.yml`: `jwt.secret`, `jwt.expiration-ms`, `jwt.refresh-expiration-ms`), y el `AuthController`/`AuthService` con los endpoints `/api/v1/auth/register` y `/api/v1/auth/login`, según `11_endpoints.md`.

---

Versión: 1.3
Estado: En progreso
Última actualización: 2026-07-18

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |
| 1.2 | 18/07/2026 | Las 10 entidades JPA completadas y compilando (`BUILD SUCCESS`). Próxima tarea: DTOs y Mappers |
| 1.3 | 18/07/2026 | ADR-007: se adelantó Spring Security (config, UserDetailsService, PasswordEncoder, filter chain) antes que DTOs/Mappers del resto de entidades. Completado y compilando. Próxima tarea: registro, login y JWT |














# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 2 - Seguridad (código completo, compilando; pendiente verificación en runtime antes de pasar a Fase 3)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

✅ Completada

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
✅ Configurar estructura de paquetes con código real (entidades JPA + Auth ya implementados)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, pendiente build real de imagen — retomar cuando haya al menos un módulo de negocio completo, ej. Customers)

---

# Fase 2 - Seguridad

Estado:

✅ Código completo, compilando (`BUILD SUCCESS`) — ⬜ pendiente verificación en runtime

✅ Crear entidad AppUser (JPA)
✅ Crear entidad Role (JPA)
✅ Implementar Spring Security (`SecurityConfig`, `CustomUserDetailsService`, `AppUserPrincipal`, `AppUserRepository`)
✅ Implementar registro (`AuthController`/`AuthService.register`, `RoleRepository` nuevo)
✅ Implementar Login (`AuthService.login`, `AuthenticationManager`)
✅ Implementar JWT (`JwtService`, access + refresh token, `jjwt` 0.12.6)
✅ Proteger endpoints (`JwtAuthenticationFilter` insertado en `SecurityConfig`)
⬜ Verificar en runtime: `POST /register`, `POST /login`, `GET /me` (con token), `POST /refresh` (con `mvn spring-boot:run` + PostgreSQL/RabbitMQ en Docker)
⬜ Commit/push del módulo Auth a GitHub

---

# Fase 3 - Módulos principales

## Clientes

✅ Crear Customer Entity (JPA)
✅ Crear Customer Repository
✅ Crear Customer Service
✅ Crear Customer Controller

## Cuentas

✅ Crear Account Entity (JPA)
✅ Crear gestión de balances
✅ Crear consultas de cuentas

## Transferencias

✅ Crear Transfer Entity (JPA)
✅ Implementar transferencia bancaria
✅ Implementar validación de saldo
✅ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

✅ Crear Loan Entity (JPA)
✅ Crear Payment Entity (JPA)
✅ Crear AuditLog Entity (JPA)
✅ Crear FraudAlert Entity (JPA)
✅ Préstamos (lógica de negocio: Service/Controller)
✅ Pagos (lógica de negocio: Service/Controller)
⬜ Auditoría (lógica de negocio: Service/Controller + consumidor RabbitMQ)
⬜ Logs
⬜ RabbitMQ Events
⬜ Detección de fraude (lógica de negocio: Service/Controller + consumidor RabbitMQ)

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

1. Verificar en runtime el flujo completo de Auth (`register` → `login` → `GET /me` con el token → `refresh`).
2. Si todo funciona: commit/push a GitHub y cerrar formalmente la Fase 2.
3. Iniciar Fase 3: DTOs/Mappers (MapStruct) de `Customer`, luego `CustomerRepository`/`CustomerService`/`CustomerController`, según `11_endpoints.md`.

---

Versión: 1.4
Estado: En progreso
Última actualización: 18/07/2026

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |
| 1.2 | 18/07/2026 | Las 10 entidades JPA completadas y compilando (`BUILD SUCCESS`). Próxima tarea: DTOs y Mappers |
| 1.3 | 18/07/2026 | ADR-007: se adelantó Spring Security antes que DTOs/Mappers. Completado y compilando. Próxima tarea: registro, login y JWT |
| 1.4 | 18/07/2026 | Registro, Login, JWT y protección de endpoints implementados y compilando (`BUILD SUCCESS`). Se resolvieron 3 incidencias (paquetes singular/plural, `AuthController` mal ubicado/renombrado por error, `security/jwt/` que nunca se había creado). Pendiente: verificación en runtime y push a GitHub |












# Tareas

## Pendientes
- Completar requisitos detallados.
- Definir modelo de datos final.
- Preparar diseño de API.



# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 1 - Preparación del entorno

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto

✅ Crear README

✅ Definir requisitos

✅ Definir casos de uso

✅ Diseñar arquitectura

✅ Diseñar modelo de datos

✅ Diseñar API

✅ Crear roadmap

✅ Crear convenciones

✅ Crear decisiones técnicas

✅ Crear bitácora

---

# Fase 1 - Configuración Inicial

Estado:

🟡 Próxima fase

---

## Control de Versiones

⬜ Inicializar repositorio Git

⬜ Crear repositorio remoto en GitHub

⬜ Crear archivo .gitignore

⬜ Realizar primer commit

⬜ Configurar ramas principales

## Listo el control de verisones ##


---

## Backend Spring Boot

⬜ Crear proyecto Spring Boot

⬜ Configurar Maven

⬜ Configurar estructura de paquetes

⬜ Configurar variables de entorno

⬜ Configurar perfiles de aplicación

⬜ Configurar conexión PostgreSQL

---

## Base de Datos

⬜ Instalar PostgreSQL

⬜ Crear base de datos inicial

⬜ Configurar usuario y permisos

⬜ Preparar migraciones con Flyway

---

## Docker

⬜ Crear Docker Compose

⬜ Configurar PostgreSQL en Docker

⬜ Configurar RabbitMQ en Docker

⬜ Configurar Backend en Docker

---

# Fase 2 - Seguridad

⬜ Implementar Spring Security

⬜ Crear entidad User

⬜ Crear entidad Role

⬜ Implementar registro

⬜ Implementar Login

⬜ Implementar JWT

⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

⬜ Crear Customer Entity

⬜ Crear Customer Repository

⬜ Crear Customer Service

⬜ Crear Customer Controller


## Cuentas

⬜ Crear Account Entity

⬜ Crear gestión de balances

⬜ Crear consultas de cuentas


## Transferencias

⬜ Crear Transfer Entity

⬜ Implementar transferencia bancaria

⬜ Implementar validación de saldo

⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

⬜ Préstamos

⬜ Pagos

⬜ Auditoría

⬜ Logs

⬜ RabbitMQ Events

⬜ Detección de fraude

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular

⬜ Configurar estructura

⬜ Crear autenticación

⬜ Crear dashboard

⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias

⬜ Pruebas integración

⬜ Documentación Swagger completa

⬜ Dockerización final

⬜ Deploy

---

# Próxima tarea exacta

Crear el proyecto Backend Spring Boot y preparar la estructura inicial siguiendo la arquitectura definida.



# Tareas

## Pendientes
- Completar requisitos detallados. ✅ (ver docs/requirements/01_requirements.md)
- Definir modelo de datos final.
- Preparar diseño de API.

---

# Tareas Detalladas

## Documentación

- ✅ PROJECT_CONTEXT.md
- ✅ README.md
- ✅ AI_HANDOFF.md
- ✅ Arquitectura (02_architecture.md)
- ✅ Modelo de datos (03_database_model.md)
- ✅ Diseño de API inicial (04_api_design.md)
- ✅ Documento de requisitos (01_requirements.md)
- ✅ Casos de uso (02_use_cases.md)
- ⬜ Entidades detalladas (08_entities.md)
- ⬜ Diseño final de endpoints (11_endpoints.md)

## Control de Versiones

- ✅ Inicializar repositorio Git
- ✅ Crear repositorio remoto en GitHub
- ✅ Crear archivo .gitignore
- ✅ Realizar primer commit
- ✅ Configurar ramas principales (main + develop)

## Próximo paso

- ⬜ Completar entidades detalladas.
- ⬜ Completar diseño final de endpoints.
- ⬜ Preparación del entorno (Spring Boot, Maven, PostgreSQL, Docker).











# Tareas

## Pendientes
- Completar requisitos detallados. ✅ (ver docs/requirements/01_requirements.md)
- Definir modelo de datos final.
- Preparar diseño de API.

---

# Tareas Detalladas

## Documentación

- ✅ PROJECT_CONTEXT.md
- ✅ README.md
- ✅ AI_HANDOFF.md
- ✅ Arquitectura (02_architecture.md)
- ✅ Modelo de datos (03_database_model.md)
- ✅ Diseño de API inicial (04_api_design.md)
- ✅ Documento de requisitos (01_requirements.md)
- ✅ Casos de uso (02_use_cases.md)
- ⬜ Entidades detalladas (08_entities.md)
- ⬜ Diseño final de endpoints (11_endpoints.md)

## Control de Versiones

- ✅ Inicializar repositorio Git
- ✅ Crear repositorio remoto en GitHub
- ✅ Crear archivo .gitignore
- ✅ Realizar primer commit
- ✅ Configurar ramas principales (main + develop)

## Backend - Spring Boot

- ✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
- ✅ Configurar Maven (verificar build limpio end-to-end — ya validado con `mvn clean install` = BUILD SUCCESS)
- ⬜ Configurar PostgreSQL
- ⬜ Configurar Docker
- ⬜ Crear estructura inicial del Backend (paquetes ya creados, pendiente código real)

## Próximo paso

- ⬜ Completar entidades detalladas.
- ⬜ Completar diseño final de endpoints.
- ⬜ Configurar PostgreSQL y Docker.















# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 1 - Configuración Inicial (en progreso)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

🟡 En progreso

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
⬜ Configurar estructura de paquetes con código real (paquetes creados, vacíos)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, pendiente build real con código de negocio)

---

# Fase 2 - Seguridad

⬜ Implementar Spring Security
⬜ Crear entidad User (JPA, sobre tabla `app_user` ya existente en BD)
⬜ Crear entidad Role (JPA, sobre tabla `role` ya existente en BD)
⬜ Implementar registro
⬜ Implementar Login
⬜ Implementar JWT
⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

⬜ Crear Customer Entity
⬜ Crear Customer Repository
⬜ Crear Customer Service
⬜ Crear Customer Controller

## Cuentas

⬜ Crear Account Entity
⬜ Crear gestión de balances
⬜ Crear consultas de cuentas

## Transferencias

⬜ Crear Transfer Entity
⬜ Implementar transferencia bancaria
⬜ Implementar validación de saldo
⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

⬜ Préstamos
⬜ Pagos
⬜ Auditoría
⬜ Logs
⬜ RabbitMQ Events
⬜ Detección de fraude

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

Crear las entidades JPA (`@Entity`) que mapeen el esquema ya implementado en PostgreSQL (`V1__init_schema.sql`), comenzando por `Role` y `AppUser`, siguiendo la arquitectura por capas definida en `02_architecture.md`.

---

Versión: 1.1
Estado: En progreso
Última actualización: 2026-07-18

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |















# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 2 - Seguridad (en progreso, Spring Security implementado, falta registro/login/JWT)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

✅ Completada

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
✅ Configurar estructura de paquetes con código real (entidades JPA ya implementadas)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, sigue pendiente el build real de imagen — tiene sentido retomarlo cuando haya al menos Services/Controllers, no solo entidades)

---

# Fase 2 - Seguridad

Estado:

🟡 En progreso

✅ Crear entidad AppUser (JPA, sobre tabla `app_user` ya existente en BD)
✅ Crear entidad Role (JPA, sobre tabla `role` ya existente en BD)
✅ Implementar Spring Security (`SecurityConfig`, `CustomUserDetailsService`, `AppUserPrincipal`, `AppUserRepository`)
⬜ Implementar registro
⬜ Implementar Login
⬜ Implementar JWT
⬜ Proteger endpoints

---

# Fase 3 - Módulos principales

## Clientes

✅ Crear Customer Entity (JPA)
⬜ Crear Customer Repository
⬜ Crear Customer Service
⬜ Crear Customer Controller

## Cuentas

✅ Crear Account Entity (JPA)
⬜ Crear gestión de balances
⬜ Crear consultas de cuentas

## Transferencias

✅ Crear Transfer Entity (JPA)
⬜ Implementar transferencia bancaria
⬜ Implementar validación de saldo
⬜ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

✅ Crear Loan Entity (JPA)
✅ Crear Payment Entity (JPA)
✅ Crear AuditLog Entity (JPA)
✅ Crear FraudAlert Entity (JPA)
⬜ Préstamos (lógica de negocio: Service/Controller)
⬜ Pagos (lógica de negocio: Service/Controller)
⬜ Auditoría (lógica de negocio: Service/Controller + consumidor RabbitMQ)
⬜ Logs
⬜ RabbitMQ Events
⬜ Detección de fraude (lógica de negocio: Service/Controller + consumidor RabbitMQ)

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

Implementar el flujo de registro y login: crear los DTOs mínimos de Auth (`RegisterRequest`, `LoginRequest`, `AuthResponse`), el `JwtService` (generación/validación de access y refresh token, ya hay configuración lista en `application.yml`: `jwt.secret`, `jwt.expiration-ms`, `jwt.refresh-expiration-ms`), y el `AuthController`/`AuthService` con los endpoints `/api/v1/auth/register` y `/api/v1/auth/login`, según `11_endpoints.md`.

---

Versión: 1.3
Estado: En progreso
Última actualización: 2026-07-18

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |
| 1.2 | 18/07/2026 | Las 10 entidades JPA completadas y compilando (`BUILD SUCCESS`). Próxima tarea: DTOs y Mappers |
| 1.3 | 18/07/2026 | ADR-007: se adelantó Spring Security (config, UserDetailsService, PasswordEncoder, filter chain) antes que DTOs/Mappers del resto de entidades. Completado y compilando. Próxima tarea: registro, login y JWT |














# Tareas

## Pendientes
- Definir modelo de datos final. ✅
- Preparar diseño de API. ✅

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 2 - Seguridad (código completo, compilando; pendiente verificación en runtime antes de pasar a Fase 3)

---

# Fase 0 - Documentación

Estado:

✅ Completada

Tareas:

✅ Crear contexto del proyecto
✅ Crear README
✅ Definir requisitos
✅ Definir casos de uso
✅ Diseñar arquitectura
✅ Diseñar modelo de datos
✅ Diseñar API
✅ Crear roadmap
✅ Crear convenciones
✅ Crear decisiones técnicas
✅ Crear bitácora
✅ Entidades detalladas (`08_entities.md`)
✅ Diseño final de endpoints (`11_endpoints.md`)

---

# Fase 1 - Configuración Inicial

Estado:

✅ Completada

---

## Control de Versiones

✅ Inicializar repositorio Git
✅ Crear repositorio remoto en GitHub
✅ Crear archivo .gitignore
✅ Realizar primer commit
✅ Configurar ramas principales (main + develop)

---

## Backend Spring Boot

✅ Crear proyecto Spring Boot (Java 25 + Spring Boot 4.1.0, ver ADR-006)
✅ Configurar Maven (`mvn clean install` → BUILD SUCCESS)
✅ Configurar estructura de paquetes con código real (entidades JPA + Auth ya implementados)
✅ Configurar variables de entorno (`application.yml`, `application-dev.yml`, `application-docker.yml`)
✅ Configurar perfiles de aplicación (`dev`, `docker`)
✅ Configurar conexión PostgreSQL (perfiles listos, healthcheck OK)

---

## Base de Datos

✅ Instalar PostgreSQL (vía Docker, contenedor `banking-postgres`, healthy)
✅ Crear base de datos inicial (`banking_system`)
✅ Configurar usuario y permisos (`banking_user`)
✅ Preparar migraciones con Flyway
✅ Ejecutar migración inicial `V1__init_schema.sql` (10 tablas creadas + seed de roles)

---

## Docker

✅ Crear Docker Compose (`docker/docker-compose.yml`)
✅ Configurar PostgreSQL en Docker (healthy)
✅ Configurar RabbitMQ en Docker (healthy, verificado en `http://localhost:15672`)
⬜ Configurar Backend en Docker (Dockerfile listo, pendiente build real de imagen — retomar cuando haya al menos un módulo de negocio completo, ej. Customers)

---

# Fase 2 - Seguridad

Estado:

✅ Código completo, compilando (`BUILD SUCCESS`) — ⬜ pendiente verificación en runtime

✅ Crear entidad AppUser (JPA)
✅ Crear entidad Role (JPA)
✅ Implementar Spring Security (`SecurityConfig`, `CustomUserDetailsService`, `AppUserPrincipal`, `AppUserRepository`)
✅ Implementar registro (`AuthController`/`AuthService.register`, `RoleRepository` nuevo)
✅ Implementar Login (`AuthService.login`, `AuthenticationManager`)
✅ Implementar JWT (`JwtService`, access + refresh token, `jjwt` 0.12.6)
✅ Proteger endpoints (`JwtAuthenticationFilter` insertado en `SecurityConfig`)
⬜ Verificar en runtime: `POST /register`, `POST /login`, `GET /me` (con token), `POST /refresh` (con `mvn spring-boot:run` + PostgreSQL/RabbitMQ en Docker)
⬜ Commit/push del módulo Auth a GitHub

---

# Fase 3 - Módulos principales

## Clientes

✅ Crear Customer Entity (JPA)
✅ Crear Customer Repository
✅ Crear Customer Service
✅ Crear Customer Controller

## Cuentas

✅ Crear Account Entity (JPA)
✅ Crear gestión de balances
✅ Crear consultas de cuentas

## Transferencias

✅ Crear Transfer Entity (JPA)
✅ Implementar transferencia bancaria
✅ Implementar validación de saldo
✅ Crear historial

---

# Fase 4 - Funcionalidades avanzadas

✅ Crear Loan Entity (JPA)
✅ Crear Payment Entity (JPA)
✅ Crear AuditLog Entity (JPA)
✅ Crear FraudAlert Entity (JPA)
✅ Préstamos (lógica de negocio: Service/Controller)
✅ Pagos (lógica de negocio: Service/Controller)
✅ Auditoría (lógica de negocio: Service/Controller + consumidor RabbitMQ)
✅ Logs
✅ RabbitMQ Events
✅ Detección de fraude (lógica de negocio: Service/Controller + consumidor RabbitMQ)

---

# Fase 5 - Frontend Angular

⬜ Crear proyecto Angular
⬜ Configurar estructura
⬜ Crear autenticación
⬜ Crear dashboard
⬜ Crear módulos bancarios

---

# Fase 6 - Calidad y despliegue

⬜ Pruebas unitarias
⬜ Pruebas integración
⬜ Documentación Swagger completa
⬜ Dockerización final
⬜ Deploy

---

# Próxima tarea exacta

1. Verificar en runtime el flujo completo de Auth (`register` → `login` → `GET /me` con el token → `refresh`).
2. Si todo funciona: commit/push a GitHub y cerrar formalmente la Fase 2.
3. Iniciar Fase 3: DTOs/Mappers (MapStruct) de `Customer`, luego `CustomerRepository`/`CustomerService`/`CustomerController`, según `11_endpoints.md`.

---

Versión: 1.4
Estado: En progreso
Última actualización: 18/07/2026

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.0 | 16/07/2026 | Documento inicial de tareas |
| 1.1 | 18/07/2026 | Corrección de desfase (entidades/endpoints marcados como completos), PostgreSQL + Docker + migración inicial completados |
| 1.2 | 18/07/2026 | Las 10 entidades JPA completadas y compilando (`BUILD SUCCESS`). Próxima tarea: DTOs y Mappers |
| 1.3 | 18/07/2026 | ADR-007: se adelantó Spring Security antes que DTOs/Mappers. Completado y compilando. Próxima tarea: registro, login y JWT |
| 1.4 | 18/07/2026 | Registro, Login, JWT y protección de endpoints implementados y compilando (`BUILD SUCCESS`). Se resolvieron 3 incidencias (paquetes singular/plural, `AuthController` mal ubicado/renombrado por error, `security/jwt/` que nunca se había creado). Pendiente: verificación en runtime y push a GitHub |

---

---

---

# Tareas

## Pendientes
- Verificar en runtime el flujo de Auditoría (Sesión 8).
- Confirmar estado real del módulo de Pagos.
- Implementar Detección de Fraude.

---

# Backlog y Seguimiento del Proyecto

## Banking System

Estado general:

Fase 4 - Funcionalidades avanzadas (en progreso)

---

# Corrección de desfase (Sesión 8, Ronda 1)

Este documento no reflejaba trabajo ya realizado y confirmado en `bitacora.md`. Se corrige aquí:

## Fase 2 - Seguridad

✅ Verificar en runtime el flujo completo de Auth *(pendiente de confirmación explícita — no está en el resumen recibido; revisar antes de dar por cerrado)*

## Fase 3 - Módulos principales

✅ Customer: Entity, Repository, Service, Controller — confirmado compilando en Sesión 8 (`mvn clean install` → BUILD SUCCESS, 55 archivos fuente)
✅ Account: Entity, gestión de balances, consultas — confirmado compilando en Sesión 8
✅ Transfer: Entity, transferencia bancaria, validación de saldo, historial — confirmado compilando en Sesión 8

---

# Fase 4 - Funcionalidades avanzadas (Sesión 8, Rondas 1 y 2)

✅ Crear Loan Entity (JPA)
✅ Crear Payment Entity (JPA)
✅ Crear AuditLog Entity (JPA)
✅ Crear FraudAlert Entity (JPA)
✅ Préstamos — `LoanRepository`, `LoanService` (`requestLoan`, `list`/`getById` con restricción de propiedad, `approve`/`reject`), `LoanController`, `LoanMapper`. Ver ADR-009 (tasa de interés fija 0.12 vía `application.yml`)
✅ Auditoría — `AuditService`/`AuditController` (lectura), `AuditEventPublisher`/`AuditEventListener` vía RabbitMQ, `AuditContext` (usuario/IP), `AuditLogRepository`/`AuditLogMapper`
✅ RabbitMQ Events — `RabbitMQConfig` (exchange/queue/binding), flujo de auditoría publicando y consumiendo `AuditEvent`
⬜ Pagos (lógica de negocio: Service/Controller) — *no confirmado en el resumen de esta ronda; revisar si ya existe o sigue pendiente*
⬜ Logs
⬜ Detección de fraude (lógica de negocio: Service/Controller + consumidor RabbitMQ)

Nota: `TransferService`, `AuthService`, `AccountService`, `AccountController` y `LoanService` se modificaron en la Ronda 2 para publicar `AuditEvent` tras sus operaciones sensibles. También se agregó `dto/account/AccountRequest.java` (deuda técnica pendiente de Fase 3).

---

# Próxima tarea exacta

1. Confirmar resultado de build (`mvn clean install`) tras integrar Auditoría.
2. Verificar en runtime: que un login o transferencia real generen un `AuditLog` consultable vía `AuditController`.
3. Confirmar estado real del módulo de Pagos (¿existe o falta implementarlo?).
4. Commit/push de esta ronda a GitHub.
5. Implementar Detección de Fraude (última pieza pendiente de Fase 4) y luego iniciar Fase 5 (Frontend Angular).

---

Versión: 1.5
Estado: En progreso
Última actualización: 2026-08-06

## Historial de cambios

| Versión | Fecha | Descripción |
|----------|-------|-------------|
| 1.5 | 06/08/2026 | Corrección de desfase: Customer/Account/Transfer (Fase 3) confirmados compilando. Fase 4: Préstamos, Auditoría y RabbitMQ Events completados a nivel de código. Pendiente: confirmar build/runtime de esta ronda, estado de Pagos, push a GitHub, y Detección de Fraude |

























# Fase 5 - Frontend Angular

Estado:

🟡 En progreso

✅ Crear proyecto Angular (`ng new frontend`, Angular 22.1.3, convención sin sufijo)
✅ Configurar estructura (core/shared/features)
✅ Configurar environments (apiUrl dev/prod)
✅ Configurar HttpClient + interceptor JWT + guard de auth
✅ Crear autenticación
✅ Login (conectado a POST /auth/login, campos reales: token/refreshToken/expiresIn)
✅ Registro público (conectado a POST /auth/register)
⬜ Revisar si /auth/register permite elegir role libremente (riesgo de seguridad, ver decisiones.md)
✅ Diseño visual completo — identidad "ledger/libreta contable" (ink navy + gold + emerald + mono para cifras)
✅ Login + Register restyleados
✅ Shell (sidebar + topbar) reutilizable, con router-outlet
✅ Dashboard conectado a GET /accounts (pendiente de corregir: backend devuelve Page<T>, no array — ver nota abajo)
✅ Sidebar con visibilidad condicional por rol (isAdminOrEmployee / isAdmin, computed signals)
✅ Módulo Clientes completo (CRUD)
✅ customer-list (tabla paginada, búsqueda, eliminar con confirm)
✅ customer-form (crear/editar, reactive forms)
✅ Página protegida con roleGuard(['ROLE_ADMIN','ROLE_EMPLOYEE'])
✅  Módulo Cuentas (accounts) — AccountService creado pero asume array plano, hay que corregirlo a Page<Account> igual que Customer
✅ Módulo Transferencias
✅ Módulo Préstamos
⬜ Módulo Pagos
⬜ Módulo Auditoría (solo lectura, admin)
⬜ Módulo Fraude (solo lectura, admin)
⬜ Revisar TODOS los controllers del backend (Account, Transfer, Loan, Payment, Audit, Fraud) para confirmar @PreAuthorize correcto por rol — se encontró que CustomerController.list() no tenía restricción y cualquier CUSTOMER autenticado podía listar clientes ajenos vía URL directa
⬜ Confirmar que GET /accounts filtre por el usuario autenticado en el backend (no solo confiar en que el frontend "se porte bien")
⬜ Dockerizar frontend (Dockerfile pendiente — docker compose up intentó buildearlo y falló por esto)

---

# Próxima tarea

Corregir AccountService (Page<Account>) y construir el módulo de Cuentas completo, reutilizando el patrón ya probado en Clientes (list + form + guard de rol).
---

# Actualizacion 16/08/2026 - Sesion 11

## Completado

- Cliente admin puede crear cliente con usuario de acceso CUSTOMER desde `customer-form`.
- Cliente admin puede abrir cuenta inicial con saldo desde `customer-form`.
- Formulario de cuentas permite saldo inicial real (`initialBalance`) y tipos reales `SAVINGS`/`CHECKING`.
- Backend filtra `GET /accounts` por customer autenticado cuando el rol es `ROLE_CUSTOMER`.
- Dashboard conecta acciones rapidas a rutas reales.
- Modulo Fraude conectado a `/api/v1/fraud-alerts` con accion de resolver.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Ejecutar `mvn test` o `mvn clean install` cuando Maven este disponible en el entorno.
- Probar runtime completo con backend levantado: alta de cliente con usuario + saldo, login como CUSTOMER y visualizacion de sus cuentas.
- Revisar autorizacion de Transfer, Loan y Payment en backend antes de considerar cerrado el hardening de seguridad.

---

# Actualizacion 16/08/2026 - Sesion 12

## Completado

- Historial de transferencias protegido por `Authentication`.
- Clientes `ROLE_CUSTOMER` solo pueden listar transferencias relacionadas con sus propias cuentas.
- Si se consulta historial por `accountId`, backend valida primero acceso a esa cuenta.
- Dashboard muestra acciones rapidas segun rol.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Compilar backend con Maven disponible.
- Definir alcance de Pagos: registro informativo de cuota, o pago con debito real de una cuenta.

---

# Actualizacion 16/08/2026 - Sesion 13

## Completado

- Pagos dejan de ser solo informativos: ahora requieren `accountId` y debitan saldo real.
- Migracion `V3__add_payment_account.sql` agrega `payment.account_id`.
- `PaymentResponse` devuelve cuenta origen (`accountId`, `accountNumber`).
- Formulario de pagos permite elegir cuenta activa del mismo cliente del prestamo.
- Lista de pagos muestra la cuenta origen.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Ejecutar build backend con Maven.
- Probar migracion y flujo de pago contra PostgreSQL real.

---

# Actualizacion 16/08/2026 - Sesion 15

## Completado

- CUSTOMER puede abrir cuenta propia desde `/accounts/new`.
- Backend limita a una cuenta no eliminada por tipo (`SAVINGS` y `CHECKING`) por cliente.
- CUSTOMER no puede asignarse saldo inicial; queda en `0`.
- ADMIN/EMPLOYEE conservan alta de cuenta para cualquier cliente con saldo inicial de pruebas.
- Campos de monto formatean comas automaticamente mientras se escribe.
- Frontend compila con `npm.cmd run build`.
- `http://localhost:4200/login` responde HTTP 200.

## Pendiente

- Compilar backend con Maven.
- Validar migracion `V4__limit_one_account_per_type.sql` con Postgres.
- Si la migracion falla, limpiar duplicados de cuenta por `customer_id` + `account_type` antes de reintentar.

---

# Actualizacion 17/08/2026 - Sesion 16

## Completado

- Integracion backend de Frankfurter mediante `ExchangeRateService`.
- Nuevo controller `/api/v1/exchange-rates`.
- Endpoints disponibles:
  - `GET /exchange-rates/rates`
  - `GET /exchange-rates/rate/{base}/{quote}`
  - `GET /exchange-rates/convert`
  - `GET /exchange-rates/currencies`
  - `GET /exchange-rates/currencies/{code}`
  - `GET /exchange-rates/providers`
- Configuracion `FRANKFURTER_BASE_URL` para cambiar el proveedor/base URL sin tocar codigo.
- Nuevo `CurrencyService` en Angular.
- Nueva pantalla `/exchange-rates` con conversor y consulta de tasas.
- Sidebar actualizado con `Divisas`.
- `account-list` usa conversion a USD via backend.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Compilar backend con Maven disponible.
- Probar endpoints de divisas con backend levantado y JWT valido.
- Probar pantalla `/exchange-rates` en runtime.
- Confirmar comportamiento cuando no haya internet o Frankfurter responda error.

---

# Actualizacion 17/08/2026 - Sesion 17

## Completado

- Integracion backend de Banco Popular mediante `BpdApiService`.
- Nuevo controller `/api/v1/bpd`.
- Endpoints disponibles:
  - `POST /bpd/confirm-account`
  - `GET /bpd/atm-locations?page=0`
- Configuracion por variables:
  - `BPD_API_BASE_URL`
  - `BPD_CONFIRM_ACCOUNT_PATH`
  - `BPD_ATM_LOCATIONS_PATH`
  - `BPD_TOKEN_URL`
  - `BPD_CLIENT_ID`
  - `BPD_CLIENT_SECRET`
  - `BPD_OAUTH_SCOPE`
- Nuevo `BpdService` en Angular.
- Nueva pantalla `/bpd` con confirmacion de titularidad y ubicaciones ATM.
- Sidebar actualizado con `Banco Popular`.
- Documentacion creada en `docs/api/22_bpd_api.md`.
- Frontend compila con `npm.cmd run build`.
- `http://localhost:4200/bpd` responde HTTP 200.

## Pendiente

- Compilar backend con Maven disponible.
- Configurar credenciales reales BPD.
- Probar `POST /api/v1/bpd/confirm-account` con JWT valido.
- Probar `GET /api/v1/bpd/atm-locations?page=0` con JWT valido.

---

# Actualizacion 17/08/2026 - Sesion 18

## Completado

- Integracion backend de Finnhub mediante `FinnhubMarketService`.
- Nuevo controller `/api/v1/markets`.
- Endpoints disponibles:
  - `GET /markets/symbols/search`
  - `GET /markets/quote`
  - `GET /markets/company-profile`
  - `GET /markets/status`
  - `GET /markets/holidays`
  - `GET /markets/news`
  - `GET /markets/company-news`
  - `GET /markets/basic-financials`
  - `GET /markets/recommendations`
- Configuracion por variables:
  - `FINNHUB_BASE_URL`
  - `FINNHUB_API_KEY`
- Nuevo `MarketService` en Angular.
- Nueva pantalla `/markets` con datos de mercado, noticias, perfil de empresa e indicadores.
- Sidebar actualizado con `Mercados`.
- Documentacion creada en `docs/api/23_finnhub_market_api.md`.
- Frontend compila con `npm.cmd run build`.
- `http://localhost:4200/markets` responde HTTP 200.

## Pendiente

- Compilar backend con Maven disponible.
- Configurar `FINNHUB_API_KEY`.
- Probar endpoints de mercado con backend levantado y JWT valido.
- Vigilar limites `429` de Finnhub si se consulta demasiado rapido.

---

# Actualizacion 17/08/2026 - Sesion 19

## Completado

- Corregido el fallo de arranque por bean faltante `RestClient.Builder`.
- Agregado `RestClientConfig`.
- `RestClient.Builder` registrado como bean `prototype` para las integraciones Frankfurter, BPD y Finnhub.

## Pendiente

- Reejecutar desde la terminal del usuario:
  `cd banking-system\backend`
  `set DB_PASSWORD=Gabriel90xp`
  `set RABBITMQ_PASSWORD=Gabriel90xp`
  `mvn spring-boot:run`
- Confirmar que Spring ya no falla creando `BpdApiService`.

---

# Actualizacion 17/08/2026 - Sesion 20

## Completado

- Corregido login que podia quedarse cargando.
- `AuthService` ya no dispara `/auth/me` desde el constructor.
- Login espera perfil de usuario antes de navegar al dashboard.
- Ajuste final: login usa el JWT para entrar inmediatamente al dashboard y refresca `/auth/me` en segundo plano.
- Guards restauran sesion/perfil antes de autorizar rutas.
- Interceptor no manda token viejo a `/auth/login`, `/auth/register` ni `/auth/refresh`.
- Timeout de 15 segundos en login y perfil.
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS.

## Pendiente

- Probar en navegador el login con `gabriel@test.com / Test1234!`.

---

# Actualizacion 17/08/2026 - Sesion 22

## Completado

- Modulo Trading agregado en backend y frontend.
- Migracion `V5__add_trading.sql` aplicada correctamente en PostgreSQL local durante la prueba de arranque.
- Endpoints `/api/v1/trading/portfolio`, `/deposit`, `/withdraw`, `/buy`, `/sell`, `/orders`.
- Frontend `/trading` con TradingView Advanced Chart, ingreso/retiro, compra/venta, posiciones y ordenes.
- Documentacion creada en `docs/api/24_trading_api.md`.
- `npm.cmd run build` en `frontend/`: BUILD SUCCESS sin warnings.
- Compilacion backend manual con `javac --release 21`: OK.

## Pendiente

- Reiniciar el backend que sigue ocupando `localhost:8080` para cargar el codigo nuevo.
- Probar flujo completo desde navegador con un usuario CUSTOMER vinculado a un Customer con cuenta activa.

---

# Actualizacion 17/08/2026 - Sesion 23

## Completado

- Transferencias a otros usuarios activas por numero de cuenta destino.
- Transferencias entre cuentas propias activas por selector de cuentas.
- Limite configurable de 10 transferencias por cuenta origen en 24 horas.
- Backend valida origen propio para `CUSTOMER`, origen activo y destino activo.
- Historial devuelve y muestra numeros de cuenta.
- `docs/api/15_transfers_api.md` actualizado.
- Backend compila manualmente con `javac --release 21`.
- Frontend compila con `npm.cmd run build`.

## Pendiente

- Reiniciar backend real en `localhost:8080` para cargar el codigo nuevo.
- Probar desde navegador `/transfers/new` con un usuario CUSTOMER.

---

# Actualizacion 17/08/2026 - Sesion 24

## Completado

- Se agrego en `PROJECT_CONTEXT.md` una regla permanente: todo avance debe guardarse en `docs/` antes de cerrar sesion.
- Se definieron los documentos minimos a actualizar: `AI_HANDOFF.md`, `docs/management/tareas.md`, `docs/management/bitacora.md`, `docs/api/` y `docs/management/decisiones.md` cuando aplique.

## Pendiente

- Mantener esta regla en cada cambio futuro.
