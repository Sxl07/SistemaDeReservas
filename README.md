# SistemaDeReservas | Reservation System

[Español](#español) | [English](#english)

---

## Español

## Descripción

**SistemaDeReservas** es una práctica académica full stack para la gestión de reservas. El proyecto está dividido en dos aplicaciones:

- **Backend:** API REST desarrollada con Spring Boot.
- **Frontend:** aplicación web desarrollada con Angular.

El sistema permite crear reservas, listar las reservas existentes y cancelar reservas. Además, implementa una regla de negocio para evitar que se creen dos reservas en la misma fecha y hora.

Este repositorio reemplaza el README mínimo anterior por una documentación más completa del proyecto, sus tecnologías, estructura, ejecución local y endpoints principales.

## Objetivo del proyecto

El objetivo principal fue construir una aplicación full stack sencilla aplicando conceptos de desarrollo backend y frontend, tales como:

- Creación de API REST con Spring Boot.
- Persistencia de datos con Spring Data JPA.
- Conexión con PostgreSQL.
- Validación de datos de entrada.
- Manejo global de errores.
- Separación por capas en backend.
- Consumo de API REST desde Angular.
- Formularios reactivos.
- Componentes standalone en Angular.
- Comunicación HTTP entre frontend y backend.
- Gestión de estados básicos en interfaz.

## Funcionalidades principales

### Backend

El backend permite:

- Listar todas las reservas.
- Crear una nueva reserva.
- Cancelar una reserva existente.
- Validar campos obligatorios.
- Evitar reservas duplicadas en la misma fecha y hora.
- Manejar errores de negocio y errores de validación.
- Exponer documentación de API con OpenAPI/Swagger.

### Frontend

El frontend permite:

- Visualizar una tabla de reservas.
- Crear una nueva reserva desde un formulario.
- Cancelar reservas activas.
- Mostrar estado de carga.
- Mostrar mensajes de error.
- Usar notificaciones tipo toast.
- Navegar entre la tabla de reservas y el formulario de creación.

## Tecnologías utilizadas

### Backend

- Java 25
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- Springdoc OpenAPI / Swagger UI

### Frontend

- Angular 21
- TypeScript
- Angular Router
- Angular Reactive Forms
- Angular HttpClient
- RxJS
- npm
- Vitest
- Prettier

## Estructura general del proyecto

```text
SistemaDeReservas/
├── README.md
├── reservationBackend/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/com/Sxl07/reservation/
│       │   │   ├── ReservationApplication.java
│       │   │   ├── controller/
│       │   │   │   └── ReservationController.java
│       │   │   ├── dto/
│       │   │   │   ├── ApiErrorResponse.java
│       │   │   │   ├── ReservationRequest.java
│       │   │   │   └── ReservationResponse.java
│       │   │   ├── entity/
│       │   │   │   ├── Reservation.java
│       │   │   │   └── ReservationStatus.java
│       │   │   ├── exception/
│       │   │   │   ├── ReservationBusinessException.java
│       │   │   │   ├── ReservationNotFoundException.java
│       │   │   │   └── RestExceptionHandler.java
│       │   │   ├── repository/
│       │   │   │   └── ReservationRepository.java
│       │   │   └── service/
│       │   │       └── ReservationService.java
│       │   └── resources/
│       │       └── application.properties
│       └── test/
└── reservationFrontend/
    ├── package.json
    ├── angular.json
    └── src/
        ├── app/
        │   ├── app.config.ts
        │   ├── app.routes.ts
        │   ├── app.html
        │   ├── components/
        │   │   ├── reservation-form/
        │   │   ├── reservation-table/
        │   │   └── toast-message/
        │   ├── models/
        │   │   ├── reservation.ts
        │   │   └── reservation-create-request.ts
        │   └── services/
        │       └── reservation.service.ts
        └── environments/
            ├── environment.ts
            └── environment.development.ts
```

## Backend

### Modelo principal

La entidad principal del sistema es `Reservation`.

Campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` | Identificador de la reserva |
| `clientName` | `String` | Nombre del cliente |
| `date` | `LocalDate` | Fecha de la reserva |
| `time` | `LocalTime` | Hora de la reserva |
| `service` | `String` | Servicio reservado |
| `status` | `ReservationStatus` | Estado de la reserva |

### Estados de reserva

El sistema maneja dos estados:

```text
ACTIVE
CANCELLED
```

Cuando una reserva se crea, queda automáticamente con estado `ACTIVE`.

Cuando se cancela, no se elimina físicamente de la base de datos; su estado cambia a `CANCELLED`.

### Reglas de negocio

El backend implementa las siguientes reglas:

- No se puede crear una reserva si ya existe otra reserva en la misma fecha y hora.
- No se puede cancelar una reserva que no existe.
- No se puede cancelar una reserva que ya está cancelada.
- El nombre del cliente es obligatorio.
- La fecha es obligatoria.
- La hora es obligatoria.
- El servicio es obligatorio.

### Endpoints principales

Base URL local:

```text
http://localhost:8080
```

#### Listar reservas

```http
GET /reservas
```

Respuesta esperada:

```json
[
  {
    "id": 1,
    "clientName": "Sebastian Lopez",
    "date": "2026-04-28",
    "time": "10:30:00",
    "service": "Laptop preventive maintenance",
    "status": "ACTIVE"
  }
]
```

#### Crear reserva

```http
POST /reservas
```

Body:

```json
{
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance"
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance",
  "status": "ACTIVE"
}
```

#### Cancelar reserva

```http
DELETE /reservas/{id}
```

Ejemplo:

```http
DELETE /reservas/1
```

Respuesta esperada:

```json
{
  "id": 1,
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance",
  "status": "CANCELLED"
}
```

### Manejo de errores

El backend cuenta con un manejador global de excepciones:

| Caso | Código HTTP | Respuesta |
|---|---:|---|
| Error de validación | `400 Bad Request` | Mensaje con los campos inválidos |
| Reserva no encontrada | `404 Not Found` | Mensaje indicando que la reserva no existe |
| Regla de negocio incumplida | `409 Conflict` | Mensaje de conflicto |

Ejemplo:

```json
{
  "message": "A reservation already exists for the selected date and time."
}
```

### Configuración de base de datos

El backend usa PostgreSQL. La configuración actual se encuentra en:

```text
reservationBackend/src/main/resources/application.properties
```

Configuración actual:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reservationdb
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Para otro entorno, cambia estos valores según tus credenciales locales.

## Frontend

### Rutas de la aplicación

| Ruta | Componente | Descripción |
|---|---|---|
| `/reservations` | `ReservationTableComponent` | Tabla de reservas |
| `/reservations/new` | `ReservationFormComponent` | Formulario para crear reserva |
| `/` | Redirect | Redirecciona a `/reservations` |
| `**` | Redirect | Redirecciona a `/reservations` |

### Servicios disponibles en el formulario

El formulario incluye opciones de servicios como:

- Laptop preventive maintenance
- Desktop cleaning and thermal paste replacement
- Operating system installation
- Hardware diagnostics and repair
- Data backup and recovery
- Virus and malware removal

### Conexión con backend

La URL del backend se define en:

```text
reservationFrontend/src/environments/environment.development.ts
```

Valor actual:

```ts
export const environment = {
  apiUrl: "http://localhost:8080"
};
```

El servicio Angular construye la URL de reservas así:

```ts
`${environment.apiUrl}/reservas`
```

## Instalación y ejecución local

### Requisitos previos

Debes tener instalado:

- Java 25 o una versión compatible con el proyecto.
- Maven, o usar el wrapper incluido `mvnw`.
- PostgreSQL.
- Node.js.
- npm.
- Angular CLI opcional.

## Ejecución del backend

### 1. Crear base de datos PostgreSQL

Crea una base de datos llamada:

```sql
reservationdb
```

Ejemplo desde PostgreSQL:

```sql
CREATE DATABASE reservationdb;
```

### 2. Configurar credenciales

Revisa el archivo:

```text
reservationBackend/src/main/resources/application.properties
```

Ajusta usuario y contraseña si tu configuración local es distinta.

### 3. Entrar a la carpeta del backend

```bash
cd reservationBackend
```

### 4. Ejecutar el backend

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

El backend quedará disponible en:

```text
http://localhost:8080
```

### 5. Swagger UI

Si la aplicación está ejecutándose correctamente, la documentación interactiva puede consultarse en:

```text
http://localhost:8080/swagger-ui.html
```

O, según la configuración de Springdoc:

```text
http://localhost:8080/swagger-ui/index.html
```

## Ejecución del frontend

### 1. Entrar a la carpeta del frontend

```bash
cd reservationFrontend
```

### 2. Instalar dependencias

```bash
npm install
```

### 3. Ejecutar la aplicación

```bash
npm start
```

También se puede usar:

```bash
ng serve
```

El frontend quedará disponible en:

```text
http://localhost:4200
```

## Flujo general de uso

1. Ejecutar PostgreSQL.
2. Ejecutar el backend Spring Boot.
3. Ejecutar el frontend Angular.
4. Abrir `http://localhost:4200`.
5. Crear una reserva desde el formulario.
6. Ver la reserva en la tabla.
7. Cancelar una reserva activa si es necesario.

## Scripts disponibles del frontend

| Comando | Descripción |
|---|---|
| `npm start` | Ejecuta Angular en modo desarrollo |
| `npm run build` | Compila la aplicación |
| `npm run watch` | Compila en modo observación |
| `npm test` | Ejecuta pruebas del frontend |

## Notas del proyecto

- El backend permite CORS desde `http://localhost:4200`.
- Las reservas se persisten en PostgreSQL.
- La cancelación se maneja como cambio de estado, no como eliminación física.
- El frontend usa formularios reactivos para crear reservas.
- El diseño incluye componentes separados para tabla, formulario y mensajes toast.
- Este proyecto corresponde a una práctica académica full stack.

## Posibles mejoras futuras

- Agregar edición de reservas.
- Agregar filtros por fecha, estado o cliente.
- Agregar autenticación de usuarios.
- Agregar paginación en la tabla.
- Agregar confirmación antes de cancelar una reserva.
- Mejorar mensajes de éxito al crear y cancelar reservas.
- Crear perfiles de configuración para desarrollo y producción.
- Mover credenciales de base de datos a variables de entorno.
- Agregar Docker Compose para PostgreSQL, backend y frontend.
- Agregar pruebas unitarias y de integración en backend.
- Corregir la prueba generada del frontend que aún espera el texto inicial de Angular.
- Crear despliegue en la nube.

---

## English

## Description

**SistemaDeReservas** is an academic full stack practice project for reservation management. The project is divided into two applications:

- **Backend:** REST API developed with Spring Boot.
- **Frontend:** web application developed with Angular.

The system allows users to create reservations, list existing reservations, and cancel reservations. It also implements a business rule to prevent two reservations from being created for the same date and time.

This repository replaces the previous minimal README with more complete documentation about the project, its technologies, structure, local execution, and main endpoints.

## Project goal

The main goal was to build a simple full stack application while applying backend and frontend development concepts such as:

- REST API creation with Spring Boot.
- Data persistence with Spring Data JPA.
- PostgreSQL connection.
- Input validation.
- Global error handling.
- Layered backend structure.
- REST API consumption from Angular.
- Reactive forms.
- Standalone Angular components.
- HTTP communication between frontend and backend.
- Basic UI state management.

## Main features

### Backend

The backend allows users to:

- List all reservations.
- Create a new reservation.
- Cancel an existing reservation.
- Validate required fields.
- Prevent duplicate reservations for the same date and time.
- Handle business and validation errors.
- Expose API documentation with OpenAPI/Swagger.

### Frontend

The frontend allows users to:

- View a reservation table.
- Create a new reservation from a form.
- Cancel active reservations.
- Display loading state.
- Display error messages.
- Use toast-style notifications.
- Navigate between the reservation table and the creation form.

## Technologies used

### Backend

- Java 25
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- Springdoc OpenAPI / Swagger UI

### Frontend

- Angular 21
- TypeScript
- Angular Router
- Angular Reactive Forms
- Angular HttpClient
- RxJS
- npm
- Vitest
- Prettier

## General project structure

```text
SistemaDeReservas/
├── README.md
├── reservationBackend/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/com/Sxl07/reservation/
│       │   │   ├── ReservationApplication.java
│       │   │   ├── controller/
│       │   │   │   └── ReservationController.java
│       │   │   ├── dto/
│       │   │   │   ├── ApiErrorResponse.java
│       │   │   │   ├── ReservationRequest.java
│       │   │   │   └── ReservationResponse.java
│       │   │   ├── entity/
│       │   │   │   ├── Reservation.java
│       │   │   │   └── ReservationStatus.java
│       │   │   ├── exception/
│       │   │   │   ├── ReservationBusinessException.java
│       │   │   │   ├── ReservationNotFoundException.java
│       │   │   │   └── RestExceptionHandler.java
│       │   │   ├── repository/
│       │   │   │   └── ReservationRepository.java
│       │   │   └── service/
│       │   │       └── ReservationService.java
│       │   └── resources/
│       │       └── application.properties
│       └── test/
└── reservationFrontend/
    ├── package.json
    ├── angular.json
    └── src/
        ├── app/
        │   ├── app.config.ts
        │   ├── app.routes.ts
        │   ├── app.html
        │   ├── components/
        │   │   ├── reservation-form/
        │   │   ├── reservation-table/
        │   │   └── toast-message/
        │   ├── models/
        │   │   ├── reservation.ts
        │   │   └── reservation-create-request.ts
        │   └── services/
        │       └── reservation.service.ts
        └── environments/
            ├── environment.ts
            └── environment.development.ts
```

## Backend

### Main model

The main entity of the system is `Reservation`.

Fields:

| Field | Type | Description |
|---|---|---|
| `id` | `Long` | Reservation identifier |
| `clientName` | `String` | Client name |
| `date` | `LocalDate` | Reservation date |
| `time` | `LocalTime` | Reservation time |
| `service` | `String` | Reserved service |
| `status` | `ReservationStatus` | Reservation status |

### Reservation statuses

The system handles two statuses:

```text
ACTIVE
CANCELLED
```

When a reservation is created, it is automatically stored with the `ACTIVE` status.

When it is canceled, it is not physically deleted from the database; its status changes to `CANCELLED`.

### Business rules

The backend implements the following rules:

- A reservation cannot be created if another reservation already exists for the same date and time.
- A reservation that does not exist cannot be canceled.
- A reservation that is already canceled cannot be canceled again.
- Client name is required.
- Date is required.
- Time is required.
- Service is required.

### Main endpoints

Local base URL:

```text
http://localhost:8080
```

#### List reservations

```http
GET /reservas
```

Expected response:

```json
[
  {
    "id": 1,
    "clientName": "Sebastian Lopez",
    "date": "2026-04-28",
    "time": "10:30:00",
    "service": "Laptop preventive maintenance",
    "status": "ACTIVE"
  }
]
```

#### Create reservation

```http
POST /reservas
```

Body:

```json
{
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance"
}
```

Expected response:

```json
{
  "id": 1,
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance",
  "status": "ACTIVE"
}
```

#### Cancel reservation

```http
DELETE /reservas/{id}
```

Example:

```http
DELETE /reservas/1
```

Expected response:

```json
{
  "id": 1,
  "clientName": "Sebastian Lopez",
  "date": "2026-04-28",
  "time": "10:30:00",
  "service": "Laptop preventive maintenance",
  "status": "CANCELLED"
}
```

### Error handling

The backend includes a global exception handler:

| Case | HTTP code | Response |
|---|---:|---|
| Validation error | `400 Bad Request` | Message with invalid fields |
| Reservation not found | `404 Not Found` | Message indicating the reservation does not exist |
| Business rule violation | `409 Conflict` | Conflict message |

Example:

```json
{
  "message": "A reservation already exists for the selected date and time."
}
```

### Database configuration

The backend uses PostgreSQL. The current configuration is located in:

```text
reservationBackend/src/main/resources/application.properties
```

Current configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reservationdb
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> For another environment, update these values according to your local credentials.

## Frontend

### Application routes

| Route | Component | Description |
|---|---|---|
| `/reservations` | `ReservationTableComponent` | Reservation table |
| `/reservations/new` | `ReservationFormComponent` | Form to create a reservation |
| `/` | Redirect | Redirects to `/reservations` |
| `**` | Redirect | Redirects to `/reservations` |

### Available services in the form

The form includes service options such as:

- Laptop preventive maintenance
- Desktop cleaning and thermal paste replacement
- Operating system installation
- Hardware diagnostics and repair
- Data backup and recovery
- Virus and malware removal

### Backend connection

The backend URL is defined in:

```text
reservationFrontend/src/environments/environment.development.ts
```

Current value:

```ts
export const environment = {
  apiUrl: "http://localhost:8080"
};
```

The Angular service builds the reservations URL as:

```ts
`${environment.apiUrl}/reservas`
```

## Local installation and execution

### Prerequisites

You need to have installed:

- Java 25 or a version compatible with the project.
- Maven, or use the included `mvnw` wrapper.
- PostgreSQL.
- Node.js.
- npm.
- Angular CLI optional.

## Running the backend

### 1. Create PostgreSQL database

Create a database named:

```sql
reservationdb
```

Example from PostgreSQL:

```sql
CREATE DATABASE reservationdb;
```

### 2. Configure credentials

Review the file:

```text
reservationBackend/src/main/resources/application.properties
```

Update username and password if your local configuration is different.

### 3. Enter the backend folder

```bash
cd reservationBackend
```

### 4. Run the backend

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend will be available at:

```text
http://localhost:8080
```

### 5. Swagger UI

If the application is running correctly, the interactive API documentation can be accessed at:

```text
http://localhost:8080/swagger-ui.html
```

Or, depending on the Springdoc configuration:

```text
http://localhost:8080/swagger-ui/index.html
```

## Running the frontend

### 1. Enter the frontend folder

```bash
cd reservationFrontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Run the application

```bash
npm start
```

You can also use:

```bash
ng serve
```

The frontend will be available at:

```text
http://localhost:4200
```

## General usage flow

1. Start PostgreSQL.
2. Run the Spring Boot backend.
3. Run the Angular frontend.
4. Open `http://localhost:4200`.
5. Create a reservation from the form.
6. View the reservation in the table.
7. Cancel an active reservation if needed.

## Frontend available scripts

| Command | Description |
|---|---|
| `npm start` | Runs Angular in development mode |
| `npm run build` | Builds the application |
| `npm run watch` | Builds in watch mode |
| `npm test` | Runs frontend tests |

## Project notes

- The backend allows CORS from `http://localhost:4200`.
- Reservations are persisted in PostgreSQL.
- Cancellation is handled as a status change, not as physical deletion.
- The frontend uses reactive forms to create reservations.
- The UI design includes separated components for table, form, and toast messages.
- This project corresponds to an academic full stack practice.

## Possible future improvements

- Add reservation editing.
- Add filters by date, status, or client.
- Add user authentication.
- Add pagination to the table.
- Add confirmation before canceling a reservation.
- Improve success messages when creating and canceling reservations.
- Create configuration profiles for development and production.
- Move database credentials to environment variables.
- Add Docker Compose for PostgreSQL, backend, and frontend.
- Add unit and integration tests in the backend.
- Fix the generated frontend test that still expects the initial Angular text.
- Create a cloud deployment workflow.
