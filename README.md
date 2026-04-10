# CineTrack

> Plataforma web de streaming de películas inspirada en Netflix.
> Proyecto transversal final DAW/DAM.

## Estado actual — v0.3.0

El proyecto está en fase de desarrollo activo con el núcleo funcional completamente operativo.
La aplicación arranca, se conecta a la base de datos y todas las funcionalidades principales funcionan end-to-end.

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Backend | Java 17 + Spring Boot 3.3.5 |
| Frontend | Thymeleaf + Bootstrap 5.3 |
| Base de datos | MySQL 8 (XAMPP en desarrollo, Docker en despliegue) |
| Seguridad | Spring Security + BCrypt |
| ORM | Spring Data JPA / Hibernate |
| Control de versiones | Git + GitHub |
| Contenedores | Docker + Docker Compose |

## Funcionalidades implementadas

- **Registro y autenticación** — Flujo completo: email → plan → creación de perfil → login automático
- **Sistema de perfiles múltiples** — Hasta 5 perfiles por cuenta según el plan contratado
- **Catálogo de películas** — 31 películas clásicas reales distribuidas en 6 géneros
- **Cartelera de inicio** — Carousel de novedades + filas por género al estilo Netflix
- **Historial de visualización** — Seguimiento de progreso por perfil (segundos vistos)
- **Continuar viendo** — Sección dinámica en la home basada en el historial
- **Mi Lista** — Guardado de películas por perfil con respuesta AJAX
- **Página de detalle** — Información completa de cada película
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
├── docker/                 # Configuración MySQL Docker
├── docs/                   # Documentación técnica y arquitectura
│   ├── atlas/              # Roadmap y notas de arquitectura
│   └── er/                 # Modelo relacional y ER
├── docker-compose.yml
└── README.md
```

## Puesta en marcha

### Con XAMPP (desarrollo)

1. Iniciar MySQL en XAMPP (puerto 3306)
2. Crear la base de datos y ejecutar los scripts:
   ```sql
   source database/schema/001_tables.sql
   source database/schema/002_relations.sql
   source database/schema/003_constraints.sql
   source database/schema/004_seeds.sql
   ```
3. Lanzar la aplicación:
   ```bash
   cd app
   ./mvnw spring-boot:run
   ```
4. Acceder a `http://localhost:8080`

### Con Docker

```bash
docker-compose up -d
```

## Credenciales de acceso

| Rol | Email | Contraseña |
|---|---|---|
| Administrador | admin@cinetrack.com | admin123 |

## Autor

**Jorge Ruiz** — Proyecto académico DAW/DAM
