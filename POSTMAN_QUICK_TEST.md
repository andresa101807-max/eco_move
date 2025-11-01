# 🚀 Prueba Rápida en Postman - 5 Minutos

## ✅ La aplicación YA está corriendo en: `http://localhost:8090`

---

## 📝 Prueba en 3 Pasos

### 🔵 PASO 1: Crear un Usuario (2 minutos)

**En Postman:**
1. Crea un nuevo request
3. URL: `http://localhost:8090/api/users`
4. Ve a la pestaña **Headers** y agrega:
   - Key: `Content-Type`
   - Value: `application/json`
5. Ve a la pestaña **Body**:
   - Selecciona **raw**
   - Selecciona **JSON** en el dropdown
6. Pega este JSON:

```json
{
  "name": "María García",
  "email": "maria@example.com",
  "phoneNumber": "+57 310 5551234",
  "dateOfBirth": "1995-03-10",
  "documentNumber": "9876543210",
  "userType": "PREMIUM",
  "balance": 150000,
  "isActive": true,
  "tripHistory": {
    "trips": []
  }
}
```

7. Click en **Send**
8. ✅ Deberías ver respuesta **201 Created** con el usuario creado
9. **IMPORTANTE**: Copia el `id` que aparece en la respuesta (lo necesitarás después)

---

### 🚴 PASO 2: Crear una Bicicleta (2 minutos)

**En Postman:**
1. Crea otro request nuevo
2. Método: **POST**
3. URL: `http://localhost:8090/api/vehicles`
4. Headers: `Content-Type: application/json`
5. Body (raw, JSON):

```json
{
  "name": "E-Bike Express",
  "model": "Fast 2024",
  "brand": "SpeedBike",
  "type": "ELECTRIC_BIKE",
  "pricePerHour": 6000,
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
  "gears": 21,
  "hasLights": true,
  "weight": 18.5
}
```

6. Click en **Send**
7. ✅ Respuesta **201 Created**
8. **IMPORTANTE**: Copia el `id` del vehículo

---

### 📋 PASO 3: Ver Todos los Datos (1 minuto)

**Ver Usuarios:**
- Método: **GET**
- URL: `http://localhost:8090/api/users`
- Click **Send**
- ✅ Verás el array con tu usuario

**Ver Vehículos:**
- Método: **GET**
- URL: `http://localhost:8090/api/vehicles`
- Click **Send**
- ✅ Verás el array con tu bicicleta

**Ver Vehículos Disponibles:**
- Método: **GET**
- URL: `http://localhost:8090/api/vehicles/available`
- Click **Send**
- ✅ Solo vehículos con status AVAILABLE

---

## 🎯 Prueba Completa: Flujo de Reserva

### 1. Crear Reserva
- **POST** `http://localhost:8090/api/reservations`
- Body:
```json
{
  "userId": "PEGA-AQUI-EL-ID-DEL-USUARIO",
  "vehicleId": "PEGA-AQUI-EL-ID-DEL-VEHICULO",
  "startTime": "2024-11-01T14:00:00",
  "endTime": "2024-11-01T16:00:00",
  "estimatedCost": 12000
}
```
- Copia el `id` de la reserva

### 2. Confirmar Reserva
- **POST** `http://localhost:8090/api/reservations/{RESERVATION_ID}/confirm`
- Reemplaza `{RESERVATION_ID}` con el ID que copiaste

### 3. Iniciar Viaje
- **POST** `http://localhost:8090/api/reservations/{RESERVATION_ID}/start`

### 4. Completar Viaje
- **POST** `http://localhost:8090/api/reservations/{RESERVATION_ID}/complete?cost=12000`

### 5. Ver Reservas
- **GET** `http://localhost:8090/api/reservations`

---

## 🎨 Crear Otros Vehículos

