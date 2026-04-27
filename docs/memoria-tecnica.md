# Memoria Técnica del Proyecto — CineTrack

> **Proyecto de Desarrollo de Aplicaciones Web**
> Primer Curso — DAW (Desarrollo de Aplicaciones Web)
> Alumno: Jorge
> Curso: 2025-2026

---

## 1. Descripción del Producto

**CineTrack** es una plataforma web de streaming de películas, inspirada en la experiencia de usuario de Netflix.
Permite a los usuarios registrarse, elegir un plan de suscripción y disfrutar de un catálogo de películas organizado
por géneros, con personalización por perfil, lista personal de contenido y un panel de administración completo.

El proyecto no es un simple ejercicio académico: es un producto digital con identidad de marca propia,
diseñado con criterios profesionales y con una base técnica sólida que permite escalar hacia un producto
comercialmente viable.

---

## 2. Propuesta de Valor

| Característica | Descripción |
|---|---|
| Experiencia Netflix | Interfaz oscura, carruseles, hero banner y comportamiento familiar para el usuario |
| Multiusuario / Multiperfil | Cada cuenta soporta varios perfiles independientes con preferencias propias |
| Planes de suscripción | BÁSICO, ESTÁNDAR y PREMIUM con límites de perfiles configurables |
| Personalización | Géneros favoritos por perfil → sugerencias adaptadas |
| Catálogo real | 31 películas clásicas con director, valoración IMDb y contenido visual |
| Panel de administración | CRUD completo de películas, géneros y usuarios sin tocar la base de datos |
| Seguridad robusta | Spring Security, BCrypt, control de acceso por rol y validaciones en backend |

---

## 3. Tecnologías Utilizadas

### Backend
| Tecnología | Versión | Rol en el proyecto |
|---|---|---|
| Java | 17 (LTS) | Lenguaje principal de la aplicación |
| Spring Boot | 3.3.5 | Framework que orquesta toda la aplicación |
| Spring Security | 6.x | Autenticación, autorización y remember-me |
| Spring Data JPA | 3.3.5 | Acceso a base de datos vía ORM |
| Hibernate | 6.x | Implementación JPA; mapeo objeto-relacional |
| Bean Validation | 3.x | Validación de formularios en servidor |

### Frontend
| Tecnología | Versión | Rol en el proyecto |
|---|---|---|
| Thymeleaf | 3.x | Motor de plantillas HTML server-side |
| Thymeleaf Layout Dialect | 3.x | Sistema de layouts reutilizables (base + app) |
| CSS personalizado | — | Diseño completo dark theme, sin frameworks CSS externos |
| JavaScript (Vanilla) | ES2020 | Carruseles, buscador AJAX, Mi Lista, interacciones |
| Google Fonts | — | Montserrat (titulares) + Inter (cuerpo de texto) |
| Font Awesome | 6.x | Iconografía consistente en toda la UI |

### Base de Datos
| Tecnología | Versión | Rol en el proyecto |
|---|---|---|
| MySQL | 8.x | Motor principal de base de datos relacional |
| XAMPP | — | Entorno de desarrollo local |
| Docker + docker-compose | — | Entorno de producción / despliegue reproducible |

### Herramientas de Desarrollo
| Herramienta | Rol |
|---|---|
| IntelliJ IDEA | IDE principal para Java y Spring Boot |
| VS Code | Edición de frontend, documentación y repositorio |
| Maven (Wrapper) | Gestión de dependencias y ciclo de construcción |
| Git + GitHub | Control de versiones y repositorio remoto |
| XAMPP | Servidor MySQL local durante el desarrollo |

---

## 4. Arquitectura del Sistema

El proyecto sigue el patrón de arquitectura **MVC (Modelo-Vista-Controlador)** en su implementación estándar
para Spring Boot:

