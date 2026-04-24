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

## Documentación técnica y diagramas

### Diagrama de Casos de Uso

#### Actores

| Actor | Descripción |
|---|---|
| **Usuario no autenticado** | Visitante sin sesión activa |
| **Usuario autenticado** | Cuenta registrada con sesión iniciada |
| **Perfil activo** | Perfil seleccionado dentro de una sesión |
| **Administrador** | Cuenta con rol `ADMIN`, accede al panel de gestión |

```mermaid
graph TD
    subgraph Actores
        UA[👤 Usuario no autenticado]
        UL[👤 Usuario autenticado]
        PA[👤 Perfil activo]
        AD[👤 Administrador]
    end

    subgraph UC_AUTH["Autenticación y registro"]
        UC1(Registrarse)
        UC2(Elegir plan de suscripción)
        UC3(Crear perfil inicial)
        UC4(Iniciar sesión)
        UC5(Cerrar sesión)
        UC6(Recordar sesión)
    end

    subgraph UC_PERFILES["Gestión de perfiles"]
        UC7(Seleccionar perfil activo)
        UC8(Crear nuevo perfil)
        UC9(Editar perfil)
        UC10(Eliminar perfil)
        UC11(Gestionar géneros favoritos)
        UC12(Cambiar avatar)
    end

    subgraph UC_CATALOGO["Catálogo y búsqueda"]
        UC13(Ver cartelera de inicio)
        UC14(Buscar película por título)
        UC15(Ver detalle de película)
        UC16(Ver sugerencias personalizadas)
    end

    subgraph UC_MILISTA["Mi Lista"]
        UC17(Añadir película a Mi Lista)
        UC18(Eliminar película de Mi Lista)
        UC19(Ver Mi Lista)
    end

    subgraph UC_CUENTA["Gestión de cuenta"]
        UC20(Cambiar contraseña)
        UC21(Cambiar plan de suscripción)
    end

    subgraph UC_ADMIN["Panel de administración"]
        UC22(Ver dashboard con estadísticas)
        UC23(Gestionar películas — CRUD)
        UC24(Gestionar géneros — CRUD)
        UC25(Gestionar usuarios — CRUD)
        UC26(Subir imagen / vídeo de película)
    end

    UA --> UC1
    UC1 --> UC2
    UC2 --> UC3
    UA --> UC4
    UC4 --> UC6

    UL --> UC5
    UL --> UC7
    UL --> UC8
    UL --> UC9
    UL --> UC10
    UL --> UC20
    UL --> UC21

    UC7 --> PA

    PA --> UC11
    PA --> UC12
    PA --> UC13
    PA --> UC14
    PA --> UC15
    PA --> UC16
    PA --> UC17
    PA --> UC18
    PA --> UC19

    AD --> UC22
    AD --> UC23
    AD --> UC24
    AD --> UC25
    UC23 --> UC26
```

---

### Diagramas de Secuencia

#### DS-01: Registro completo de usuario

```mermaid
sequenceDiagram
    actor U as Usuario
    participant V as Navegador
    participant AC as AuthController
    participant US as UsuarioService
    participant PS as PerfilService
    participant DB as MySQL

    U->>V: GET /registro
    V->>AC: GET /registro
    AC-->>V: render auth/registro.html

    U->>V: POST /registro (email, password, plan)
    V->>AC: POST /registro
    AC->>US: buscarPorEmail(email)
    US->>DB: SELECT * FROM usuarios WHERE email=?
    DB-->>US: empty
    US-->>AC: Optional.empty()

    AC->>US: registrar(email, password, plan)
    US->>DB: INSERT INTO usuarios (email, password_hash, plan)
    DB-->>US: Usuario guardado (id=N)
    US-->>AC: Usuario

    AC->>V: redirect /registro/plan (sesión: usuarioRegistroId=N)
    U->>V: GET /registro/plan → elige plan
    V->>AC: POST /registro/plan (plan)
    AC->>US: guardar(usuario con plan)
    US->>DB: UPDATE usuarios SET plan=? WHERE id=?
    AC->>V: redirect /registro/perfil

    U->>V: GET /registro/perfil → formulario perfil
    U->>V: POST /registro/perfil (nombre, avatar, géneros)
    V->>AC: POST /registro/perfil
    AC->>PS: crearPerfil(usuario, nombre, avatarUrl)
    PS->>DB: INSERT INTO perfiles
    DB-->>PS: Perfil guardado
    AC->>PS: guardarPreferenciasGenero(perfil, géneros)
    PS->>DB: DELETE perfil_generos WHERE perfil_id=?
    PS->>DB: INSERT INTO perfil_generos (perfil_id, genero_id) x N
    AC->>V: Auto-login + redirect /perfiles
    V-->>U: Pantalla de selección de perfiles
```

