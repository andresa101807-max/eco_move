# EcoMove - Plataforma Inteligente de Movilidad Sostenible

API REST desarrollada con Spring Boot para gestionar un ecosistema de movilidad sostenible.

## 🚀 Características

- **CRUD completo** para 5 entidades principales: Vehículos, Usuarios, Rutas, Reservas y Mantenimientos
- **Persistencia en CSV** para almacenamiento de datos
- **Arquitectura MVC** con separación clara de responsabilidades
- **Principios SOLID, KISS y DRY** aplicados en todo el código
- **Herencia y Polimorfismo** en la jerarquía de vehículos
- **Interfaces** para comportamientos comunes (Rechargeable, Autonomous, IoTEnabled)
- **Composición y Agregación** (Battery, TripHistory)
- **Enumeraciones** para estados (VehicleStatus, ReservationStatus, etc.)
- **Records** para objetos inmutables (Coordinates, UsageStatistics)

## 📋 Requisitos

- Java 17+
- Maven 3.6+
- Spring Boot 3.5.6

## 🛠️ Instalación

1. Clonar el repositorio
2. Ejecutar Maven para descargar dependencias:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📚 Estructura del Proyecto

```
eco_move/
├── src/main/java/co/edu/umanizales/eco_move/
│   ├── controller/          # Controladores REST
│   │   ├── UserController.java
│   │   ├── VehicleController.java
│   │   ├── RouteController.java
│   │   ├── ReservationController.java
│   │   └── MaintenanceController.java
│   ├── service/             # Lógica de negocio
│   │   ├── UserService.java
│   │   ├── VehicleService.java
│   │   ├── RouteService.java
│   │   ├── ReservationService.java
│   │   └── MaintenanceService.java
│   ├── repository/          # Persistencia CSV
│   │   ├── CsvRepository.java
│   │   ├── UserCsvRepository.java
│   │   ├── VehicleCsvRepository.java
│   │   ├── RouteCsvRepository.java
│   │   ├── ReservationCsvRepository.java
│   │   └── MaintenanceCsvRepository.java
│   ├── model/               # Modelos de dominio
│   │   ├── Vehicle.java (abstracta)
│   │   ├── ElectricBike.java
│   │   ├── ElectricScooter.java
│   │   ├── HybridCar.java
│   │   ├── DeliveryDrone.java
│   │   ├── User.java
│   │   ├── Route.java
│   │   ├── Reservation.java
│   │   ├── Maintenance.java
│   │   ├── Battery.java
│   │   ├── TripHistory.java
│   │   ├── enums/
│   │   │   ├── VehicleStatus.java
│   │   │   ├── VehicleType.java
│   │   │   ├── UserType.java
│   │   │   ├── ReservationStatus.java
│   │   │   └── MaintenanceType.java
│   │   ├── records/
│   │   │   ├── Coordinates.java
│   │   │   └── UsageStatistics.java
│   │   └── interfaces/
│   │       ├── Rechargeable.java
│   │       ├── Autonomous.java
│   │       └── IoTEnabled.java
│   └── EcoMoveApplication.java
└── data/                    # Archivos CSV (generados automáticamente)
    ├── users.csv
    ├── vehicles.csv
    ├── routes.csv
    ├── reservations.csv
    └── maintenances.csv
```

## 🔌 API Endpoints

### Usuarios (`/api/users`)
- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `POST /api/users` - Crear nuevo usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario
- `POST /api/users/{id}/balance/add?amount={amount}` - Agregar saldo
- `POST /api/users/{id}/balance/deduct?amount={amount}` - Deducir saldo

### Vehículos (`/api/vehicles`)
- `GET /api/vehicles` - Obtener todos los vehículos
- `GET /api/vehicles/{id}` - Obtener vehículo por ID
- `GET /api/vehicles/available` - Obtener vehículos disponibles
- `POST /api/vehicles` - Crear nuevo vehículo
- `PUT /api/vehicles/{id}` - Actualizar vehículo
- `DELETE /api/vehicles/{id}` - Eliminar vehículo
- `PATCH /api/vehicles/{id}/status?status={status}` - Actualizar estado
- `POST /api/vehicles/{id}/maintenance` - Enviar a mantenimiento

### Rutas (`/api/routes`)
- `GET /api/routes` - Obtener todas las rutas
- `GET /api/routes/{id}` - Obtener ruta por ID
- `POST /api/routes` - Crear nueva ruta
- `PUT /api/routes/{id}` - Actualizar ruta
- `DELETE /api/routes/{id}` - Eliminar ruta