```
┌─────────────────────────────────────────────────────┐
│  NAVEGADOR  (HTML + CSS + JS)                        │
│       ↓ HTTP Request                                 │
├─────────────────────────────────────────────────────┤
│  SPRING SECURITY  — Intercepta toda petición        │
│    ↓ Solo pasa si está autenticado + autorizado      │
├─────────────────────────────────────────────────────┤
│  PROFILE INTERCEPTOR  — Verifica perfil activo       │
│    ↓                                                 │
├─────────────────────────────────────────────────────┤
│  CONTROLADORES  — Coordinan la lógica de solicitud   │
│    AuthController / HomeController / AdminController │
│    PeliculaController / MiListaController / etc.     │
│    ↓                                                 │
├─────────────────────────────────────────────────────┤
│  SERVICIOS  — Lógica de negocio                     │
│    UsuarioService / PerfilService / PeliculaService  │
│    GeneroService / MiListaService                    │
│    ↓                                                 │
├─────────────────────────────────────────────────────┤
│  REPOSITORIOS  — Acceso a datos (Spring Data JPA)   │
│    UsuarioRepository / PeliculaRepository / etc.     │
│    ↓                                                 │
├─────────────────────────────────────────────────────┤
│  BASE DE DATOS  MySQL                               │
│    usuarios / perfiles / peliculas / generos         │
│    mi_lista / perfil_generos                         │
└─────────────────────────────────────────────────────┘
```

### Separación de responsabilidades aplicada:
- Los **Controladores** no consultan la base de datos directamente; siempre delegan en Servicios.
- Los **Servicios** contienen toda la lógica de negocio (límites de perfiles, validaciones, etc.).
- Los **Repositorios** son interfaces de Spring Data JPA — no tienen lógica propia, solo consultas.
- Los **Modelos** son entidades JPA con comportamiento propio cuando corresponde (ej. `Genero.getSlug()`).

---

## 5. Modelo de Datos

### Diagrama Entidad-Relación (conceptual)

```
usuarios ─────< perfiles >──────── perfil_generos >───── generos
                   │                                         │
                   └──────< mi_lista                         │
                                │                            │
                            peliculas >──────────────────────┘
```

### Entidades y su justificación

| Entidad | Tabla | Propósito |
|---|---|---|
| `Usuario` | `usuarios` | Representa la cuenta de suscripción (quien paga) |
| `Perfil` | `perfiles` | Cada miembro dentro de la cuenta (quien consume) |
| `Pelicula` | `peliculas` | Contenido del catálogo con metadatos completos |
| `Genero` | `generos` | Categorización del catálogo con lógica propia |
| `PerfilGenero` | `perfil_generos` | Gustos del perfil; base del motor de sugerencias |
| `MiLista` | `mi_lista` | Intención de consumo futuro por perfil |

### Decisiones técnicas clave

**Perfil vs Usuario**: La separación es intencional y refleja el modelo real de plataformas de streaming.
Un usuario paga la suscripción; un perfil consume contenido. Esto permite que cada perfil tenga
sus propias preferencias de género, su propia lista y su propio avatar, de forma completamente independiente.

**PerfilGenero (N:M)**: La tabla intermedia entre perfiles y géneros es necesaria porque un perfil puede
preferir múltiples géneros y un género puede ser preferido por múltiples perfiles. La restricción
`UNIQUE(perfil_id, genero_id)` garantiza integridad.

**Restricción de perfiles por plan**:
- BÁSICO → 1 perfil máximo
- ESTÁNDAR → 2 perfiles máximo
- PREMIUM → 5 perfiles máximo

Esta lógica está implementada en el backend (`UsuarioService`) y verificada en el servidor,
no solo en la interfaz. Esto es fundamental para la seguridad del negocio.

---

## 6. Seguridad

La seguridad es uno de los pilares del proyecto. Se han aplicado múltiples capas de protección:

### Autenticación
- **BCrypt** para el almacenamiento de contraseñas. Nunca se guarda la contraseña en texto plano.
- **Remember-me** con token persistente de 7 días, gestionado por Spring Security.
- La clave del token remember-me se configura vía variable de entorno (`REMEMBER_ME_KEY`), nunca hardcodeada en el código fuente.

### Autorización
- **Control por roles**: las URLs `/admin/**` solo son accesibles para usuarios con rol `ADMIN`. Cualquier
  intento de acceso directo resulta en un error 403.
