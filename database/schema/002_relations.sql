USE cinetrack_db;


ALTER TABLE peliculas
ADD CONSTRAINT fk_peliculas_genero
FOREIGN KEY (genero_id) REFERENCES generos(id);


ALTER TABLE usuario_generos
ADD CONSTRAINT fk_usuario_generos_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);


ALTER TABLE usuario_generos
ADD CONSTRAINT fk_usuario_generos_genero
FOREIGN KEY (genero_id) REFERENCES generos(id);


ALTER TABLE historial_visualizacion
ADD CONSTRAINT fk_historial_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);


ALTER TABLE historial_visualizacion
ADD CONSTRAINT fk_historial_pelicula
FOREIGN KEY (pelicula_id) REFERENCES peliculas(id);


ALTER TABLE mi_lista
ADD CONSTRAINT fk_mi_lista_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);


ALTER TABLE mi_lista
ADD CONSTRAINT fk_mi_lista_pelicula
FOREIGN KEY (pelicula_id) REFERENCES peliculas(id);
