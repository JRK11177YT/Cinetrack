USE cinetrack_db;


INSERT INTO generos (nombre, descripcion) VALUES
('Acción', 'Películas con ritmo intenso, enfrentamientos y secuencias dinámicas'),
('Drama', 'Historias centradas en conflictos emocionales y desarrollo de personajes'),
('Ciencia Ficción', 'Películas con tecnología avanzada, futuro o elementos imaginarios'),
('Comedia', 'Películas orientadas al humor y al entretenimiento'),
('Thriller', 'Historias de tensión, suspense e intriga'),
('Aventura', 'Películas centradas en exploración, viajes y desafíos');


INSERT INTO usuarios (nombre, email, password, imagen_perfil, rol, activo) VALUES
('Jorge Ruiz', '[jorge@cinetrack.com](mailto:jorge@cinetrack.com)', '123456', 'perfil1.png', 'ADMIN', TRUE);


INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
(
'Horizonte Final',
'Una misión espacial descubre una señal procedente de una nave desaparecida hace años.',
124,
2021,
'img/horizonte-final.jpg',
'videos/horizonte-final.mp4',
3,
TRUE
),
(
'Código Rojo',
'Un antiguo agente debe regresar a la acción para detener una amenaza internacional.',
112,
2022,
'img/codigo-rojo.jpg',
'videos/codigo-rojo.mp4',
1,
TRUE
),
(
'Última Parada',
'Un thriller psicológico en el que nada es lo que parece durante un viaje nocturno.',
98,
2020,
'img/ultima-parada.jpg',
'videos/ultima-parada.mp4',
5,
FALSE
),
(
'Rumbo al Sur',
'Una aventura de supervivencia y descubrimiento en territorios desconocidos.',
130,
2019,
'img/rumbo-al-sur.jpg',
'videos/rumbo-al-sur.mp4',
6,
FALSE
),
(
'Entre Dos Mundos',
'Drama contemporáneo sobre decisiones personales, pérdida y reconstrucción.',
116,
2023,
'img/entre-dos-mundos.jpg',
'videos/entre-dos-mundos.mp4',
2,
TRUE
);


INSERT INTO usuario_generos (usuario_id, genero_id) VALUES
(1, 1),
(1, 3),
(1, 5);


INSERT INTO mi_lista (usuario_id, pelicula_id) VALUES
(1, 4),
(1, 5);


INSERT INTO historial_visualizacion (usuario_id, pelicula_id, progreso_segundos, completada) VALUES
(1, 1, 1840, FALSE),
(1, 2, 6720, TRUE);