### Scooter Eléctrico
**POST** `http://localhost:8090/api/vehicles`
```json
{
  "name": "E-Scooter Pro",
  "model": "Speed 2024",
  "brand": "EcoMove",
  "type": "ELECTRIC_SCOOTER",
  "pricePerHour": 4000,
  "currentLocation": {"latitude": 5.0689, "longitude": -75.5174},
  "battery": {"capacity": 350, "currentCharge": 350, "chargeCycles": 0, "maxChargeCycles": 800},
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
  "currentLocation": {"latitude": 5.0689, "longitude": -75.5174},
  "battery": {"capacity": 1500, "currentCharge": 1500, "chargeCycles": 0, "maxChargeCycles": 2000},
  "seats": 5,
  "fuelCapacity": 45.0,
  "currentFuel": 45.0,
  "hasAirConditioning": true
}
```

### Drone
```json
{
  "name": "Delivery Drone X1",
  "model": "Cargo 2024",
  "brand": "SkyMove",
  "type": "DELIVERY_DRONE",
  "pricePerHour": 20000,
  "currentLocation": {"latitude": 5.0689, "longitude": -75.5174},
  "battery": {"capacity": 200, "currentCharge": 200, "chargeCycles": 0, "maxChargeCycles": 500},
  "maxPayload": 5.0,
  "maxAltitude": 120.0
}
```

---

## 📊 Endpoints Más Usados

| Acción | Método | URL |
|--------|--------|-----|
| Ver usuarios | GET | `http://localhost:8090/api/users` |
| Crear usuario | POST | `http://localhost:8090/api/users` |
| Ver vehículos | GET | `http://localhost:8090/api/vehicles` |
| Crear vehículo | POST | `http://localhost:8090/api/vehicles` |
| Ver disponibles | GET | `http://localhost:8090/api/vehicles/available` |
| Ver reservas | GET | `http://localhost:8090/api/reservations` |
| Crear reserva | POST | `http://localhost:8090/api/reservations` |
| Ver rutas | GET | `http://localhost:8090/api/routes` |
| Ver mantenimientos | GET | `http://localhost:8090/api/maintenances` |

---

## 💡 Tips Rápidos

### Para Headers
Siempre que hagas POST o PUT, agrega:
- `Content-Type: application/json`

### Para Fechas
Usa formato: `2024-11-01T14:00:00`

### Para IDs
Copia y pega los IDs de las respuestas, son UUIDs largos como:
`a1b2c3d4-e5f6-7890-abcd-ef1234567890`

### Estados de Vehículos
- `AVAILABLE` - Disponible
- `IN_USE` - En uso
- `MAINTENANCE` - En mantenimiento
- `RESERVED` - Reservado

### Tipos de Usuario
- `REGULAR` - Normal
- `PREMIUM` - Premium
- `CORPORATE` - Corporativo
- `STUDENT` - Estudiante

---

## 🐛 Si Algo Sale Mal

### Error 404
- Verifica la URL: `http://localhost:8090/api/...`
- Verifica que no falte `/api/` en la URL

### Error 400
- Verifica que el JSON esté bien escrito (comas, llaves)
- Verifica que las fechas tengan el formato correcto
- Verifica que todos los campos requeridos estén presentes

### Error 500
- Verifica que los IDs que usas existan
- Revisa la consola de la aplicación para ver el error

### No se conecta
- Verifica que la aplicación esté corriendo
- Abre http://localhost:8090/api/users en el navegador
- Si no funciona, ejecuta: `./mvnw spring-boot:run`

---

## 📁 Archivos CSV

Los datos se guardan automáticamente en:
- `data/users.csv`
- `data/vehicles.csv`
- `data/reservations.csv`
- `data/routes.csv`
- `data/maintenances.csv`

Puedes abrirlos con Excel o cualquier editor de texto.

---

## ✅ Checklist de Prueba Rápida

- [ ] Crear usuario ✓
- [ ] Ver usuarios ✓
- [ ] Crear bicicleta ✓
- [ ] Ver vehículos ✓
- [ ] Ver disponibles ✓
- [ ] Crear reserva
- [ ] Confirmar reserva
- [ ] Ver reservas

---

**¡Todo listo para probar! 🎉**

Para más detalles, consulta: **POSTMAN_GUIDE.md**
