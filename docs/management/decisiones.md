# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.








# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.

---

# ADR-005

## Adelantar configuración de control de versiones

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

Inicializar el repositorio Git y configurar el repositorio remoto en GitHub (`https://github.com/Sir-Gabriel90xp/Banking-system-.git`) antes de completar la documentación de entidades detalladas (`08_entities.md`) y el diseño final de endpoints (`11_endpoints.md`).

Se completaron los siguientes puntos:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub.
- Creación de archivo `.gitignore` (Spring Boot + Angular + Docker).
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

## Motivo

Contar con versionado desde el inicio para no perder el progreso de la documentación ya generada, y dejar preparado el entorno de control de versiones en paralelo a la finalización de la documentación técnica pendiente.

## Impacto

Esto representa una desviación puntual del flujo estricto originalmente acordado (completar toda la documentación antes de tocar control de versiones/código). No se ha escrito código de aplicación; solo se versionó la documentación existente. Los pendientes de documentación (entidades detalladas y diseño final de endpoints) siguen abiertos y deben completarse antes de iniciar el desarrollo del backend.
















# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.

---

# ADR-005

## Adelantar configuración de control de versiones

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

Inicializar el repositorio Git y configurar el repositorio remoto en GitHub (`https://github.com/Sir-Gabriel90xp/Banking-system-.git`) antes de completar la documentación de entidades detalladas (`08_entities.md`) y el diseño final de endpoints (`11_endpoints.md`).

Se completaron los siguientes puntos:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub.
- Creación de archivo `.gitignore` (Spring Boot + Angular + Docker).
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

## Motivo

Contar con versionado desde el inicio para no perder el progreso de la documentación ya generada, y dejar preparado el entorno de control de versiones en paralelo a la finalización de la documentación técnica pendiente.

## Impacto

Esto representa una desviación puntual del flujo estricto originalmente acordado (completar toda la documentación antes de tocar control de versiones/código). No se ha escrito código de aplicación; solo se versionó la documentación existente. Los pendientes de documentación (entidades detalladas y diseño final de endpoints) siguen abiertos y deben completarse antes de iniciar el desarrollo del backend.

---

# ADR-006

## Usar Java 25 (LTS) + Spring Boot 4.1.0 en lugar de Java 21 + Spring Boot 3.x

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

El proyecto backend se construirá sobre:

- **Java 25 (LTS)** — versión ya instalada en el entorno de desarrollo.
- **Spring Boot 4.1.0** — versión activamente soportada (OSS) al momento de iniciar el proyecto, compatible oficialmente con Java 25/26.

Esto reemplaza la configuración inicial del `pom.xml` (Java 21 + Spring Boot 3.3.4), que fue la primera aproximación antes de detectar que esa rama ya no recibe soporte.

## Motivo

- Spring Boot 3.3.4 (usado inicialmente) ya alcanzó su fin de soporte (EOL). A julio de 2026, solo Spring Boot 4.0 y 4.1 están en soporte activo.
- Java 25 es LTS y es la versión ya disponible en la máquina de desarrollo; forzar Java 21 hubiera requerido instalar una JDK adicional sin necesidad real.
- Se evaluaron 3 combinaciones posibles (ver discusión en la sesión): Java 21 + Boot 3.5, Java 25 + Boot 4.1, y Java 25 + Boot 3.5. Esta última se descartó por incompatibilidades activas y no resueltas entre Lombok y JDK 25 sobre la rama 3.x.

## Cambios técnicos derivados

- Lombok actualizado a **1.18.44** (mínimo requerido para JDK 25: 1.18.40), con `lombok-mapstruct-binding` agregado para compatibilidad con MapStruct.
- Starters renombrados según la modularización de Spring Boot 4: `spring-boot-starter-web` → `spring-boot-starter-webmvc`; `flyway-core` (dependencia suelta) → `spring-boot-starter-flyway`.
- Dependencias de test reemplazadas por starters específicos por tecnología: `spring-boot-starter-webmvc-test`, `spring-boot-starter-data-jpa-test`, `spring-boot-starter-security-test` (ya no existe un `spring-boot-starter-test` genérico en Boot 4).
- `springdoc-openapi` actualizado a **3.0.3** (primera línea compatible con Spring Framework 7).
- `<mainClass>` especificado explícitamente en `spring-boot-maven-plugin` para asegurar el empaquetado correcto del JAR ejecutable.

