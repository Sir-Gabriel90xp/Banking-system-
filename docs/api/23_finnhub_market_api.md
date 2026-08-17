# API de Mercados Finnhub

Fuente externa: Finnhub (`https://finnhub.io/api/v1`).

Base interna:

```text
/api/v1/markets
```

Todos los endpoints internos requieren JWT. La API key de Finnhub se configura solo en backend.

## Configuracion

```yaml
app:
  finnhub:
    base-url: ${FINNHUB_BASE_URL:https://finnhub.io/api/v1}
    api-key: ${FINNHUB_API_KEY:}
```

Si `FINNHUB_API_KEY` no esta configurada, el backend responde `503 Service Unavailable`.

## Endpoints integrados

| Metodo | Endpoint interno | Finnhub externo |
|---|---|---|
| GET | `/symbols/search?q=apple&exchange=US` | `/search` |
| GET | `/quote?symbol=AAPL` | `/quote` |
| GET | `/company-profile?symbol=AAPL` | `/stock/profile2` |
| GET | `/status?exchange=US` | `/stock/market-status` |
| GET | `/holidays?exchange=US` | `/stock/market-holiday` |
| GET | `/news?category=general` | `/news` |
| GET | `/company-news?symbol=AAPL&from=2026-08-03&to=2026-08-17` | `/company-news` |
| GET | `/basic-financials?symbol=AAPL` | `/stock/metric?metric=all` |
| GET | `/recommendations?symbol=AAPL` | `/stock/recommendation` |

## Notas

- El backend envia la credencial por header `X-Finnhub-Token`.
- El frontend consume solo `/api/v1/markets`; no conoce la API key.
- La pantalla Angular esta en `/markets`.
- Los endpoints de WebSocket y premium no se integraron en esta fase.
- Finnhub puede responder `429` si se excede el limite de llamadas.