#### DS-02: Inicio de sesión y selección de perfil

```mermaid
sequenceDiagram
    actor U as Usuario
    participant V as Navegador
    participant SS as Spring Security
    participant CUDS as CustomUserDetailsService
    participant PC as PerfilController
    participant PS as PerfilService
    participant DB as MySQL

    U->>V: GET /login
    V->>SS: GET /login
    SS-->>V: render auth/login.html

    U->>V: POST /login (email, password)
    V->>SS: POST /login
    SS->>CUDS: loadUserByUsername(email)
    CUDS->>DB: SELECT * FROM usuarios WHERE email=?
    DB-->>CUDS: Usuario encontrado
    CUDS-->>SS: UserDetails (email, hash, [ROLE_USER])
    SS->>SS: BCrypt.matches(password, hash)
    SS-->>V: Sesión autenticada → redirect /perfiles

    V->>PC: GET /perfiles
    PC->>PS: obtenerPerfilesPorUsuario(usuarioId)
    PS->>DB: SELECT * FROM perfiles WHERE usuario_id=? AND activo=true
    DB-->>PS: Lista de perfiles
    PS-->>PC: [Perfil1, Perfil2]
    PC-->>V: render perfiles/seleccionar.html

    U->>V: POST /perfiles/seleccionar/{id}
    V->>PC: POST /perfiles/seleccionar/1
    PC->>PS: obtenerPorId(1)
    PS->>DB: SELECT * FROM perfiles WHERE id=?
    DB-->>PS: Perfil
    PC->>V: session.setAttribute("perfilActivoId", 1)
    PC-->>V: redirect /inicio
    V-->>U: Cartelera de inicio (home)
```

#### DS-03: Añadir película a Mi Lista (flujo AJAX)

```mermaid
sequenceDiagram
    actor U as Usuario
    participant V as Navegador (JS)
    participant MLC as MiListaController
    participant MLS as MiListaService
    participant PS as PerfilService
    participant PelS as PeliculaService
    participant DB as MySQL

    U->>V: Pulsa botón "+" en película (id=10)
    V->>MLC: POST /mi-lista/agregar/10 (AJAX, sesión activa)

    MLC->>PS: obtenerPorId(perfilActivoId)
    PS->>DB: SELECT * FROM perfiles WHERE id=?
    DB-->>PS: Perfil
    MLC->>PelS: obtenerPorId(10)
    PelS->>DB: SELECT * FROM peliculas WHERE id=10
    DB-->>PelS: Pelicula

    MLC->>MLS: agregar(perfil, pelicula)
    MLS->>DB: SELECT * FROM mi_lista WHERE perfil_id=? AND pelicula_id=?
    DB-->>MLS: Optional.empty() (no existe)
    MLS->>DB: INSERT INTO mi_lista (perfil_id, pelicula_id, fecha_agregado)
    DB-->>MLS: MiLista guardada
    MLS-->>MLC: MiLista

    MLC-->>V: 200 OK {success: true, message: "Añadido a Mi Lista"}
    V->>V: Cambia icono "+" por "✓" (sin recargar página)
    V-->>U: Botón actualizado visualmente
```

#### DS-04: Búsqueda de películas en tiempo real (AJAX)

```mermaid
sequenceDiagram
    actor U as Usuario
    participant V as Navegador (JS)
    participant BC as BuscadorController
    participant PelS as PeliculaService
    participant PelR as PeliculaRepository
    participant DB as MySQL

    U->>V: Escribe "incep" en el campo de búsqueda
    V->>V: JS debounce (300ms sin nueva tecla)
    V->>BC: GET /buscar?q=incep (AJAX)

    BC->>BC: Verifica perfilActivoId en sesión
    BC->>PelS: buscarPorTitulo("incep")
    PelS->>PelR: findByTituloContainingIgnoreCase("incep")
    PelR->>DB: SELECT * FROM peliculas WHERE titulo LIKE '%incep%'
    DB-->>PelR: [Inception, ...]
    PelR-->>PelS: List<Pelicula>
    PelS-->>BC: List<Pelicula>

    BC-->>V: render buscar/index.html (fragment o página completa)
    V-->>U: Lista de resultados actualizada
```

---

### Diagrama de Clases