## Impacto

Cambio pendiente a futuro por seguir de cerca: Spring Boot 4 usa Jackson 3 por defecto (cambia el groupId de `com.fasterxml.jackson` a `tools.jackson`), lo cual deberá tenerse en cuenta al implementar DTOs y serialización más adelante. No hay impacto en la documentación de arquitectura, modelo de datos o API ya definida — estos cambios son de infraestructura/build, no de diseño.












# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.








# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.

---

# ADR-005

## Adelantar configuración de control de versiones

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

Inicializar el repositorio Git y configurar el repositorio remoto en GitHub (`https://github.com/Sir-Gabriel90xp/Banking-system-.git`) antes de completar la documentación de entidades detalladas (`08_entities.md`) y el diseño final de endpoints (`11_endpoints.md`).

Se completaron los siguientes puntos:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub.
- Creación de archivo `.gitignore` (Spring Boot + Angular + Docker).
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

## Motivo

Contar con versionado desde el inicio para no perder el progreso de la documentación ya generada, y dejar preparado el entorno de control de versiones en paralelo a la finalización de la documentación técnica pendiente.

## Impacto

Esto representa una desviación puntual del flujo estricto originalmente acordado (completar toda la documentación antes de tocar control de versiones/código). No se ha escrito código de aplicación; solo se versionó la documentación existente. Los pendientes de documentación (entidades detalladas y diseño final de endpoints) siguen abiertos y deben completarse antes de iniciar el desarrollo del backend.
















# Decisiones de diseño

## Decisión 1
- Usar una arquitectura modular con backend y frontend separados.

## Decisión 2
- Elegir una base de datos relacional para la gestión de cuentas y movimientos.









# Architecture Decision Records

## Banking System

Este documento registra decisiones técnicas importantes.

---

# ADR-001

## Usar PostgreSQL como base de datos

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar PostgreSQL como sistema gestor de base de datos.

## Motivo

PostgreSQL ofrece:

- Soporte ACID.
- Excelente integración con Spring Boot.
- Buen rendimiento.
- Uso profesional empresarial.

---

# ADR-002

## Utilizar RabbitMQ

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar RabbitMQ para comunicación asíncrona.

## Motivo

Permite separar procesos como:

- Auditoría.
- Logs.
- Detección de fraude.

---

# ADR-003

## Implementar JWT

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

Utilizar JWT para autenticación.

## Motivo

Permite autenticación stateless y es estándar en APIs modernas.

---

# ADR-004

## Usar DTOs

Fecha:

16/07/2026

Estado:

Aceptado


## Decisión

No devolver entidades directamente.

## Motivo

Mejora:

- Seguridad.
- Separación de responsabilidades.
- Control de datos expuestos.

---

# ADR-005

## Adelantar configuración de control de versiones

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

Inicializar el repositorio Git y configurar el repositorio remoto en GitHub (`https://github.com/Sir-Gabriel90xp/Banking-system-.git`) antes de completar la documentación de entidades detalladas (`08_entities.md`) y el diseño final de endpoints (`11_endpoints.md`).

Se completaron los siguientes puntos:

- Inicialización de repositorio Git local.
- Creación de repositorio remoto en GitHub.
- Creación de archivo `.gitignore` (Spring Boot + Angular + Docker).
- Primer commit con la documentación inicial del proyecto.
- Configuración de ramas principales: `main` y `develop`.

## Motivo

Contar con versionado desde el inicio para no perder el progreso de la documentación ya generada, y dejar preparado el entorno de control de versiones en paralelo a la finalización de la documentación técnica pendiente.

## Impacto

Esto representa una desviación puntual del flujo estricto originalmente acordado (completar toda la documentación antes de tocar control de versiones/código). No se ha escrito código de aplicación; solo se versionó la documentación existente. Los pendientes de documentación (entidades detalladas y diseño final de endpoints) siguen abiertos y deben completarse antes de iniciar el desarrollo del backend.

---

# ADR-006

## Usar Java 25 (LTS) + Spring Boot 4.1.0 en lugar de Java 21 + Spring Boot 3.x

Fecha:

16/07/2026

Estado:

Aceptado

## Decisión

El proyecto backend se construirá sobre:

