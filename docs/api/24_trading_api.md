# 24 - Trading API

Modulo para operar un wallet de trading ligado al cliente autenticado.

Base path:

`/api/v1/trading`

Todos los endpoints requieren JWT. El servicio resuelve el `Customer` desde el usuario autenticado y valida que la cuenta bancaria usada para ingresar o retirar pertenezca a ese cliente.

## Portfolio

`GET /portfolio`

Devuelve `cashBalance`, `portfolioValue`, `totalEquity` y `positions`.

Si el cliente no tiene wallet, se crea automaticamente con balance 0 USD.

## Ingresar efectivo

`POST /deposit`

```json
{
  "accountId": "uuid",
  "amount": 100.00
}
```

Debita la cuenta bancaria y aumenta el efectivo del wallet.

## Retirar efectivo

`POST /withdraw`

```json
{
  "accountId": "uuid",
  "amount": 100.00
}
```

Reduce el efectivo del wallet y acredita la cuenta bancaria.

## Comprar

`POST /buy`

```json
{
  "symbol": "NASDAQ:AAPL",
  "quantity": 1
}
```

Ejecuta la compra si hay efectivo suficiente. Usa Finnhub cuando `FINNHUB_API_KEY` esta configurado; si no, usa precio demo deterministico para desarrollo local.

## Vender

`POST /sell`

```json
{
  "symbol": "NASDAQ:AAPL",
  "quantity": 1
}
```

Ejecuta la venta si existe posicion suficiente.

## Ordenes

`GET /orders?page=0&size=10&sort=createdAt,desc`

Devuelve un `Page<TradingOrderResponse>`.

## Frontend

Ruta:

`/trading`

Incluye resumen de efectivo/equity, TradingView Advanced Chart, ticket de compra/venta, ingreso/retiro desde cuentas bancarias, posiciones y ordenes recientes.
