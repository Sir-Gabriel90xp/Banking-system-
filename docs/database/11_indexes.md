# 11. Índices y optimización

## Índices recomendados
- Índice en `customers.user_id`.
- Índice en `accounts.customer_id`.
- Índice en `transactions.account_id`.
- Índice en `transfers.created_at`.
- Índice compuesto en `accounts.number` y `customer_id`.

## Objetivos
- Mejorar búsquedas por cliente, cuenta y fecha.
- Reducir tiempos de consulta en reportes y cierres.
- Optimizar operaciones transaccionales.
