# Justificación del diseño de base de datos

## Objetivo del diseño

La base de datos de CineTrack se ha diseñado para soportar una plataforma de streaming inspirada en servicios como Netflix, manteniendo un equilibrio entre realismo funcional, simplicidad de implementación y capacidad de evolución futura.

## Criterios de diseño

Se han seguido los siguientes criterios:

* separar entidades principales de entidades de interacción
* evitar duplicidad de información
* mantener relaciones coherentes y defendibles
* facilitar la integración posterior con Spring Data JPA
* permitir futuras ampliaciones sin rehacer la base completa

## Entidades principales

Las entidades núcleo del sistema son:

* usuarios
* peliculas
* generos

Estas representan la información estructural básica del sistema.

## Entidades de personalización e interacción

Para representar comportamiento real del usuario, se han añadido:

* perfil_generos
* mi_lista

Estas tablas permiten implementar funcionalidades avanzadas como:

* personalización del catálogo según gustos del perfil
* lista de contenido para ver más adelante

## Decisiones relevantes

### perfil_generos

Se utiliza una tabla intermedia porque un perfil puede preferir varios géneros y un mismo género puede estar asociado a muchos perfiles. La restricción única compuesta `(perfil_id, genero_id)` evita duplicados.

Se diseña a nivel de **perfil** (no de usuario) para permitir que cada miembro de la cuenta tenga preferencias independientes, siguiendo el modelo de plataformas como Netflix.

### mi_lista

Representa una intención de consumo futuro por parte de un perfil concreto. La restricción única compuesta `(perfil_id, pelicula_id)` garantiza que una película no se añada dos veces a la misma lista.

## Evolución futura

El diseño permite añadir en el futuro nuevas tablas como:

* valoraciones
* comentarios
* recomendaciones automáticas
* listas personalizadas

Esto convierte la base de datos en una estructura modular y extensible, adecuada para el crecimiento progresivo del proyecto.