- **ProfileInterceptor**: antes de acceder a cualquier página de la aplicación, se verifica que el perfil
  activo almacenado en sesión existe realmente en la base de datos. Esto previene el acceso con
  perfiles eliminados o manipulados.

### Validaciones de negocio en backend
- El límite de perfiles por plan se valida en el servidor. No es suficiente confiar en el frontend.
- La subida de archivos (imágenes y vídeos) valida tanto la extensión como el **Content-Type** real del
  archivo. Esto previene ataques de subida de archivos maliciosos que simplemente renombran su extensión.

### Protección CSRF
- Spring Security protege todos los formularios contra ataques CSRF automáticamente.
- El `MultipartFilter` se registra **antes** del filtro de seguridad para que el token CSRF pueda leerse
  correctamente en formularios `multipart/form-data` (subida de archivos).

---

## 7. Flujo de Usuario — Paso a Paso

### 7.1 Registro de nuevo usuario (3 pasos)
```
PASO 1: /registro
  → El usuario introduce email y contraseña
  → Se crea la cuenta en BD y se autentica automáticamente (sin guardar la contraseña en sesión)

PASO 2: /registro/plan
  → El usuario elige su plan: BÁSICO, ESTÁNDAR o PREMIUM
  → El plan actualiza el atributo `maxPerfiles` de la cuenta

PASO 3: /registro/crear-perfil
  → El usuario crea su primer perfil (nombre + avatar + géneros favoritos)
  → Redirige a /inicio con el perfil activo
```

### 7.2 Inicio de sesión
```
/login
  → Spring Security procesa email + contraseña
  → Si correcto → redirige a /perfiles
  → El usuario selecciona con qué perfil desea continuar
  → Redirige a /inicio
```

### 7.3 Experiencia de catálogo
```
/inicio        → Películas destacadas + novedades (carruseles)
/buscar        → Búsqueda en tiempo real con AJAX (sin recargar la página)
/sugerencias   → Películas filtradas por géneros favoritos del perfil activo
/peliculas/{id} → Detalle completo: hero banner, vídeo, director, IMDb, películas relacionadas
/mi-lista      → Contenido guardado por el perfil activo
```

### 7.4 Gestión de cuenta
```
/perfiles/gestionar → Crear, editar o eliminar perfiles de la cuenta
/cuenta             → Datos de la cuenta del usuario
```

### 7.5 Panel de administración
```
/admin/dashboard           → Estadísticas generales (usuarios, películas, géneros)
/admin/peliculas           → Listado + CRUD de películas (subida de imagen/vídeo)
/admin/peliculas/nueva     → Formulario de nueva película
/admin/peliculas/{id}/editar → Edición con previsualización de archivos existentes
/admin/generos             → CRUD completo de géneros
/admin/usuarios            → Listado de usuarios con gestión de estado
```

---

## 8. Frontend — Decisiones de Diseño

### Identidad Visual
Bootstrap 5 actúa como base estructural (sistema de grid, utilidades y componente navbar). Sobre
esta base, un CSS personalizado completo (`main.css`) implementa toda la identidad visual:
- **Paleta de color**: fondos `#141414` / `#1a1a1a` con acentos en rojo `#e50914` (identidad Netflix)
- **Tipografía**: Montserrat para títulos (impacto visual) + Inter para texto (legibilidad máxima)
- **Modelo caja**: uso de Flexbox para la navegación y chips de géneros; CSS Grid para el catálogo

### HTML Semántico
Las plantillas Thymeleaf utilizan etiquetas HTML5 semánticas para garantizar accesibilidad y SEO:
- `<header>` para la barra de navegación principal
- `<main>` para el contenido único de cada página
- `<section>` para cada carrusel o bloque de contenido diferenciado
- `<nav>` para la barra de navegación y la barra de admin
- `<article>` para tarjetas de película individuales
- `<footer>` para el pie de página global

### Responsividad
La interfaz es completamente responsiva mediante CSS Grid y Flexbox sin media queries complejas:
- Las tarjetas de película usan `grid-template-columns: repeat(auto-fill, minmax(190px, 1fr))`,
  adaptándose automáticamente a cualquier ancho de pantalla.
