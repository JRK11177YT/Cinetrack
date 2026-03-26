USE cinetrack_db;


ALTER TABLE usuarios
ADD CONSTRAINT uq_usuarios_email UNIQUE (email);

ALTER TABLE generos
ADD CONSTRAINT uq_generos_nombre UNIQUE (nombre);

ALTER TABLE usuario_generos
ADD CONSTRAINT uq_usuario_genero UNIQUE (usuario_id, genero_id);

ALTER TABLE mi_lista
ADD CONSTRAINT uq_mi_lista UNIQUE (usuario_id, pelicula_id);

ALTER TABLE historial_visualizacion
ADD CONSTRAINT uq_historial_usuario_pelicula UNIQUE (usuario_id, pelicula_id);


CREATE INDEX idx_peliculas_titulo ON peliculas(titulo);
CREATE INDEX idx_peliculas_genero_id ON peliculas(genero_id);

CREATE INDEX idx_usuario_generos_usuario_id ON usuario_generos(usuario_id);
CREATE INDEX idx_usuario_generos_genero_id ON usuario_generos(genero_id);

CREATE INDEX idx_historial_usuario_id ON historial_visualizacion(usuario_id);
CREATE INDEX idx_historial_pelicula_id ON historial_visualizacion(pelicula_id);

CREATE INDEX idx_mi_lista_usuario_id ON mi_lista(usuario_id);
CREATE INDEX idx_mi_lista_pelicula_id ON mi_lista(pelicula_id);
