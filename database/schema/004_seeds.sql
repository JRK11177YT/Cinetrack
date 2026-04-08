USE cinetrack_db;


-- =============================================
-- Géneros cinematográficos
-- =============================================
INSERT INTO generos (nombre, descripcion) VALUES
('Acción', 'Películas con ritmo intenso, enfrentamientos y secuencias dinámicas'),
('Drama', 'Historias centradas en conflictos emocionales y desarrollo de personajes'),
('Ciencia Ficción', 'Películas con tecnología avanzada, futuro o elementos imaginarios'),
('Comedia', 'Películas orientadas al humor y al entretenimiento'),
('Thriller', 'Historias de tensión, suspense e intriga'),
('Aventura', 'Películas centradas en exploración, viajes y desafíos');


-- =============================================
-- Catálogo de películas
-- =============================================
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Horizonte Final', 'Una misión espacial descubre una señal procedente de una nave desaparecida hace años. La tripulación deberá enfrentarse a lo desconocido en los confines del universo.', 124, 2021, '/img/movies/horizonte-final.jpg', '/videos/horizonte-final.mp4', 3, TRUE),
('Código Rojo', 'Un antiguo agente debe regresar a la acción para detener una amenaza internacional que pone en peligro a millones de personas.', 112, 2022, '/img/movies/codigo-rojo.jpg', '/videos/codigo-rojo.mp4', 1, TRUE),
('Última Parada', 'Un thriller psicológico en el que nada es lo que parece durante un viaje nocturno en un tren que no debería existir.', 98, 2020, '/img/movies/ultima-parada.jpg', '/videos/ultima-parada.mp4', 5, FALSE),
('Rumbo al Sur', 'Una aventura de supervivencia y descubrimiento en territorios desconocidos de la Patagonia más salvaje.', 130, 2019, '/img/movies/rumbo-al-sur.jpg', '/videos/rumbo-al-sur.mp4', 6, FALSE),
('Entre Dos Mundos', 'Drama contemporáneo sobre decisiones personales, pérdida y la reconstrucción de una vida rota en una ciudad que nunca duerme.', 116, 2023, '/img/movies/entre-dos-mundos.jpg', '/videos/entre-dos-mundos.mp4', 2, TRUE),
('Furia Eléctrica', 'Persecuciones a alta velocidad y combates cuerpo a cuerpo en las calles de Tokio. Sin reglas, sin límites.', 108, 2024, '/img/movies/furia-electrica.jpg', '/videos/furia-electrica.mp4', 1, FALSE),
('La Última Risa', 'Un comediante fracasado recibe una segunda oportunidad que cambiará su vida... y la de todo un barrio.', 95, 2023, '/img/movies/la-ultima-risa.jpg', '/videos/la-ultima-risa.mp4', 4, FALSE),
('Nebulosa', 'En un futuro donde la Tierra es inhabitable, una científica descubre una forma de comunicarse con civilizaciones de otras galaxias.', 142, 2024, '/img/movies/nebulosa.jpg', '/videos/nebulosa.mp4', 3, TRUE),
('Cenizas del Ayer', 'Una familia marcada por secretos enfrenta su pasado cuando un descubrimiento inesperado lo pone todo en cuestión.', 121, 2022, '/img/movies/cenizas-del-ayer.jpg', '/videos/cenizas-del-ayer.mp4', 2, FALSE),
('Ruta Salvaje', 'Dos hermanos emprenden un viaje por carretera que se convierte en la mayor aventura de sus vidas.', 118, 2021, '/img/movies/ruta-salvaje.jpg', '/videos/ruta-salvaje.mp4', 6, FALSE),
('Sin Rastro', 'Un detective retirado vuelve al juego cuando su hija desaparece sin dejar ninguna pista.', 105, 2023, '/img/movies/sin-rastro.jpg', '/videos/sin-rastro.mp4', 5, TRUE),
('Gravedad Cero', 'Acción vertiginosa en una estación espacial donde un grupo de astronautas lucha por sobrevivir tras un sabotaje.', 110, 2024, '/img/movies/gravedad-cero.jpg', '/videos/gravedad-cero.mp4', 1, FALSE),
('El Plan Perfecto', 'Cuando cuatro amigos idean la broma del siglo, nada sale como esperaban. Comedia de enredos con giros inesperados.', 102, 2022, '/img/movies/el-plan-perfecto.jpg', '/videos/el-plan-perfecto.mp4', 4, FALSE),
('Proyecto Aurora', 'Una conspiración gubernamental amenaza con reescribir la historia. Solo una periodista conoce la verdad.', 135, 2024, '/img/movies/proyecto-aurora.jpg', '/videos/proyecto-aurora.mp4', 3, FALSE);


-- =============================================
-- NOTA: El usuario admin y su perfil se crean automáticamente
-- desde DatabaseSeederConfig.java con contraseña BCrypt.
-- Credenciales: admin@cinetrack.com / admin123
-- =============================================
