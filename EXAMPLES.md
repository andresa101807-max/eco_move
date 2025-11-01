# Ejemplos de Uso de la API EcoMove

Este documento contiene ejemplos de peticiones HTTP para probar todos los endpoints de la API.

## 🚴 Vehículos

### Crear una Bicicleta Eléctrica
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "E-Bike City",
    "model": "Urban 2024",
    "brand": "EcoRide",
    "type": "ELECTRIC_BIKE",
    "pricePerHour": 5000,
    "currentLocation": {
      "latitude": 5.0689,
      "longitude": -75.5174
    },
    "battery": {
      "capacity": 500,
      "currentCharge": 500,
      "chargeCycles": 0,
      "maxChargeCycles": 1000
    },
    "gears": 7,
    "hasLights": true,
    "weight": 22.5
  }'
```

### Crear un Scooter Eléctrico
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "E-Scooter Pro",
    "model": "Speed 2024",
    "brand": "EcoMove",
    "type": "ELECTRIC_SCOOTER",
    "pricePerHour": 4000,
    "currentLocation": {
      "latitude": 5.0689,
      "longitude": -75.5174
    },
    "battery": {
      "capacity": 350,
      "currentCharge": 350,
      "chargeCycles": 0,
      "maxChargeCycles": 800
    },
    "hasSuspension": true,
    "maxLoad": 120.0
  }'
```

### Crear un Auto Híbrido
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hybrid Sedan",
    "model": "Eco 2024",
    "brand": "GreenCars",
    "type": "HYBRID_CAR",
    "pricePerHour": 15000,
    "currentLocation": {
      "latitude": 5.0689,
      "longitude": -75.5174
    },
    "battery": {
      "capacity": 1500,
      "currentCharge": 1500,
      "chargeCycles": 0,
      "maxChargeCycles": 2000
    },
    "seats": 5,
    "fuelCapacity": 45.0,
    "currentFuel": 45.0,
    "hasAirConditioning": true
  }'
```

### Crear un Drone de Entrega
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Delivery Drone X1",
    "model": "Cargo 2024",
    "brand": "SkyMove",
    "type": "DELIVERY_DRONE",
    "pricePerHour": 20000,
    "currentLocation": {
      "latitude": 5.0689,
      "longitude": -75.5174
    },
    "battery": {
      "capacity": 200,
      "currentCharge": 200,
      "chargeCycles": 0,
      "maxChargeCycles": 500
    },
    "maxPayload": 5.0,
    "maxAltitude": 120.0
  }'
```

### Obtener Todos los Vehículos
```bash
curl -X GET http://localhost:8090/api/vehicles
```

### Obtener Vehículos Disponibles
```bash
curl -X GET http://localhost:8090/api/vehicles/available
```

### Actualizar Estado de Vehículo
```bash
curl -X PATCH "http://localhost:8090/api/vehicles/{vehicleId}/status?status=MAINTENANCE"
```

### Enviar Vehículo a Mantenimiento
```bash
curl -X POST http://localhost:8090/api/vehicles/{vehicleId}/maintenance
```

## 👤 Usuarios

### Crear Usuario Regular
```bash
curl -X POST http://localhost:8090/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "email": "juan.perez@example.com",
    "phoneNumber": "+57 300 1234567",
    "dateOfBirth": "1990-05-15",
    "documentNumber": "1234567890",
    "userType": "REGULAR",
    "balance": 50000,
    "isActive": true,
    "tripHistory": {
      "trips": []
    }
  }'
```

### Crear Usuario Premium
```bash
curl -X POST http://localhost:8090/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "María García",
    "email": "maria.garcia@example.com",
    "phoneNumber": "+57 310 9876543",
    "dateOfBirth": "1985-08-20",
    "documentNumber": "9876543210",
    "userType": "PREMIUM",
    "balance": 100000,
    "isActive": true,
    "tripHistory": {
      "trips": []
    }
  }'
```

### Obtener Todos los Usuarios
```bash
curl -X GET http://localhost:8090/api/users
```

### Agregar Saldo a Usuario
```bash
curl -X POST "http://localhost:8090/api/users/{userId}/balance/add?amount=50000"
```

### Deducir Saldo de Usuario
```bash
curl -X POST "http://localhost:8090/api/users/{userId}/balance/deduct?amount=10000"
```

## 🗺️ Rutas

### Crear Ruta
```bash
curl -X POST http://localhost:8090/api/routes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ruta Centro - Universidad",
    "description": "Ruta desde el centro de la ciudad hasta la universidad",
    "startPoint": {
      "latitude": 5.0689,
      "longitude": -75.5174
    },
    "endPoint": {
      "latitude": 5.0700,
      "longitude": -75.5100
    },
    "waypoints": [
      {
        "latitude": 5.0695,
        "longitude": -75.5150
      }
    ],
    "estimatedDistance": 2.5,
    "estimatedDuration": 15,
    "difficulty": "easy"
  }'
```

### Obtener Todas las Rutas
```bash
curl -X GET http://localhost:8090/api/routes
```

### Obtener Ruta por ID
```bash
curl -X GET http://localhost:8090/api/routes/{routeId}
```

## 📅 Reservas

