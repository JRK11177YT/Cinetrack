# CineTrack — Módulo Backend

Módulo Spring Boot de la aplicación CineTrack.

## Requisitos

- Java 17+
- Maven 3.9+ (o usar el wrapper incluido `mvnw`)
- MySQL 8.0 (XAMPP o Docker)

## Configuración

La aplicación lee la URL de base de datos desde variables de entorno con fallback a valores por defecto:

```
DB_HOST=127.0.0.1
DB_PORT=3306
DB_NAME=cinetrack_db
DB_USER=root
DB_PASSWORD=
```

Sin variables de entorno arranca directamente contra XAMPP con usuario root sin contraseña.

## Arranque

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

## Estructura del código

```
src/main/java/com/cinetrack/
├── config/          # SecurityConfig, DatabaseSeeder, ProfileInterceptor
├── controller/      # HomeController, AuthController, PerfilController,
│                    # PeliculaController, MiListaController,
│                    # CuentaController, AdminController
├── model/           # Usuario, Perfil, Pelicula, Genero,
│                    # HistorialVisualizacion, MiLista, PerfilGenero
├── repository/      # Spring Data JPA interfaces
├── service/         # Lógica de negocio
└── health/          # Health check endpoint
```

## Subida de archivos

Los archivos subidos se almacenan en `uploads/`:
- `uploads/avatars/` — Avatares de perfil (máx. 2 MB)
- `uploads/peliculas/imagenes/` — Posters (máx. 10 MB)
- `uploads/peliculas/videos/` — Vídeos (máx. 500 MB)

## Cuenta de administración

Se crea automáticamente al arrancar si no existe:
- Email: `admin@cinetrack.com`
- Contraseña: `admin123`
- Acceso al panel: `/admin`
