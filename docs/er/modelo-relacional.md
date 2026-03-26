# Modelo relacional de CineTrack

## Tablas principales

### usuarios

* id (PK)
* nombre
* email (UNIQUE)
* password
* imagen_perfil
* fecha_registro
* rol
* activo

### generos

* id (PK)
* nombre (UNIQUE)
* descripcion

### peliculas

* id (PK)
* titulo
* descripcion
* duracion
* anio
* url_imagen
* url_video
* genero_id (FK → generos.id)
* destacada
* fecha_creacion

### usuario_generos

* id (PK)
* usuario_id (FK → usuarios.id)
* genero_id (FK → generos.id)
* UNIQUE(usuario_id, genero_id)

### historial_visualizacion

* id (PK)
* usuario_id (FK → usuarios.id)
* pelicula_id (FK → peliculas.id)
* fecha_ultima_visualizacion
* progreso_segundos
* completada
* UNIQUE(usuario_id, pelicula_id)

### mi_lista

* id (PK)
* usuario_id (FK → usuarios.id)
* pelicula_id (FK → peliculas.id)
* fecha_agregado
* UNIQUE(usuario_id, pelicula_id)

## Relaciones

* generos 1:N peliculas
* usuarios N:M generos mediante usuario_generos
* usuarios 1:N historial_visualizacion
* peliculas 1:N historial_visualizacion
* usuarios 1:N mi_lista
* peliculas 1:N mi_lista