- La navegación colapsa correctamente en pantallas pequeñas gracias a Flexbox con `flex-wrap`.

### AJAX / Fetch API
La operación "Mi Lista" (añadir/eliminar) se realiza sin recargar la página:
1. El usuario hace clic en el botón
2. JavaScript envía una petición `fetch()` al backend (POST o DELETE)
3. El DOM se actualiza directamente (la tarjeta desaparece o el botón cambia de estado)
4. Sin redirección, sin recarga completa → experiencia fluida

### Sistema de Layouts
Se usa el **Thymeleaf Layout Dialect** con dos layouts principales:
- `base.html` → Para páginas públicas (login, registro) — sin navegación
- `app.html` → Para la aplicación autenticada — incluye header, footer y navegación completa

---

## 9. Estructura del Repositorio

```
cinetrack/
├── app/                         ← Proyecto Spring Boot completo
│   ├── src/
│   │   ├── main/java/com/cinetrack/
│   │   │   ├── config/          ← Seguridad, interceptores, seeder, MVC
│   │   │   ├── controller/      ← 9 controladores (Auth, Admin, Home, etc.)
│   │   │   ├── model/           ← 6 entidades JPA
│   │   │   ├── repository/      ← 6 repositorios Spring Data JPA
│   │   │   └── service/         ← 5 servicios de negocio
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/          ← CSS, JS, imágenes
│   │   │   └── templates/       ← 10 secciones de vistas Thymeleaf
│   └── uploads/                 ← Archivos subidos (imágenes, vídeos)
├── database/
│   ├── schema/                  ← Scripts SQL ordenados (001-004)
│   └── migrations/              ← Migraciones puntuales
├── docs/
│   ├── diagrams/                ← Diagramas UML en Mermaid
│   ├── er/                      ← Justificación del modelo de datos
│   ├── notes/                   ← Notas de arquitectura backend
│   └── atlas/                   ← Guías de trabajo y roadmap del proyecto
├── docker/                      ← Configuración Docker para MySQL
├── CHANGELOG.md                 ← Historial detallado de versiones
└── README.md                    ← Documentación principal del proyecto
```

---

## 10. Control de Versiones — Git

El proyecto sigue una estrategia de ramas profesional basada en **Git Flow**:

| Rama | Propósito |
|---|---|
| `main` | Versiones estables y lanzamientos |
| `develop` | Integración continua del trabajo en curso |
| `feature/...` | Cada funcionalidad nueva en su propia rama |

### Versionado semántico
Las versiones siguen el formato `MAYOR.MENOR.PARCHE`:
- **v0.1.0-alpha** → Vistas Thymeleaf básicas (pre-diseño)
- **v0.2.0** → Modelo de datos y arquitectura principal
- **v0.3.0** → Catálogo real de 31 películas
- **v0.4.0** → Buscador AJAX + sugerencias personalizadas + mejoras UI
- **v0.5.0** → Hardening de seguridad, separación dev/prod, optimizaciones y 16 tests

### Disciplina de commits
Los mensajes de commit siguen la convención **Conventional Commits**:
- `feat:` para nuevas funcionalidades
- `fix:` para correcciones de bugs
- `refactor:` para mejoras de código sin cambio de comportamiento
- `docs:` para cambios en documentación
- `style:` para ajustes de CSS o formato

---

## 11. Fortalezas del Proyecto

### Técnicas
- **Arquitectura limpia y profesional**: clara separación MVC con 4 capas bien diferenciadas.
- **Seguridad multicapa**: BCrypt + Spring Security + validaciones en backend + control de Content-Type.
- **Diseño CSS propio**: 100% artesanal, sin dependencia de frameworks externos. Demuestra dominio real del lenguaje.
- **AJAX integrado**: Mi Lista y el buscador funcionan sin recargas, igual que una aplicación moderna.
- **Base de datos evolucionable**: diseño normalizado que permite añadir historial, valoraciones o recomendaciones sin reestructurar.
- **Código reproducible**: Docker + scripts SQL ordenados permiten arrancar el proyecto en cualquier máquina en minutos.
- **Documentación viva**: diagramas UML en Mermaid directamente en el repositorio, sincronizados con el código.

