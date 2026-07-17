# 10. Reglas de negocio que afectan la base de datos

## Reglas generales
- El saldo de una cuenta no puede ser negativo si la cuenta es de ahorro corriente sin sobregiro.
- Toda transferencia debe afectar dos cuentas en una misma transacción.
- Un movimiento debe quedar asociado a una cuenta válida.
- Un préstamo debe tener un estado definido: pendiente, aprobado, rechazado o pagado.
- Los pagos deben registrarse con referencia única.

## Reglas de integridad
- No se permiten movimientos sin cuenta asociada.
- No se aceptan transferencias entre cuentas inexistentes.
- Los montos deben ser mayores que cero.
