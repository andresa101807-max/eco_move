# 📮 Guía Completa de Postman para EcoMove API

## 🎯 Configuración Inicial

### 1. Abrir Postman
- Si no tienes Postman instalado, descárgalo de: https://www.postman.com/downloads/
- Abre Postman

### 2. Crear una Nueva Colección
1. Click en "Collections" en el panel izquierdo
2. Click en el botón "+" o "Create Collection"
3. Nombra la colección: **"EcoMove API"**

### 3. Configurar Variables de Entorno
1. Click en "Environments" (ícono de ojo en la esquina superior derecha)
2. Click en "+" para crear un nuevo ambiente
3. Nombra el ambiente: **"EcoMove Local"**
4. Agrega las siguientes variables:

| Variable | Initial Value | Current Value |
|----------|--------------|---------------|
| `base_url` | `http://localhost:8090` | `http://localhost:8090` |
| `user_id` | (vacío) | (vacío) |
| `vehicle_id` | (vacío) | (vacío) |
| `reservation_id` | (vacío) | (vacío) |

5. Click en "Save"
6. Selecciona el ambiente "EcoMove Local" en el dropdown superior derecho

---

## 🧪 Pruebas Paso a Paso

### ✅ PASO 1: Crear un Usuario

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/users`
- **Headers**: 
  - Key: `Content-Type`, Value: `application/json`
- **Body** (selecciona "raw" y "JSON"):

```json
{
  "name": "Juan Pérez",
  "email": "juan.perez@example.com",
  "phoneNumber": "+57 300 1234567",
  "dateOfBirth": "1990-05-15",
  "documentNumber": "1234567890",
  "userType": "REGULAR",
  "balance": 100000,
  "isActive": true,
  "tripHistory": {
    "trips": []
  }
}
```

**Después de enviar:**
1. Deberías recibir un código `201 Created`
2. Copia el `id` de la respuesta
3. Ve a "Environments" → "EcoMove Local"
4. Pega el `id` en la variable `user_id`

---

### ✅ PASO 2: Crear una Bicicleta Eléctrica

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/vehicles`
- **Headers**: 
  - Key: `Content-Type`, Value: `application/json`
- **Body**:

```json
{
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
}
```

**Después de enviar:**
1. Copia el `id` de la respuesta
2. Guárdalo en la variable `vehicle_id`

---

### ✅ PASO 3: Ver Todos los Usuarios

**Configuración en Postman:**
- **Método**: `GET`
- **URL**: `{{base_url}}/api/users`
- **Headers**: Ninguno necesario

**Resultado esperado:**
- Código `200 OK`
- Array con todos los usuarios creados

---

### ✅ PASO 4: Ver Todos los Vehículos

**Configuración en Postman:**
- **Método**: `GET`
- **URL**: `{{base_url}}/api/vehicles`

**Resultado esperado:**
- Código `200 OK`
- Array con todos los vehículos

---

### ✅ PASO 5: Ver Vehículos Disponibles

**Configuración en Postman:**
- **Método**: `GET`
- **URL**: `{{base_url}}/api/vehicles/available`

**Resultado esperado:**
- Solo vehículos con status `AVAILABLE`

---

### ✅ PASO 6: Crear una Reserva

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/reservations`
- **Headers**: 
  - Key: `Content-Type`, Value: `application/json`
- **Body**:

```json
{
  "userId": "{{user_id}}",
  "vehicleId": "{{vehicle_id}}",
  "startTime": "2024-11-01T10:00:00",
  "endTime": "2024-11-01T12:00:00",
  "estimatedCost": 10000
}
```

**Después de enviar:**
1. Copia el `id` de la respuesta
2. Guárdalo en la variable `reservation_id`

---

### ✅ PASO 7: Confirmar la Reserva

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/reservations/{{reservation_id}}/confirm`

**Resultado esperado:**
- Status de la reserva cambia a `CONFIRMED`

---

### ✅ PASO 8: Iniciar el Viaje

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/reservations/{{reservation_id}}/start`

**Resultado esperado:**
- Status de la reserva cambia a `IN_PROGRESS`
- El vehículo cambia a status `IN_USE`

---

### ✅ PASO 9: Completar el Viaje

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/reservations/{{reservation_id}}/complete?cost=12000`
- **Params**:
  - Key: `cost`, Value: `12000`

**Resultado esperado:**
- Status de la reserva cambia a `COMPLETED`
- El vehículo vuelve a status `AVAILABLE`

---