### Reservas (`/api/reservations`)
- `GET /api/reservations` - Obtener todas las reservas
- `GET /api/reservations/{id}` - Obtener reserva por ID
- `POST /api/reservations` - Crear nueva reserva
- `PUT /api/reservations/{id}` - Actualizar reserva
- `DELETE /api/reservations/{id}` - Eliminar reserva
- `POST /api/reservations/{id}/confirm` - Confirmar reserva
- `POST /api/reservations/{id}/start` - Iniciar reserva
- `POST /api/reservations/{id}/complete?cost={cost}` - Completar reserva
- `POST /api/reservations/{id}/cancel` - Cancelar reserva

### Mantenimientos (`/api/maintenances`)
- `GET /api/maintenances` - Obtener todos los mantenimientos
- `GET /api/maintenances/{id}` - Obtener mantenimiento por ID
- `POST /api/maintenances` - Crear nuevo mantenimiento
- `PUT /api/maintenances/{id}` - Actualizar mantenimiento
- `DELETE /api/maintenances/{id}` - Eliminar mantenimiento
- `POST /api/maintenances/{id}/complete?cost={cost}` - Completar mantenimiento
- `POST /api/maintenances/{id}/reschedule?newDate={date}` - Reprogramar mantenimiento

## 🏗️ Principios de Diseño Aplicados

### SOLID
- **S**ingle Responsibility: Cada clase tiene una única responsabilidad
- **O**pen/Closed: Extensible mediante herencia (Vehicle)
- **L**iskov Substitution: Subclases de Vehicle son intercambiables
- **I**nterface Segregation: Interfaces específicas (Rechargeable, Autonomous, IoTEnabled)
- **D**ependency Inversion: Inyección de dependencias en servicios y controladores

### KISS (Keep It Simple, Stupid)
- Código claro y fácil de entender
- Métodos cortos y concisos
- Nombres descriptivos

### DRY (Don't Repeat Yourself)
- Reutilización de código mediante herencia e interfaces
- Repositorio genérico `CsvRepository<T>`
- Métodos helper para conversión CSV

## 🎯 Conceptos de POO Implementados

### Herencia
- Clase abstracta `Vehicle` con subclases concretas:
  - `ElectricBike`
  - `ElectricScooter`
  - `HybridCar`
  - `DeliveryDrone`

### Polimorfismo
- Método abstracto `calculateRentalCost()` implementado de forma diferente en cada tipo de vehículo
- Métodos `getMaxSpeed()` y `getRange()` con comportamiento específico

### Interfaces
- `Rechargeable`: Para vehículos que se pueden recargar
- `Autonomous`: Para vehículos autónomos
- `IoTEnabled`: Para vehículos con sensores IoT

### Composición
- `Vehicle` contiene un objeto `Battery`
- `User` contiene un objeto `TripHistory`

### Agregación
- `Route` contiene una lista de `Coordinates` (waypoints)
- `TripHistory` contiene una lista de `Trip`

### Enumeraciones
- `VehicleStatus`: AVAILABLE, IN_USE, MAINTENANCE, RESERVED, UNAVAILABLE
- `VehicleType`: ELECTRIC_BIKE, ELECTRIC_SCOOTER, HYBRID_CAR, DELIVERY_DRONE
- `UserType`: REGULAR, PREMIUM, CORPORATE, STUDENT
- `ReservationStatus`: PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
- `MaintenanceType`: PREVENTIVE, CORRECTIVE, BATTERY_REPLACEMENT, etc.

### Records (Java 14+)
- `Coordinates`: Objeto inmutable para coordenadas geográficas
- `UsageStatistics`: Objeto inmutable para estadísticas de uso

## 📝 Ejemplos de Uso

### Crear un Usuario
```json
POST /api/users
{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phoneNumber": "+57 300 1234567",
  "dateOfBirth": "1990-05-15",
  "documentNumber": "1234567890",
  "userType": "REGULAR"
}
```

### Crear una Bicicleta Eléctrica
```json
POST /api/vehicles
{
  "name": "E-Bike City",
  "model": "Urban 2024",
  "brand": "EcoRide",
  "type": "ELECTRIC_BIKE",
  "pricePerHour": 5000,
  "gears": 7,
  "hasLights": true,
  "weight": 22.5
}
```

### Crear una Reserva
```json
POST /api/reservations
{
  "userId": "user-id-here",
  "vehicleId": "vehicle-id-here",
  "startTime": "2024-11-01T10:00:00",
  "endTime": "2024-11-01T12:00:00"
}
```

## 🧪 Testing

Para ejecutar las pruebas:
```bash
mvn test
```

## 📄 Licencia

Este proyecto es parte de un ejercicio académico para la Universidad de Manizales.

## 👥 Autor

Desarrollado como parte del curso de Programación Orientada a Objetos.
