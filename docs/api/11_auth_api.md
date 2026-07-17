# 11. API de autenticación

## Objetivo
- Gestionar el inicio de sesión, cierre de sesión y emisión de tokens.

## Endpoints sugeridos
- POST /api/v1/auth/login
- POST /api/v1/auth/logout
- POST /api/v1/auth/refresh

## Consideraciones
- Validación de credenciales.
- Expiración de tokens.
- Registro de intentos fallidos.
