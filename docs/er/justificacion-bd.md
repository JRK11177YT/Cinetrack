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

* usuario_generos
* historial_visualizacion
* mi_lista

Estas tablas permiten implementar funcionalidades avanzadas como:

* personalización según gustos
* continuar viendo
* historial de reproducción
* lista de contenido para ver más adelante

## Decisiones relevantes

### usuario_generos

Se utiliza una tabla intermedia porque un usuario puede tener varios gustos y un mismo género puede estar asociado a muchos usuarios.

### historial_visualizacion

Se ha diseñado con una sola fila por combinación de usuario y película para mantener el progreso actual de visualización y facilitar la funcionalidad de “seguir viendo”.

### mi_lista

Se separa del historial porque representa una intención de consumo futuro, no una reproducción iniciada.

## Evolución futura

El diseño permite añadir en el futuro nuevas tablas como:

* valoraciones
* comentarios
* favoritos
* recomendaciones
* listas personalizadas

Esto convierte la base de datos en una estructura modular y extensible, adecuada para el crecimiento progresivo del proyecto.
