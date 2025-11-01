# 🚀 Quick Start - EcoMove API

## Inicio Rápido en 3 Pasos

### 1️⃣ Compilar el Proyecto
```bash
./mvnw clean compile
```

### 2️⃣ Ejecutar la Aplicación
```bash
./mvnw spring-boot:run
```

### 3️⃣ Probar la API
La aplicación estará disponible en: `http://localhost:8090`

## 🧪 Prueba Rápida

### Crear un Usuario
```bash
curl -X POST http://localhost:8090/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phoneNumber": "+57 300 1234567",
    "dateOfBirth": "1990-01-01",
    "documentNumber": "123456789",
    "userType": "REGULAR",
    "balance": 100000,
    "isActive": true,
    "tripHistory": {"trips": []}
  }'
```

### Crear una Bicicleta Eléctrica
```bash
curl -X POST http://localhost:8090/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "E-Bike Test",
    "model": "Test 2024",
    "brand": "TestBrand",
    "type": "ELECTRIC_BIKE",
    "pricePerHour": 5000,
    "currentLocation": {"latitude": 5.0689, "longitude": -75.5174},
    "battery": {"capacity": 500, "currentCharge": 500, "chargeCycles": 0, "maxChargeCycles": 1000},
    "gears": 7,
    "hasLights": true,
    "weight": 22.5
  }'
```

### Ver Todos los Usuarios
```bash
curl http://localhost:8090/api/users
```

### Ver Todos los Vehículos
```bash
curl http://localhost:8090/api/vehicles
```

### Ver Vehículos Disponibles
```bash
curl http://localhost:8090/api/vehicles/available
```

## 📚 Documentación Completa

- **README.md** - Documentación principal del proyecto
- **EXAMPLES.md** - Ejemplos detallados de todos los endpoints
- **RESUMEN_PROYECTO.md** - Resumen técnico y arquitectura

## 🔧 Configuración

El servidor corre por defecto en el puerto **8090**. 

Para cambiar el puerto, edita `src/main/resources/application.properties`:
```properties
server.port=8090
```

## 📁 Archivos CSV

Los datos se guardan automáticamente en la carpeta `data/`:
- `data/users.csv`
- `data/vehicles.csv`
- `data/routes.csv`
- `data/reservations.csv`
- `data/maintenances.csv`

## 🎯 Endpoints Principales

| Entidad | Endpoint Base | Métodos |
|---------|---------------|---------|
| Usuarios | `/api/users` | GET, POST, PUT, DELETE |
| Vehículos | `/api/vehicles` | GET, POST, PUT, DELETE, PATCH |
| Rutas | `/api/routes` | GET, POST, PUT, DELETE |
| Reservas | `/api/reservations` | GET, POST, PUT, DELETE |
| Mantenimientos | `/api/maintenances` | GET, POST, PUT, DELETE |

## ✅ Verificar que Todo Funciona

1. **Compilación exitosa**: `./mvnw clean compile`
2. **Aplicación corriendo**: Ver log "Started EcoMoveApplication"
3. **API respondiendo**: `curl http://localhost:8090/api/users`

## 🆘 Solución de Problemas

### Puerto en uso
Si el puerto 8090 está ocupado:
1. Edita `application.properties`
2. Cambia `server.port=8090` a otro puerto (ej: 8080)

### Error de compilación
```bash
./mvnw clean install -U
```

### Limpiar datos CSV
```bash
rm -rf data/*.csv
```
Los archivos se recrearán automáticamente al ejecutar la aplicación.

## 📞 Ayuda

Para más información, consulta:
- `README.md` - Documentación completa
- `EXAMPLES.md` - Ejemplos de uso
- `RESUMEN_PROYECTO.md` - Arquitectura y diseño

---

**¡Listo para usar! 🎉**
