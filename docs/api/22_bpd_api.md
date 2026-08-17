# API Banco Popular Dominicano

Fuente externa: Banco Popular Dominicano en IBM API Connect.

Base interna:

```text
/api/v1/bpd
```

Todos los endpoints internos requieren JWT. El frontend nunca debe enviar `Client Secret`; las credenciales BPD viven solo en backend.

## APIs integradas

| API externa | Version | Endpoint interno |
|---|---:|---|
| BPDConfirmarCuenta | 2.5.1 | `POST /confirm-account` |
| BPDUbicacionesATM | 2.2.1 | `GET /atm-locations` |

## Configuracion

Variables soportadas:

```yaml
app:
  bpd:
    base-url: ${BPD_API_BASE_URL:https://api.us-east-a.apiconnect.ibmappdomain.cloud}
    confirm-account-path: ${BPD_CONFIRM_ACCOUNT_PATH:/apiportalpopular/bpdsandbox/bpdconfirmarcuenta}
    atm-locations-path: ${BPD_ATM_LOCATIONS_PATH:/apiportalpopular/bpdsandbox/ubicacionesatm}
    token-url: ${BPD_TOKEN_URL:https://api.us-east-a.apiconnect.ibmappdomain.cloud/apiportalpopular/bpdsandbox/bpd/Authentication/oauth2/token}
    client-id: ${BPD_CLIENT_ID:}
    client-secret: ${BPD_CLIENT_SECRET:}
    scope: ${BPD_OAUTH_SCOPE:scope_1}
```

Si `BPD_CLIENT_ID` o `BPD_CLIENT_SECRET` no estan configurados, el backend responde `503 Service Unavailable`.

## Confirmar Cuenta

```http
POST /api/v1/bpd/confirm-account
Content-Type: application/json
Authorization: Bearer <jwt>
```

Body interno:

```json
{
  "documentType": "CEDULA",
  "documentNumber": "131416179",
  "accountNumber": "765676929"
}
```

El backend transforma ese body al contrato externo:

```json
{
  "ConfirmarCuentaReq": {
    "documentType": "CEDULA",
    "documentNumber": "131416179",
    "accountNumber": "765676929"
  }
}
```

Respuesta:

```json
{
  "status": true,
  "message": "..."
}
```

## Ubicaciones ATM

```http
GET /api/v1/bpd/atm-locations?page=0
Authorization: Bearer <jwt>
```

El parametro `page` acepta valores de `0` a `99` y se envia al proveedor como `Page`.

Respuesta interna:

```json
[
  {
    "id": "562",
    "name": "SANUT DOMINICANA PRUEBA",
    "category": "ATM",
    "address": "Km. 10 1, Autopista Duarte",
    "schedule": "L-V: 8am-9pm; S: 8am-9pm; D: 8am-3pm",
    "latitude": 18.4903118,
    "longitude": -69.9816734
  }
]
```

## OAuth externo

El backend solicita token con `grant_type=client_credentials`, `client_id`, `client_secret` y `scope`, usando `application/x-www-form-urlencoded`. El token se cachea temporalmente para evitar pedirlo en cada llamada.
