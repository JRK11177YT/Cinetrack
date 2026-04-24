# Diagramas de Secuencia — CineTrack

Se documentan los 4 flujos más relevantes y defendibles del sistema.

---

## DS-01: Registro completo de usuario

Flujo desde que el visitante accede a `/registro` hasta que queda autenticado con su primer perfil creado.

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

---

## DS-02: Inicio de sesión y selección de perfil

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

---

## DS-03: Añadir película a Mi Lista (flujo AJAX)

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

---

## DS-04: Búsqueda de películas en tiempo real (AJAX)

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
