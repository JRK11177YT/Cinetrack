# CineTrack Backend (Spring Boot)

Configuracion base profesional para conectar CineTrack con MySQL de XAMPP.

## Estado actual validado

- Conexion Spring Boot -> MySQL `cinetrack_db` verificada.
- Test de contexto y test de conexion ejecutados en local con exito.
- Endpoints de salud operativos:
  - `http://localhost:8080/health/ping`
  - `http://localhost:8080/health/db`

## Requisitos

- Java 17 (JDK)
- XAMPP con modulo MySQL en estado **Running**
- Base de datos `cinetrack_db` creada y scripts SQL aplicados

> Nota: no necesitas tener `mvn` instalado globalmente. El proyecto usa Maven Wrapper (`mvnw.cmd`).

## Arranque recomendado (PowerShell)

```powershell
Set-Location "C:\Users\Jorge\cinetrack\app"
.\scripts\start-backend.ps1
```

Alternativa equivalente:

```powershell
Set-Location "C:\Users\Jorge\cinetrack\app"
.\mvnw.cmd spring-boot:run
```

## Verificacion de conexion

Con el backend levantado, ejecuta:

```powershell
Set-Location "C:\Users\Jorge\cinetrack\app"
.\scripts\check-backend.ps1
```

Resultado esperado:

- `OK /health/ping -> {"service":"cinetrack-app","status":"UP"}`
- `OK /health/db -> {"status":"UP","database":"cinetrack_db"}`

## Variables de entorno opcionales

Puedes sobreescribir parametros de conexion sin tocar codigo:

- `DB_HOST` (por defecto `127.0.0.1`)
- `DB_PORT` (por defecto `3306`)
- `DB_NAME` (por defecto `cinetrack_db`)
- `DB_USERNAME` (por defecto `root`)
- `DB_PASSWORD` (por defecto vacio)

Ejemplo para puerto y password personalizados:

```powershell
Set-Location "C:\Users\Jorge\cinetrack\app"
$env:DB_PORT="3307"
$env:DB_PASSWORD="tu_password"
.\mvnw.cmd spring-boot:run
```

## Diagnostico rapido de errores tipicos

1. **`Connection refused`**: MySQL no esta encendido o el puerto no coincide con XAMPP.
2. **`Access denied for user`**: usuario/password incorrectos.
3. **`Unknown database`**: `cinetrack_db` no existe o `DB_NAME` apunta a otra base.
4. **No aparece boton Run en IntelliJ**: abrir `app/pom.xml` como proyecto Maven y confirmar JDK 17 en `Project SDK`.

## Pruebas automatizadas

```powershell
Set-Location "C:\Users\Jorge\cinetrack\app"
.\mvnw.cmd test
```

La clase `CineTrackApplicationTests` comprueba que Spring levanta y que `SELECT DATABASE()` devuelve `cinetrack_db`.

