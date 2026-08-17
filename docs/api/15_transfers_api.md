# 15 - Transferencias API

Base path:

`/api/v1/transfers`

Todos los endpoints requieren JWT.

## Crear transferencia

`POST /api/v1/transfers`

Permite transferir desde una cuenta origen activa del usuario autenticado hacia:

- otra cuenta propia, enviando `destinationAccount`;
- una cuenta de otro usuario registrado, enviando `destinationAccountNumber`.

Body para cuenta propia:

```json
{
  "sourceAccount": "uuid-cuenta-origen",
  "destinationAccount": "uuid-cuenta-destino",
  "amount": 250.00
}
```

Body para otro usuario:

```json
{
  "sourceAccount": "uuid-cuenta-origen",
  "destinationAccountNumber": "0000000001",
  "amount": 250.00
}
```

Reglas:

- origen y destino no pueden ser la misma cuenta;
- la cuenta origen debe estar activa;
- la cuenta destino debe existir y estar activa;
- usuarios `CUSTOMER` solo pueden usar cuentas origen propias;
- limite por defecto: 10 transferencias por cuenta origen en las ultimas 24 horas;
- el limite se configura con `TRANSFER_DAILY_LIMIT` o `app.transfer.daily-limit`.

## Historial

`GET /api/v1/transfers/history?page=0&size=10`

Filtros opcionales:

- `accountId`
- `status`

La respuesta incluye UUID y numero de cuenta:

```json
{
  "id": "uuid-transferencia",
  "sourceAccount": "uuid-cuenta-origen",
  "sourceAccountNumber": "0000000001",
  "destinationAccount": "uuid-cuenta-destino",
  "destinationAccountNumber": "0000000002",
  "amount": 250.00,
  "status": "COMPLETED",
  "createdAt": "2026-08-17T00:00:00Z"
}
```