### Crear Reserva
```bash
curl -X POST http://localhost:8090/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "{userId}",
    "vehicleId": "{vehicleId}",
    "startTime": "2024-11-01T10:00:00",
    "endTime": "2024-11-01T12:00:00",
    "estimatedCost": 10000
  }'
```

### Confirmar Reserva
```bash
curl -X POST http://localhost:8090/api/reservations/{reservationId}/confirm
```

### Iniciar Reserva
```bash
curl -X POST http://localhost:8090/api/reservations/{reservationId}/start
```

### Completar Reserva
```bash
curl -X POST "http://localhost:8090/api/reservations/{reservationId}/complete?cost=12000"
```

### Cancelar Reserva
```bash
curl -X POST http://localhost:8090/api/reservations/{reservationId}/cancel
```

### Obtener Todas las Reservas
```bash
curl -X GET http://localhost:8090/api/reservations
```

## 🔧 Mantenimientos

### Crear Mantenimiento Preventivo
```bash
curl -X POST http://localhost:8090/api/maintenances \
  -H "Content-Type: application/json" \
  -d '{
    "vehicleId": "{vehicleId}",
    "type": "PREVENTIVE",
    "scheduledDate": "2024-11-05T09:00:00",
    "description": "Revisión general y cambio de aceite",
    "technicianName": "Carlos Rodríguez"
  }'
```

### Crear Mantenimiento de Batería
```bash
curl -X POST http://localhost:8090/api/maintenances \
  -H "Content-Type: application/json" \
  -d '{
    "vehicleId": "{vehicleId}",
    "type": "BATTERY_REPLACEMENT",
    "scheduledDate": "2024-11-10T14:00:00",
    "description": "Reemplazo de batería por desgaste",
    "technicianName": "Ana Martínez"
  }'
```

### Completar Mantenimiento
```bash
curl -X POST "http://localhost:8090/api/maintenances/{maintenanceId}/complete?cost=150000"
```

### Reprogramar Mantenimiento
```bash
curl -X POST "http://localhost:8090/api/maintenances/{maintenanceId}/reschedule?newDate=2024-11-12T10:00:00"
```

### Obtener Todos los Mantenimientos
```bash
curl -X GET http://localhost:8090/api/maintenances
```

## 🔄 Flujo Completo de Uso

### 1. Crear un usuario
```bash
curl -X POST http://localhost:8090/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pedro López",
    "email": "pedro@example.com",
    "phoneNumber": "+57 320 5551234",
    "dateOfBirth": "1995-03-10",
    "documentNumber": "5551234567",
    "userType": "REGULAR",
    "balance": 100000,
    "isActive": true,
    "tripHistory": {"trips": []}
  }'
```

### 2. Crear un vehículo
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "E-Bike Express",
    "model": "Fast 2024",
    "brand": "SpeedBike",
    "type": "ELECTRIC_BIKE",
    "pricePerHour": 6000,
    "currentLocation": {"latitude": 5.0689, "longitude": -75.5174},
    "battery": {"capacity": 500, "currentCharge": 500, "chargeCycles": 0, "maxChargeCycles": 1000},
    "gears": 21,
    "hasLights": true,
    "weight": 18.5
  }'
```

### 3. Crear una reserva
```bash
curl -X POST http://localhost:8090/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "{userId-from-step-1}",
    "vehicleId": "{vehicleId-from-step-2}",
    "startTime": "2024-11-01T14:00:00",
    "endTime": "2024-11-01T16:00:00",
    "estimatedCost": 12000
  }'
```

### 4. Confirmar la reserva
```bash
curl -X POST http://localhost:8090/api/reservations/{reservationId}/confirm
```

### 5. Iniciar el viaje
```bash
curl -X POST http://localhost:8090/api/reservations/{reservationId}/start
```

### 6. Completar el viaje
```bash
curl -X POST "http://localhost:8090/api/reservations/{reservationId}/complete?cost=12000"
```

### 7. Deducir el costo del saldo del usuario
```bash
curl -X POST "http://localhost:8090/api/users/{userId}/balance/deduct?amount=12000"
```

## 📊 Consultas Útiles

### Ver estado de todos los vehículos
```bash
curl -X GET http://localhost:8090/api/vehicles | jq '.[] | {name: .name, status: .status, battery: .battery.chargePercentage}'
```

### Ver reservas activas
```bash
curl -X GET http://localhost:8090/api/reservations | jq '.[] | select(.status == "IN_PROGRESS")'
```

### Ver mantenimientos pendientes
```bash
curl -X GET http://localhost:8090/api/maintenances | jq '.[] | select(.isCompleted == false)'
```

## 🧪 Testing con Postman

Importa la siguiente colección en Postman para probar todos los endpoints:

1. Abre Postman
2. Importa > Raw Text
3. Pega el contenido de este archivo
4. Reemplaza `{userId}`, `{vehicleId}`, etc. con IDs reales

## 📝 Notas

- Todos los IDs se generan automáticamente como UUIDs
- Los datos se persisten en archivos CSV en la carpeta `data/`
- El servidor corre por defecto en el puerto 8090
- Las fechas deben estar en formato ISO-8601: `YYYY-MM-DDTHH:mm:ss`
