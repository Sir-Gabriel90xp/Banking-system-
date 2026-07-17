# 09. Relaciones y cardinalidades

## Relaciones principales
- Un usuario puede tener muchos clientes.
- Un cliente puede tener muchas cuentas.
- Una cuenta puede tener muchos movimientos.
- Una transferencia involucra dos cuentas.
- Un préstamo pertenece a un cliente.
- Un pago está asociado a una cuenta o a un préstamo.

## Cardinalidades sugeridas
- Usuario 1:N Cliente
- Cliente 1:N Cuenta
- Cuenta 1:N Movimiento
- Cuenta 1:N Transferencia (como origen o destino)
- Cliente 1:N Préstamo
- Cuenta 1:N Pago
