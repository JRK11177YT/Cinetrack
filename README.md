# CineTrack

> Plataforma web de streaming de películas inspirada en Netflix.  
> Proyecto transversal final DAW/DAM.

![Estado](https://img.shields.io/badge/estado-en%20desarrollo-yellow)
![Versión](https://img.shields.io/badge/versión-v0.4.0--dev-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)

> ⚠️ **Revisión intermedia** — Este repositorio refleja el estado actual del proyecto para revisión del profesorado. **No es la versión final de entrega.** El desarrollo continúa activamente en la rama `develop` y `feature/frontend-ui`.

---

## Estado actual — v0.4.0-dev

El núcleo funcional está completamente operativo. La aplicación arranca, se conecta a la base de datos y todas las funcionalidades principales funcionan end-to-end. Se siguen añadiendo mejoras de UX y funcionalidades.

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
- **Catálogo de películas** — 31 películas clásicas reales con directores y valoraciones IMDb
- **Cartelera de inicio** — Carousel de novedades + filas por género al estilo Netflix
- **Buscador en tiempo real** — Búsqueda con sugerencias dinámicas vía AJAX
- **Sugerencias personalizadas** — Basadas en géneros favoritos del perfil activo
- **Historial de visualización** — Seguimiento de progreso por perfil (segundos vistos)
- **Continuar viendo** — Sección dinámica en la home basada en el historial
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
