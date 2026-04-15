USE cinetrack_db;


ALTER TABLE perfiles
ADD CONSTRAINT fk_perfiles_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;


ALTER TABLE peliculas
ADD CONSTRAINT fk_peliculas_genero
FOREIGN KEY (genero_id) REFERENCES generos(id);


ALTER TABLE perfil_generos
ADD CONSTRAINT fk_perfil_generos_perfil
FOREIGN KEY (perfil_id) REFERENCES perfiles(id) ON DELETE CASCADE;

ALTER TABLE perfil_generos
ADD CONSTRAINT fk_perfil_generos_genero
FOREIGN KEY (genero_id) REFERENCES generos(id);


ALTER TABLE historial_visualizacion
ADD CONSTRAINT fk_historial_perfil
FOREIGN KEY (perfil_id) REFERENCES perfiles(id) ON DELETE CASCADE;

ALTER TABLE historial_visualizacion
ADD CONSTRAINT fk_historial_pelicula
FOREIGN KEY (pelicula_id) REFERENCES peliculas(id);


ALTER TABLE mi_lista
ADD CONSTRAINT fk_mi_lista_perfil
FOREIGN KEY (perfil_id) REFERENCES perfiles(id) ON DELETE CASCADE;

ALTER TABLE mi_lista
ADD CONSTRAINT fk_mi_lista_pelicula
FOREIGN KEY (pelicula_id) REFERENCES peliculas(id);