### ✅ PASO 10: Agregar Saldo al Usuario

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/users/{{user_id}}/balance/add?amount=50000`
- **Params**:
  - Key: `amount`, Value: `50000`

**Resultado esperado:**
- El balance del usuario aumenta

---

### ✅ PASO 11: Deducir Saldo del Usuario

**Configuración en Postman:**
- **Método**: `POST`
- **URL**: `{{base_url}}/api/users/{{user_id}}/balance/deduct?amount=12000`
- **Params**:
  - Key: `amount`, Value: `12000`

**Resultado esperado:**
- El balance del usuario disminuye

---

## 🚗 Crear Otros Tipos de Vehículos

### Scooter Eléctrico

```json
{
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
}
```

### Auto Híbrido

```json
{
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
}
```

### Drone de Entrega

```json
{
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
}
```

---

## 🗺️ Crear una Ruta

**Método**: `POST`  
**URL**: `{{base_url}}/api/routes`

```json
{
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
}
```

---

## 🔧 Crear un Mantenimiento

**Método**: `POST`  
**URL**: `{{base_url}}/api/maintenances`

```json
{
  "vehicleId": "{{vehicle_id}}",
  "type": "PREVENTIVE",
  "scheduledDate": "2024-11-05T09:00:00",
  "description": "Revisión general y cambio de aceite",
  "technicianName": "Carlos Rodríguez",
  "cost": 0,
  "isCompleted": false
}
```

---

## 📋 Lista Completa de Endpoints

### Usuarios
- `GET /api/users` - Obtener todos
- `GET /api/users/{id}` - Obtener por ID
- `POST /api/users` - Crear
- `PUT /api/users/{id}` - Actualizar
- `DELETE /api/users/{id}` - Eliminar
- `POST /api/users/{id}/balance/add?amount={amount}` - Agregar saldo
- `POST /api/users/{id}/balance/deduct?amount={amount}` - Deducir saldo

### Vehículos
- `GET /api/vehicles` - Obtener todos
- `GET /api/vehicles/{id}` - Obtener por ID
- `GET /api/vehicles/available` - Obtener disponibles
- `POST /api/vehicles` - Crear
- `PUT /api/vehicles/{id}` - Actualizar
- `DELETE /api/vehicles/{id}` - Eliminar
- `PATCH /api/vehicles/{id}/status?status={status}` - Actualizar estado
- `POST /api/vehicles/{id}/maintenance` - Enviar a mantenimiento

### Rutas
- `GET /api/routes` - Obtener todas
- `GET /api/routes/{id}` - Obtener por ID
- `POST /api/routes` - Crear
- `PUT /api/routes/{id}` - Actualizar
- `DELETE /api/routes/{id}` - Eliminar

### Reservas
- `GET /api/reservations` - Obtener todas
- `GET /api/reservations/{id}` - Obtener por ID
- `POST /api/reservations` - Crear
- `PUT /api/reservations/{id}` - Actualizar
- `DELETE /api/reservations/{id}` - Eliminar
- `POST /api/reservations/{id}/confirm` - Confirmar
- `POST /api/reservations/{id}/start` - Iniciar
- `POST /api/reservations/{id}/complete?cost={cost}` - Completar
- `POST /api/reservations/{id}/cancel` - Cancelar

### Mantenimientos
- `GET /api/maintenances` - Obtener todos
- `GET /api/maintenances/{id}` - Obtener por ID
- `POST /api/maintenances` - Crear
- `PUT /api/maintenances/{id}` - Actualizar
- `DELETE /api/maintenances/{id}` - Eliminar
- `POST /api/maintenances/{id}/complete?cost={cost}` - Completar
- `POST /api/maintenances/{id}/reschedule?newDate={date}` - Reprogramar

---

## 💡 Tips para Postman

### 1. Guardar Requests
Después de configurar cada request, haz click en "Save" y guárdalo en la colección "EcoMove API".

### 2. Organizar en Carpetas
Crea carpetas dentro de la colección:
- 📁 Users
- 📁 Vehicles
- 📁 Routes
- 📁 Reservations
- 📁 Maintenances

### 3. Usar Tests para Guardar Variables Automáticamente
En la pestaña "Tests" de un request POST, agrega:

```javascript
// Para guardar el ID del usuario automáticamente
if (pm.response.code === 201) {
    var jsonData = pm.response.json();
    pm.environment.set("user_id", jsonData.id);
}
```

### 4. Verificar Respuestas
En la pestaña "Tests", puedes agregar validaciones:

```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has id", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('id');
});
```

---

## 🐛 Solución de Problemas

### Error: "Connection refused"
- Verifica que la aplicación esté corriendo: `./mvnw spring-boot:run`
- Verifica el puerto en la consola (debe ser 8090)

### Error: "404 Not Found"
- Verifica la URL: debe ser `http://localhost:8090/api/...`
- Verifica que el endpoint esté correcto

### Error: "400 Bad Request"
- Verifica que el JSON esté bien formado
- Verifica que todos los campos requeridos estén presentes
- Verifica el formato de las fechas: `YYYY-MM-DDTHH:mm:ss`

### Error: "500 Internal Server Error"
- Revisa los logs de la aplicación en la consola
- Verifica que los IDs existan antes de usarlos

---

## 📥 Importar Colección de Postman

Si prefieres importar una colección completa, crea un archivo `EcoMove.postman_collection.json` con todos los requests configurados.

---

## ✅ Checklist de Pruebas

- [ ] Crear usuario
- [ ] Obtener todos los usuarios
- [ ] Crear bicicleta eléctrica
- [ ] Crear scooter eléctrico
- [ ] Crear auto híbrido
- [ ] Crear drone
- [ ] Ver vehículos disponibles
- [ ] Crear ruta
- [ ] Crear reserva
- [ ] Confirmar reserva
- [ ] Iniciar reserva
- [ ] Completar reserva
- [ ] Agregar saldo a usuario
- [ ] Deducir saldo de usuario
- [ ] Crear mantenimiento
- [ ] Completar mantenimiento
- [ ] Actualizar estado de vehículo
- [ ] Eliminar usuario
- [ ] Eliminar vehículo

---

**¡Listo para probar! 🚀**
