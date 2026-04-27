USE cinetrack_db;


-- Restricciones de unicidad
ALTER TABLE usuarios
ADD CONSTRAINT uq_usuarios_email UNIQUE (email);

ALTER TABLE generos
ADD CONSTRAINT uq_generos_nombre UNIQUE (nombre);

ALTER TABLE perfil_generos
ADD CONSTRAINT uq_perfil_genero UNIQUE (perfil_id, genero_id);

ALTER TABLE mi_lista
ADD CONSTRAINT uq_mi_lista UNIQUE (perfil_id, pelicula_id);

-- Índices de rendimiento
CREATE INDEX idx_perfiles_usuario_id ON perfiles(usuario_id);

CREATE INDEX idx_peliculas_titulo    ON peliculas(titulo);
CREATE INDEX idx_peliculas_genero_id ON peliculas(genero_id);

CREATE INDEX idx_perfil_generos_perfil_id ON perfil_generos(perfil_id);
CREATE INDEX idx_perfil_generos_genero_id ON perfil_generos(genero_id);

CREATE INDEX idx_mi_lista_perfil_id   ON mi_lista(perfil_id);
CREATE INDEX idx_mi_lista_pelicula_id ON mi_lista(pelicula_id);
