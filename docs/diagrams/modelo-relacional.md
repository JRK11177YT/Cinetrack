# Modelo relacional de CineTrack

## Diagrama Entidad-Relación

```mermaid
erDiagram
    usuarios {
        INT id PK
        VARCHAR_150 email UK
        VARCHAR_255 password
        VARCHAR_20 plan
        DATETIME fecha_registro
        VARCHAR_20 rol
        BOOLEAN activo
    }

    perfiles {
        INT id PK
        INT usuario_id FK
        VARCHAR_50 nombre
        VARCHAR_255 avatar_url
        BOOLEAN activo
        DATETIME fecha_creacion
    }

    generos {
        INT id PK
        VARCHAR_50 nombre UK
        VARCHAR_255 descripcion
    }

    peliculas {
        INT id PK
        VARCHAR_150 titulo
        TEXT descripcion
        INT duracion
        INT anio
        VARCHAR_255 url_imagen
        VARCHAR_255 url_hero
        VARCHAR_255 url_video
        VARCHAR_150 director
        DOUBLE valoracion_imdb
        INT genero_id FK
        BOOLEAN destacada
        BOOLEAN novedad
        DATETIME fecha_creacion
    }

    perfil_generos {
        INT id PK
        INT perfil_id FK
        INT genero_id FK
    }

    mi_lista {
        INT id PK
        INT perfil_id FK
        INT pelicula_id FK
        DATETIME fecha_agregado
    }

    usuarios ||--o{ perfiles : "tiene (CASCADE)"
    generos ||--o{ peliculas : "categoriza"
    perfiles ||--o{ perfil_generos : "prefiere (CASCADE)"
    generos ||--o{ perfil_generos : "incluido en"
    perfiles ||--o{ mi_lista : "guarda (CASCADE)"
    peliculas ||--o{ mi_lista : "guardada en"
```

## Resumen de restricciones

| Tabla | Restricción única |
|---|---|
| `usuarios` | `email` |
| `generos` | `nombre` |
| `perfil_generos` | `(perfil_id, genero_id)` |
| `mi_lista` | `(perfil_id, pelicula_id)` |

## Relaciones

| Relación | Cardinalidad | ON DELETE |
|---|---|---|
| `usuarios` → `perfiles` | 1:N | CASCADE |
| `generos` → `peliculas` | 1:N | RESTRICT |
| `perfiles` → `perfil_generos` | 1:N | CASCADE |
| `generos` → `perfil_generos` | 1:N | RESTRICT |
| `perfiles` → `mi_lista` | 1:N | CASCADE |
| `peliculas` → `mi_lista` | 1:N | RESTRICT |
