USE cinetrack_db;


-- =============================================
-- Tabla: usuarios (Cuenta / Suscripción)
-- Representa a la persona que paga la suscripción.
-- =============================================
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    plan VARCHAR(20) NOT NULL DEFAULT 'BASICO',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rol VARCHAR(20) NOT NULL DEFAULT 'USER',
    activo BOOLEAN NOT NULL DEFAULT TRUE
);


-- =============================================
-- Tabla: perfiles (Perfil dentro de una cuenta)
-- Cada suscripción puede tener varios perfiles.
-- =============================================
CREATE TABLE perfiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    avatar_url VARCHAR(255) DEFAULT 'default',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =============================================
-- Tabla: generos
-- =============================================
CREATE TABLE generos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);


-- =============================================
-- Tabla: peliculas
-- =============================================
CREATE TABLE peliculas (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(150) NOT NULL,
    descripcion     TEXT NOT NULL,
    duracion        INT NOT NULL,
    anio            INT NOT NULL,
    url_imagen      VARCHAR(255),
    url_video       VARCHAR(255),
    url_hero        VARCHAR(255),
    director        VARCHAR(150),
    valoracion_imdb DOUBLE,
    genero_id       INT NOT NULL,
    destacada       BOOLEAN NOT NULL DEFAULT FALSE,
    novedad         BIT(1) NOT NULL DEFAULT b'0',
    fecha_creacion  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =============================================
-- Tabla: perfil_generos (Preferencias de género por perfil)
-- =============================================
CREATE TABLE perfil_generos (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    perfil_id INT NOT NULL,
    genero_id INT NOT NULL
);


-- =============================================
-- Tabla: mi_lista (Películas guardadas por perfil)
-- =============================================
CREATE TABLE mi_lista (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    perfil_id      INT NOT NULL,
    pelicula_id    INT NOT NULL,
    fecha_agregado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


