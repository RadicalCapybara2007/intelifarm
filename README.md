# InteliFarm

API REST para registrar y consultar parcelas de huertos urbanos. El proyecto usa Java 21, Spring Boot, Spring Data JPA y PostgreSQL.

## Requisitos

- JDK 21.
- PostgreSQL activo y una base de datos llamada `crop`.
- Postman para enviar las solicitudes HTTP.

## Configuración local

Spring Boot lee la configuración desde `src/main/resources/application.properties`. Ese archivo está excluido de Git para que las credenciales locales no se publiquen. Configura allí la URL y las credenciales de tu PostgreSQL o usa las variables de entorno `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.

La URL local debe apuntar al host, puerto y nombre de base correctos; por ejemplo, la base creada para este proyecto es `crop` en el puerto `5432`. No guardes contraseñas en este README ni en archivos que vayas a subir al repositorio.

Hibernate está configurado actualmente con `spring.jpa.hibernate.ddl-auto=update`, por lo que puede crear o actualizar las tablas al iniciar la aplicación. Para comprobar la configuración de PostgreSQL desde `psql`:

```sql
SELECT current_database();
SELECT current_user;
SHOW port;
\d parcels
```

## Ejecutar el proyecto

Desde la raíz del repositorio:

```bash
./mvnw test
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`. Mantén el proceso de Spring Boot ejecutándose mientras pruebas en Postman.

## Probar con Postman

Puedes crear un environment llamado `InteliFarm local` y definir la variable:

| Variable | Valor inicial |
| --- | --- |
| `baseUrl` | `http://localhost:8080` |

Para solicitudes con JSON, selecciona **Body > raw > JSON**. Postman debe enviar `Content-Type: application/json`.

### Registrar una parcela

- Método: `POST`
- URL: `{{baseUrl}}/api/v1/parcels`
- Body:

```json
{
	"name": "Huerto Comunitario Norte",
	"location": "19.4326, -99.1332",
	"soilType": "arcilloso",
	"ownerOrLead": "Ana Pérez"
}
```

Respuesta esperada: `201 Created`. El servidor genera `id` y `createdAt`; no los envíes en el body.

```json
{
	"id": 1,
	"name": "Huerto Comunitario Norte",
	"location": "19.4326, -99.1332",
	"soilType": "ARCILLOSO",
	"ownerOrLead": "Ana Pérez",
	"createdAt": "2026-10-14T23:00:00Z"
}
```

Guarda el `id` que devuelva tu respuesta; los valores de ejemplo pueden ser distintos.

### Listar parcelas

- Método: `GET`
- URL: `{{baseUrl}}/api/v1/parcels`
- Respuesta esperada: `200 OK` y un arreglo JSON. Si aún no hay registros, el arreglo estará vacío (`[]`).

### Consultar una parcela por ID

- Método: `GET`
- URL: `{{baseUrl}}/api/v1/parcels/1`
- Reemplaza `1` por el `id` que devolvió el `POST`.
- Respuesta esperada: `200 OK` con la parcela solicitada.

## Probar validaciones y errores

### Datos inválidos

Envía un `POST` con un nombre vacío, coordenadas fuera de rango o un tipo de suelo diferente de `arcilloso`, `limoso` o `arenoso`. La respuesta esperada es `400 Bad Request`, con mensajes asociados a los campos inválidos:

```json
{
	"status": 400,
	"error": "Error de Validación",
	"messages": {
		"name": "El nombre de la parcela es obligatorio"
	}
}
```

Los setters recortan espacios al inicio y al final, y normalizan `soilType` a minúsculas antes de validar. La ubicación debe tener el formato `latitud, longitud`, y ambos valores deben estar dentro de los rangos GPS válidos.

### Nombre duplicado

Envía dos veces el mismo `POST` con el mismo nombre. La segunda solicitud debe responder `409 Conflict`.

### ID inexistente

Solicita un ID que no exista, por ejemplo `GET {{baseUrl}}/api/v1/parcels/999999`. La respuesta esperada es `404 Not Found`.

Los errores usan un cuerpo JSON con timestamp, estado y mensaje. La respuesta de validación incluye además el mapa `messages` por campo.

## Endpoints disponibles

| Método | Ruta | Resultado |
| --- | --- | --- |
| `POST` | `/api/v1/parcels` | Registra una parcela |
| `GET` | `/api/v1/parcels` | Lista las parcelas |
| `GET` | `/api/v1/parcels/{id}` | Consulta una parcela |

En esta versión no están implementados endpoints `PUT`, `PATCH` ni `DELETE`.