#### Capa de Modelo (Entidades JPA)

```mermaid
classDiagram
    class Usuario {
        -Integer id
        -String email
        -String password
        -String plan
        -LocalDateTime fechaRegistro
        -String rol
        -Boolean activo
        +getMaxPerfiles() int
        +prePersist() void
    }

    class Perfil {
        -Integer id
        -Usuario usuario
        -String nombre
        -String avatarUrl
        -Boolean activo
        -LocalDateTime fechaCreacion
        +prePersist() void
    }

    class Pelicula {
        -Integer id
        -String titulo
        -String descripcion
        -Integer duracion
        -Integer anio
        -String urlImagen
        -String urlVideo
        -String urlHero
        -String director
        -Double valoracionImdb
        -Genero genero
        -Boolean destacada
        -Boolean novedad
        -LocalDateTime fechaCreacion
        +prePersist() void
    }

    class Genero {
        -Integer id
        -String nombre
        -String descripcion
    }

    class PerfilGenero {
        -Integer id
        -Perfil perfil
        -Genero genero
    }

    class MiLista {
        -Integer id
        -Perfil perfil
        -Pelicula pelicula
        -LocalDateTime fechaAgregado
        +prePersist() void
    }

    Usuario "1" --> "0..*" Perfil : tiene
    Perfil "1" --> "0..*" PerfilGenero : prefiere
    Genero "1" --> "0..*" PerfilGenero : incluido en
    Perfil "1" --> "0..*" MiLista : guarda
    Pelicula "1" --> "0..*" MiLista : guardada en
    Genero "1" --> "0..*" Pelicula : categoriza
```

#### Capa de Repositorios (Spring Data JPA)

```mermaid
classDiagram
    class UsuarioRepository {
        <<interface>>
        +findByEmail(String) Optional~Usuario~
        +findByActivoTrue() Iterable~Usuario~
        +count() long
    }

    class PerfilRepository {
        <<interface>>
        +findByUsuarioIdAndActivoTrue(Integer) List~Perfil~
        +countByUsuarioId(Integer) long
    }

    class PeliculaRepository {
        <<interface>>
        +findByDestacadaTrue() List~Pelicula~
        +findByNovedadTrue() List~Pelicula~
        +findByGeneroId(Integer) List~Pelicula~
        +findByTituloContainingIgnoreCase(String) List~Pelicula~
        +findTop5ByOrderByFechaCreacionDesc() List~Pelicula~
        +count() long
    }

    class GeneroRepository {
        <<interface>>
        +findByNombreIgnoreCase(String) Optional~Genero~
        +count() long
    }

    class PerfilGeneroRepository {
        <<interface>>
        +findByPerfilId(Integer) List~PerfilGenero~
        +deleteByPerfilId(Integer) void
    }

    class MiListaRepository {
        <<interface>>
        +findByPerfilIdOrderByFechaAgregadoDesc(Integer) List~MiLista~
        +findByPerfilIdAndPeliculaId(Integer, Integer) Optional~MiLista~
        +existsByPerfilIdAndPeliculaId(Integer, Integer) boolean
        +deleteByPerfilId(Integer) void
    }

    UsuarioRepository ..> Usuario : gestiona
    PerfilRepository ..> Perfil : gestiona
    PeliculaRepository ..> Pelicula : gestiona
    GeneroRepository ..> Genero : gestiona
    PerfilGeneroRepository ..> PerfilGenero : gestiona
    MiListaRepository ..> MiLista : gestiona
```

#### Capa de Servicios

