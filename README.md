# CineTrack

> Plataforma web de streaming de películas inspirada en Netflix.  
> Proyecto transversal final DAW/DAM.

![Estado](https://img.shields.io/badge/estado-en%20desarrollo-yellow)
![Versión](https://img.shields.io/badge/versión-v0.4.0--dev-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)

---

## Estado actual — v0.4.0-dev

El núcleo funcional está completamente operativo. La aplicación arranca, se conecta a la base de datos y todas las funcionalidades principales funcionan end-to-end. Se siguen añadiendo mejoras de UX y funcionalidades.

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Backend | Java 17 + Spring Boot 3.3.5 |
| Frontend | Thymeleaf + Bootstrap 5.3 |
| Base de datos | MySQL 8 |
| Seguridad | Spring Security + BCrypt |
| ORM | Spring Data JPA / Hibernate |
| Control de versiones | Git + GitHub |

## Funcionalidades implementadas

- **Registro y autenticación** — Flujo completo: email → plan → creación de perfil → login automático
- **Sistema de perfiles múltiples** — Hasta 5 perfiles por cuenta según el plan contratado
- **Catálogo de películas** — 31 películas clásicas reales con directores y valoraciones IMDb
- **Cartelera de inicio** — Carousel de novedades + filas por género al estilo Netflix
- **Buscador en tiempo real** — Búsqueda con sugerencias dinámicas vía AJAX
- **Sugerencias personalizadas** — Basadas en géneros favoritos del perfil activo
- **Mi Lista** — Guardado de películas por perfil con respuesta AJAX
- **Página de detalle** — Director, valoración IMDb, video bajo demanda y películas relacionadas
- **Panel de administración** — CRUD completo de películas, géneros y usuarios con subida de archivos
- **Gestión de avatares** — Presets predefinidos o imagen personalizada (upload)
- **Cuenta de usuario** — Cambio de contraseña y cambio de plan de suscripción
- **Diseño responsive** — Bootstrap 5 adaptado a móvil, tablet y escritorio

## Estructura del repositorio

```
cinetrack/
├── app/                    # Proyecto Spring Boot
│   ├── src/main/java/      # Código Java (MVC)
│   ├── src/main/resources/ # Templates, CSS, JS, config
│   └── uploads/            # Archivos subidos (imágenes, vídeos)
├── database/
│   ├── schema/             # Scripts SQL (tablas, relaciones, seeds)
│   └── migrations/         # Migraciones incrementales
├── docs/                   # Documentación técnica y arquitectura
│   ├── atlas/              # Roadmap y notas de arquitectura
│   └── er/                 # Modelo relacional y ER
├── scripts/                # Scripts de inicialización (Windows y Linux)
└── README.md
```

## Puesta en marcha desde cero (clon fresco)

### Opción A — Script automático (recomendado para Windows + XAMPP)

1. Iniciar MySQL en XAMPP (panel de control → Start MySQL)
2. Ejecutar el script de inicialización desde la raíz del proyecto:
   ```powershell
   .\scripts\setup-db.ps1
   # Si tienes contraseña en MySQL:
   .\scripts\setup-db.ps1 -Password "tupassword"
   ```
3. Arrancar la aplicación:
   ```powershell
   cd app
   .\mvnw.cmd spring-boot:run
   ```
4. Acceder a `http://localhost:8080`

El script crea la base de datos `cinetrack_db`, carga toda la estructura y los datos de prueba (31 películas, 6 géneros). El usuario administrador se crea automáticamente al arrancar la app.

### Opción B — Manual (MySQL CLI)

```bash
mysql -u root < database/init.sql
cd app && ./mvnw spring-boot:run
```

### Opción C — Linux (VirtualBox / Ubuntu / macOS)

1. Instalar dependencias:
   ```bash
   sudo apt update
   sudo apt install -y openjdk-17-jdk mysql-server git
   sudo systemctl start mysql
   ```
2. Clonar el repositorio y ejecutar el script de inicialización:
   ```bash
   git clone https://github.com/JRK11177YT/Cinetrack.git cinetrack
   cd cinetrack
   bash scripts/setup-db.sh
   ```
3. Arrancar la aplicación:
   ```bash
   cd app
   chmod +x mvnw
   ./mvnw spring-boot:run
   ```
4. Acceder a `http://localhost:8080`

---

**Credenciales por defecto:**
- Admin: `admin@cinetrack.com` / `admin123`
- Panel de administración: `http://localhost:8080/admin`

## Credenciales de acceso

| Rol | Email | Contraseña |
|---|---|---|
| Administrador | admin@cinetrack.com | admin123 |

## Autor

**Jorge Ruiz** — Proyecto académico DAW/DAM