### De Producto / Negocio
- **Identidad de marca sólida**: nombre, paleta de colores, tipografía y comportamiento visual consistentes.
- **Modelo de negocio implícito**: los tres planes de suscripción (BÁSICO / ESTÁNDAR / PREMIUM) crean una base para monetización.
- **Experiencia de usuario familiar**: el usuario que conoce Netflix se orienta instantáneamente.
- **Catálogo de calidad**: 31 películas reconocidas con datos reales (director, valoración IMDb).
- **Personalización genuina**: el motor de sugerencias por géneros crea engagement y retención.

---

## 12. Áreas de Crecimiento y Potencial de Expansión

El proyecto está diseñado para crecer. Estas son las líneas de evolución más naturales:

### Experiencia de usuario
| Área | Potencial |
|---|---|
| Reproductor de vídeo avanzado | Un reproductor HTML5 con controles personalizados, subtítulos y resolución adaptativa (HLS) elevaría la experiencia a nivel profesional |
| Historial de visualización | La arquitectura de base de datos permite añadir esta tabla sin modificar las existentes |
| Sistema de valoraciones | Reseñas y puntuaciones por perfil enriquecen el catálogo y generan contenido propio |

### Negocio y Monetización
| Área | Potencial |
|---|---|
| Pasarela de pago | Integración con Stripe o PayPal para gestionar suscripciones de forma real |
| Panel de analítica | Métricas de consumo por película, género y perfil → datos para decisiones editoriales |
| Sistema de notificaciones | Email o push notifications para nuevos estrenos → fidelización del usuario |

### Técnico
| Área | Potencial |
|---|---|
| API REST | El backend puede exponerse como API para alimentar apps móviles en el futuro |
| Recomendaciones con ML | Los datos de `perfil_generos` y `mi_lista` son la semilla perfecta para un motor de recomendación basado en datos |
| Despliegue en la nube | La base Docker ya preparada permite migrar a AWS, Azure o Railway con mínima configuración |

---

## 13. Despliegue y Portabilidad

### Entorno de Desarrollo (Local)
**Requisitos**: Java 17, Maven, MySQL (XAMPP)
```bash
# 1. Crear la base de datos
mysql -u root < database/schema/001_tables.sql
mysql -u root < database/schema/002_relations.sql
mysql -u root < database/schema/003_constraints.sql
mysql -u root < database/schema/004_seeds.sql

# 2. Arrancar la aplicación
cd app
./mvnw spring-boot:run
```

La aplicación crea automáticamente el usuario administrador al arrancar por primera vez:
- **Email**: `admin@cinetrack.com`
- **Contraseña**: `admin123`

### Entorno Docker
```bash
# Levantar MySQL + App en contenedores
docker-compose up --build
```

La configuración en `application.properties` lee variables de entorno para la base de datos
(`DB_HOST`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`), haciendo la app completamente portable.

---

## 14. Pruebas

El proyecto incluye una suite de tests de la capa de servicios con H2 como base de datos en memoria:

- `MiListaServiceTest` → Verifica la lógica de añadir y eliminar películas de la lista personal.
- `CineTrackApplicationTests` → Test de contexto: garantiza que la aplicación arranca correctamente.

Los tests se ejecutan con:
```bash
cd app
./mvnw test
```

---

## 15. Conclusión

CineTrack es la demostración de que un proyecto de primer año puede alcanzar un nivel técnico y
visual digno de un portfolio profesional. No es solo un ejercicio de clase: es un producto con
identidad, con arquitectura pensada, con seguridad implementada con rigor y con una hoja de ruta
clara hacia un producto comercialmente interesante.

Cada decisión técnica tomada en este proyecto —desde la separación de perfil y usuario hasta la
validación de Content-Type en las subidas de archivo— tiene una justificación, y esa justificación
está documentada.

> *El valor de este proyecto no está solo en lo que hace hoy, sino en lo que está preparado para hacer mañana.*

---

*Documento generado: Abril 2026 | CineTrack v0.5.0*
