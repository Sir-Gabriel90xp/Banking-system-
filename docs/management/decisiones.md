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
















---

# ADR-008

## Usar ResponseStatusException en vez de excepciones de negocio propias (temporal)

Fecha:

18/07/2026

Estado:

Aceptado (provisional)

## Decisión

Para el manejo de errores del módulo Auth (`AuthService`, `GlobalExceptionHandler`), se usó `org.springframework.web.server.ResponseStatusException` en vez de crear ya la jerarquía de excepciones de negocio mencionada en `02_architecture.md` (`ResourceNotFoundException`, `ValidationException`, `UnauthorizedException`, `BusinessException`).

## Motivo

Esa jerarquía de excepciones globales todavía no existía en el proyecto (no se habían creado Services/Controllers hasta la Sesión 7). Crearla completa solo para Auth hubiera significado diseñarla sin ver primero las necesidades reales de los demás módulos (Customers, Accounts, Transfers, Loans), con riesgo de tener que rehacerla.

## Impacto

- `GlobalExceptionHandler` (nuevo, en `exceptions/`) por ahora solo mapea `ResponseStatusException` y `MethodArgumentNotValidException` al formato estándar de `04_api_design.md`.
- Cuando se implemente Fase 3 (Customers, Accounts, Transfers) y surja la necesidad real de excepciones de negocio específicas, se debe crear la jerarquía propia y migrar `AuthService`/`GlobalExceptionHandler` a ella, para no tener dos convenciones de manejo de errores conviviendo en el proyecto.
- No afecta el contrato de la API: el formato de respuesta de error (`success/message/errors/timestamp`) es el mismo sin importar qué tipo de excepción lo produce.












## ADR-0XX: Identidad visual del frontend — estética "ledger" en vez de defaults de Angular/IA

**Contexto:** El scaffold default de Angular y los estilos "genéricos" de IA (cream+terracota, negro+neón, etc.) no comunican confianza bancaria ni tienen relación con el dominio del proyecto.

**Decisión:** Adoptar una identidad basada en libretas contables reales: fondo tinta oscura para navegación, papel claro para contenido, cifras siempre en tipografía monoespaciada con `tabular-nums` (autenticidad de extracto bancario), acento dorado para jerarquía y esmeralda/terracota para créditos/débitos. Tokens centralizados en `styles.scss` como variables CSS.

**Alcance:** Aplica a login, registro, shell (sidebar/topbar) y todos los módulos subsecuentes — mantener consistencia, no reinventar paleta por módulo.

---

## ADR-0XX: Autorización debe vivir en el backend, no solo ocultarse en el frontend

**Contexto:** Al registrar un usuario público con rol CUSTOMER, se comprobó que podía acceder a `/customers` (gestión de otros clientes) simplemente escribiendo la URL, porque `CustomerController.list()` no tenía `@PreAuthorize`. El sidebar tampoco distinguía roles.

**Decisión:** 
1. El frontend oculta opciones de navegación según rol (cosmético, mejora UX) vía signals computados en `Shell` y un `roleGuard` reutilizable por ruta.
2. Esto **no reemplaza** la autorización real: cada endpoint del backend debe validar el rol con `@PreAuthorize` explícito, sin asumir que el frontend "se porta bien". Pendiente auditar todos los controllers existentes bajo esta premisa.

**Regla general adoptada:** ningún dato o acción sensible debe depender únicamente de que la UI no muestre el botón — el backend es la única fuente de verdad para permisos.

---

## Nota técnica: no adivinar contratos de API

**Contexto:** Varias horas de la Sesión 10 se fueron en corregir desajustes entre lo que el frontend asumía (`accessToken`, `role` sin prefijo) y lo que el backend realmente devolvía (`token`, `role` con prefijo `ROLE_`), porque el frontend se construyó antes de confirmar la forma real de las respuestas.

**Regla adoptada de aquí en adelante:** antes de construir un módulo nuevo del frontend, confirmar con `curl` contra el backend real + lectura directa de los DTOs/Controller correspondientes. Nunca asumir nombres de campo por convención o por lo que "debería" ser.
---

## ADR-0XX: Compilar backend a Java 21 para compatibilidad local

**Fecha:** 17/08/2026

**Contexto:** El backend estaba configurado con `<java.version>25</java.version>`, generando clases `class file version 69`. En una terminal con JDK 21 en `PATH`, esas clases no arrancan (`UnsupportedClassVersionError`) y Maven tampoco puede compilar con `--release 25`.

**Decision:** Mantener Spring Boot 4.1.0, pero compilar el proyecto con target Java 21 (`<java.version>21</java.version>`). JDK 25 puede ejecutar bytecode Java 21, y JDK 21 queda soportado para desarrollo local.

**Impacto:** Despues del cambio se debe ejecutar `mvn clean spring-boot:run` al menos una vez para eliminar clases antiguas compiladas a Java 25.

---

## ADR-0XX: Trading local debe seguir funcionando sin API key de mercado

**Fecha:** 17/08/2026

**Contexto:** El modulo Trading necesita precio de ejecucion para comprar/vender. Finnhub ya existe en el proyecto, pero en desarrollo local `FINNHUB_API_KEY` puede no estar configurada y eso no debe bloquear el flujo de deposito, compra, venta y retiro.

**Decision:** `TradingService` intenta obtener el quote con Finnhub y, si no hay API key o la llamada falla, usa un precio demo deterministico basado en el simbolo. El frontend mantiene TradingView para visualizacion del grafico.

**Impacto:** El flujo local funciona sin secretos externos. En produccion, configurar `FINNHUB_API_KEY` para precios reales antes de considerar las ordenes como datos financieros reales.
