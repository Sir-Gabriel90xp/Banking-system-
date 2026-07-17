# 12. Migraciones

## Objetivo
- Registrar los cambios estructurales de la base de datos del sistema.

## Estrategia recomendada
- Usar migraciones versionadas.
- Cada cambio debe incluir rollback cuando sea posible.
- Mantener el historial de cambios en control de versiones.

## Ejemplo de flujo
1. Crear la migración.
2. Ejecutar la migración en desarrollo.
3. Validar esquema y datos.
4. Aplicar en producción con revisión.