- **Java 25 (LTS)** — versión ya instalada en el entorno de desarrollo.
- **Spring Boot 4.1.0** — versión activamente soportada (OSS) al momento de iniciar el proyecto, compatible oficialmente con Java 25/26.

Esto reemplaza la configuración inicial del `pom.xml` (Java 21 + Spring Boot 3.3.4), que fue la primera aproximación antes de detectar que esa rama ya no recibe soporte.

## Motivo

- Spring Boot 3.3.4 (usado inicialmente) ya alcanzó su fin de soporte (EOL). A julio de 2026, solo Spring Boot 4.0 y 4.1 están en soporte activo.
- Java 25 es LTS y es la versión ya disponible en la máquina de desarrollo; forzar Java 21 hubiera requerido instalar una JDK adicional sin necesidad real.
- Se evaluaron 3 combinaciones posibles (ver discusión en la sesión): Java 21 + Boot 3.5, Java 25 + Boot 4.1, y Java 25 + Boot 3.5. Esta última se descartó por incompatibilidades activas y no resueltas entre Lombok y JDK 25 sobre la rama 3.x.

## Cambios técnicos derivados

- Lombok actualizado a **1.18.44** (mínimo requerido para JDK 25: 1.18.40), con `lombok-mapstruct-binding` agregado para compatibilidad con MapStruct.
- Starters renombrados según la modularización de Spring Boot 4: `spring-boot-starter-web` → `spring-boot-starter-webmvc`; `flyway-core` (dependencia suelta) → `spring-boot-starter-flyway`.
- Dependencias de test reemplazadas por starters específicos por tecnología: `spring-boot-starter-webmvc-test`, `spring-boot-starter-data-jpa-test`, `spring-boot-starter-security-test` (ya no existe un `spring-boot-starter-test` genérico en Boot 4).
- `springdoc-openapi` actualizado a **3.0.3** (primera línea compatible con Spring Framework 7).
- `<mainClass>` especificado explícitamente en `spring-boot-maven-plugin` para asegurar el empaquetado correcto del JAR ejecutable.

## Impacto

Cambio pendiente a futuro por seguir de cerca: Spring Boot 4 usa Jackson 3 por defecto (cambia el groupId de `com.fasterxml.jackson` a `tools.jackson`), lo cual deberá tenerse en cuenta al implementar DTOs y serialización más adelante. No hay impacto en la documentación de arquitectura, modelo de datos o API ya definida — estos cambios son de infraestructura/build, no de diseño.

---

# ADR-007

## Adelantar Spring Security y JWT antes que DTOs y Mappers

Fecha:

18/07/2026

Estado:

Aceptado

## Decisión

Invertir el orden definido en `AI_HANDOFF.md` (tras la corrección de Sesión 5): en vez de crear primero los DTOs y Mappers de las 10 entidades y luego implementar seguridad, se implementará primero la Fase 2 completa de `tareas.md` — Spring Security, registro, login, JWT y protección de endpoints — y los DTOs/Mappers del resto de entidades se retomarán después.

## Motivo

Es una decisión de secuencia de trabajo, no de arquitectura: Spring Security y la emisión de JWT no dependen de que existan DTOs de `Customer`, `Account`, `Transfer`, etc. (sí requieren, como mínimo, un `UserDetailsService` sobre `AppUser`/`Role`, ya disponibles como entidades JPA). Se prioriza tener la autenticación funcionando cuanto antes.

## Impacto

- Las tareas `⬜ Implementar Spring Security`, `⬜ Implementar registro`, `⬜ Implementar Login`, `⬜ Implementar JWT`, `⬜ Proteger endpoints` (Fase 2 de `tareas.md`) pasan a ejecutarse antes que "Crear DTOs y Mappers" para el resto de entidades.
- Es probable que se necesite un DTO/Mapper mínimo para `AppUser`/`Role` como parte de la propia implementación de Auth (login, registro, perfil `/me`) — esto no contradice ADR-004, simplemente adelanta ese DTO puntual en vez de crear los 10 de una vez.
- No afecta el diseño de endpoints ya aprobado (`11_endpoints.md`) ni el modelo de datos. Es un reordenamiento de tareas, no un cambio de alcance.
- `AI_HANDOFF.md` y `tareas.md` deben actualizarse al cierre de esta fase para reflejar el nuevo orden ejecutado.