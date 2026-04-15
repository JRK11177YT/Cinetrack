# Changelog — CineTrack

Historial de versiones del proyecto. Sigue el formato [Keep a Changelog](https://keepachangelog.com/es/1.0.0/).

---

## [0.4.0] — 2026-04-15

### Añadido
- Buscador en tiempo real con sugerencias dinámicas vía AJAX
- Página de resultados de búsqueda (`/buscar`)
- Página de sugerencias personalizadas (`/sugerencias`) basadas en géneros favoritos del perfil activo
- Chips de género seleccionables en el perfil para personalización
- Campos `director` y `valoracion_imdb` en la entidad `Pelicula` (Hibernate auto-migra)
- Todos los directores y valoraciones IMDb de las 31 películas del catálogo
- Sección "Películas relacionadas" en la página de detalle (mismo género, máx. 6)
- Icono oficial IMDb en la página de detalle
- Botón "Volver arriba" en el footer
- Versión de la app en el footer con enlace al repositorio GitHub

### Corregido
- Vídeo en detalle de película: eliminado `autoplay`, src cargado solo al pulsar Reproducir
- Sinopsis duplicada en el hero banner de detalle eliminada
- Footer flotando al centro en páginas con poco contenido (sticky footer via flex)
- Layout del panel admin roto por las reglas de sticky footer (aislado con `:not(.admin-body)`)

---

## [0.3.0] — 2026-04-10

### Añadido
- Catálogo real de 31 películas clásicas distribuidas en 6 géneros (Top Gun, The Godfather, Pulp Fiction, Interstellar, Saving Private Ryan, La Sociedad de la Nieve, The Avengers, La La Land, Pearl Harbor y más)
- Migración SQL `005_replace_peliculas.sql` para actualización puntual del catálogo
- Variable CSS `--bg-dark` para unificación de fondos oscuros

### Corregido
- **Bug crítico**: formulario editar película en admin — `#strings.substringAfterLast` no existe en Thymeleaf; reemplazado por `#strings.substring` + `lastIndexOf`
- Hover clipping en tarjetas de película: `overflow-x` en el slider forzaba `overflow-y: hidden`, cortando el efecto de escala. Solución: `translateY` en la tarjeta + `scale(1.08)` solo en el poster
- Solapamiento de contenido bajo el carousel de novedades (margin-top negativo excesivo)
- Caracteres especiales (tildes, ñ) corruptos en base de datos por encoding cp850 en pipe de PowerShell. Solución: `mysql -e "source archivo.sql" --default-character-set=utf8mb4`

### Cambiado
- Tarjetas de película: 180px → 220px de ancho
- Grilla de películas: `minmax(160px, 1fr)` → `minmax(190px, 1fr)`
- Slider: `padding: 8px 0` → `padding: 20px 0`
- Footer rediseñado: layout 3 columnas con marca CINETRACK centrada y autoría a la derecha
- Auth pages y admin panel unificados al mismo fondo (`--bg-dark`)
- `crear-perfil.html`: eliminado bloque `<style>` inline (90 líneas), clases migradas a `main.css`
- Seeds `004_seeds.sql` actualizados con catálogo real

---

## [0.2.0] — 2026-03-XX

### Añadido
- Rediseño completo de la interfaz al estilo Netflix con Bootstrap 5
- Sistema de perfiles múltiples por cuenta (hasta 5 según plan)
- Spring Security con BCrypt y remember-me cookie (7 días)
- Panel de administración: CRUD películas, géneros y usuarios
- Subida de archivos: posters, vídeos y avatares con validación de tipo y tamaño
- Cartelera de inicio: carousel de novedades + filas por género
- Historial de visualización y sección "Continuar viendo"
- Mi Lista: watchlist por perfil con respuesta AJAX
- Flujo de registro completo: email → plan → perfil → login automático
- Página de detalle de película
- Página de gestión de cuenta (cambio de contraseña y plan)
- `DatabaseSeederConfig`: admin@cinetrack.com / admin123 creado automáticamente

---

## [0.1.0] — 2026-02-XX

### Añadido
- Estructura inicial del repositorio
- Modelo de base de datos: tablas, relaciones, constraints e índices
- Proyecto Spring Boot con conexión a MySQL
- Vistas iniciales con Thymeleaf
- Configuración Docker Compose con MySQL 8
- Sistema Atlas (documentación de arquitectura)
- Organización de carpetas y workflow Git