```mermaid
classDiagram
    class UsuarioService {
        -UsuarioRepository usuarioRepository
        -PasswordEncoder passwordEncoder
        +findAll() List~Usuario~
        +obtenerPorId(Integer) Optional~Usuario~
        +buscarPorEmail(String) Optional~Usuario~
        +registrar(String, String, String) Usuario
        +guardar(Usuario) Usuario
        +eliminar(Integer) void
        +contar() long
    }

    class PerfilService {
        -PerfilRepository perfilRepository
        -PerfilGeneroRepository perfilGeneroRepository
        -MiListaRepository miListaRepository
        +obtenerPerfilesPorUsuario(Integer) List~Perfil~
        +obtenerPorId(Integer) Optional~Perfil~
        +guardar(Perfil) Perfil
        +crearPerfil(Usuario, String, String) Perfil
        +contarPerfilesPorUsuario(Integer) long
        +guardarPreferenciasGenero(Perfil, List~Genero~) void
        +eliminar(Integer) void
    }

    class PeliculaService {
        -PeliculaRepository peliculaRepository
        +obtenerTodas() List~Pelicula~
        +obtenerPorId(Integer) Optional~Pelicula~
        +obtenerDestacadas() List~Pelicula~
        +obtenerNovedades() List~Pelicula~
        +buscarPorTitulo(String) List~Pelicula~
        +obtenerPorGenero(Integer) List~Pelicula~
        +obtenerPorGeneros(List~Integer~) List~Pelicula~
        +guardar(Pelicula) Pelicula
        +eliminar(Integer) void
        +contar() long
        +obtenerRecientes(int) List~Pelicula~
    }

    class GeneroService {
        -GeneroRepository generoRepository
        +obtenerTodos() List~Genero~
        +obtenerPorId(Integer) Optional~Genero~
        +buscarPorNombre(String) Optional~Genero~
        +guardar(Genero) Genero
        +eliminar(Integer) void
        +contar() long
    }

    class MiListaService {
        -MiListaRepository miListaRepository
        +obtenerListaPorPerfil(Integer) List~MiLista~
        +estaEnMiLista(Integer, Integer) boolean
        +agregar(Perfil, Pelicula) MiLista
        +eliminar(Integer, Integer) void
    }

    UsuarioService --> UsuarioRepository : usa
    PerfilService --> PerfilRepository : usa
    PerfilService --> PerfilGeneroRepository : usa
    PerfilService --> MiListaRepository : usa
    PeliculaService --> PeliculaRepository : usa
    GeneroService --> GeneroRepository : usa
    MiListaService --> MiListaRepository : usa
```

#### Capa de Controladores (MVC completo)

```mermaid
classDiagram
    class AuthController {
        -UsuarioService usuarioService
        -PerfilService perfilService
        -GeneroService generoService
        +login() String
        +registroForm() String
        +registroProcesar() String
        +planForm() String
        +planProcesar() String
        +crearPerfilForm() String
        +crearPerfilProcesar() String
    }

    class HomeController {
        -PeliculaService peliculaService
        -GeneroService generoService
        -PerfilService perfilService
        +root() String
        +inicio() String
    }

    class PerfilController {
        -PerfilService perfilService
        -UsuarioService usuarioService
        -GeneroService generoService
        +seleccionarPerfil() String
        +activarPerfil() String
        +nuevoPerfil() String
        +editarPerfil() String
        +guardarPerfil() String
        +eliminarPerfil() String
    }

    class PeliculaController {
        -PeliculaService peliculaService
        -GeneroService generoService
        -MiListaService miListaService
        -PerfilService perfilService
        +detalle() String
    }

    class BuscadorController {
        -PeliculaService peliculaService
        +buscar() String
    }

    class MiListaController {
        -MiListaService miListaService
        -PeliculaService peliculaService
        -PerfilService perfilService
        +miLista() String
        +agregar() ResponseEntity
        +eliminar() ResponseEntity
    }

    class CuentaController {
        -UsuarioService usuarioService
        -PasswordEncoder passwordEncoder
        +miCuenta() String
        +cambiarPassword() String
        +cambiarPlan() String
    }

    class AdminController {
        -PeliculaService peliculaService
        -GeneroService generoService
        -UsuarioService usuarioService
        -PasswordEncoder passwordEncoder
        +dashboard() String
        +listaPeliculas() String
        +crearPelicula() String
        +editarPelicula() String
        +eliminarPelicula() String
        +listaGeneros() String
        +listaUsuarios() String
    }

    AuthController --> UsuarioService : usa
    AuthController --> PerfilService : usa
    AuthController --> GeneroService : usa
    HomeController --> PeliculaService : usa
    HomeController --> GeneroService : usa
    HomeController --> PerfilService : usa
    PerfilController --> PerfilService : usa
    PerfilController --> UsuarioService : usa
    PerfilController --> GeneroService : usa
    PeliculaController --> PeliculaService : usa
    PeliculaController --> GeneroService : usa
    PeliculaController --> MiListaService : usa
    PeliculaController --> PerfilService : usa
    MiListaController --> MiListaService : usa
    MiListaController --> PeliculaService : usa
    MiListaController --> PerfilService : usa
    BuscadorController --> PeliculaService : usa
    CuentaController --> UsuarioService : usa
    AdminController --> PeliculaService : usa
    AdminController --> GeneroService : usa
    AdminController --> UsuarioService : usa
```

---

## Autor

**Jorge Ruiz** — Proyecto académico DAW/DAM
