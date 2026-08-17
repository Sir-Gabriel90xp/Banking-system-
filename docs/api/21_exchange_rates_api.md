# API de Divisas

Fuente externa: Frankfurter (`https://api.frankfurter.dev`).

Base interna:

```text
/api/v1/exchange-rates
```

Todos los endpoints requieren JWT, igual que el resto de endpoints protegidos del sistema.

## Endpoints

| Metodo | Endpoint | Descripcion |
|---|---|---|
| GET | `/rates` | Lista tasas recientes, historicas o de serie temporal. |
| GET | `/rate/{base}/{quote}` | Obtiene una tasa para un par de monedas. |
| GET | `/convert` | Convierte un monto usando la tasa del par indicado. |
| GET | `/currencies` | Lista monedas disponibles. |
| GET | `/currencies/{code}` | Obtiene detalle de una moneda. |
| GET | `/providers` | Lista proveedores de tasas disponibles. |

## Parametros

### `GET /rates`

Parametros opcionales:

- `base`: moneda base ISO 4217, ejemplo `USD`.
- `quotes`: monedas destino separadas por coma, ejemplo `DOP,EUR,GBP`.
- `date`: fecha historica `YYYY-MM-DD`.
- `from`: inicio de serie temporal `YYYY-MM-DD`.
- `to`: fin de serie temporal `YYYY-MM-DD`.
- `group`: `week` o `month`.
- `providers`: proveedores separados por coma, ejemplo `ECB`.
- `includeProviders`: `true` para incluir atribucion de proveedores.

Ejemplo:

```http
GET /api/v1/exchange-rates/rates?base=USD&quotes=DOP,EUR
```

### `GET /rate/{base}/{quote}`

Parametros opcionales:

- `date`
- `providers`

Ejemplo:

```http
GET /api/v1/exchange-rates/rate/USD/DOP
```

### `GET /convert`

Parametros requeridos:

- `amount`
- `base`
- `quote`

Parametros opcionales:

- `date`
- `providers`

Ejemplo:

```http
GET /api/v1/exchange-rates/convert?amount=100&base=USD&quote=DOP
```

La conversion se calcula internamente como `amount * rate` y se redondea a 2 decimales.

### `GET /currencies`

Parametro opcional:

- `scope=all` para incluir monedas historicas/legacy.

### `GET /providers`

No recibe parametros.

## Configuracion

La URL base del proveedor se configura en:

```yaml
app:
  exchange-rates:
    frankfurter-base-url: ${FRANKFURTER_BASE_URL:https://api.frankfurter.dev}
```
