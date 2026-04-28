-- =============================================
-- CineTrack — Esquema de Base de Datos
-- Solo estructura: BD, usuario, tablas, índices
-- y claves foráneas. Sin datos.
--
-- Ejecutar PRIMERO. Después ejecutar data.sql.
--
-- PowerShell (Windows/XAMPP):
--   .\scripts\setup-db.ps1
-- Bash (Linux/macOS):
--   bash scripts/setup-db.sh
-- =============================================

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- =============================================
-- BASE DE DATOS
-- =============================================
CREATE DATABASE IF NOT EXISTS cinetrack_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE cinetrack_db;

-- =============================================
-- USUARIO DEDICADO CINETRACK
-- Se crea con localhost Y 127.0.0.1 para cubrir
-- ambas resoluciones en Windows con XAMPP.
-- =============================================
CREATE USER IF NOT EXISTS 'cinetrack_user'@'localhost'  IDENTIFIED BY 'Cinetrack2024!';
CREATE USER IF NOT EXISTS 'cinetrack_user'@'127.0.0.1' IDENTIFIED BY 'Cinetrack2024!';
GRANT ALL PRIVILEGES ON cinetrack_db.* TO 'cinetrack_user'@'localhost';
GRANT ALL PRIVILEGES ON cinetrack_db.* TO 'cinetrack_user'@'127.0.0.1';
FLUSH PRIVILEGES;

SET FOREIGN_KEY_CHECKS = 0;

-- =============================================
-- TABLAS
-- =============================================

CREATE TABLE IF NOT EXISTS `usuarios` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(150) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `plan` VARCHAR(20) NOT NULL DEFAULT 'BASICO',
    `fecha_registro` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `rol` VARCHAR(20) NOT NULL DEFAULT 'USER',
    `activo` TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `perfiles` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `usuario_id` INT(11) NOT NULL,
    `nombre` VARCHAR(50) NOT NULL,
    `avatar_url` VARCHAR(255) DEFAULT 'default',
    `activo` TINYINT(1) NOT NULL DEFAULT 1,
    `fecha_creacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `generos` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(50) NOT NULL,
    `descripcion` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `peliculas` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `titulo` VARCHAR(150) NOT NULL,
    `descripcion` TEXT NOT NULL,
    `duracion` INT(11) NOT NULL,
    `anio` INT(11) NOT NULL,
    `url_imagen` VARCHAR(255) DEFAULT NULL,
    `url_video` VARCHAR(255) DEFAULT NULL,
    `genero_id` INT(11) NOT NULL,
    `destacada` TINYINT(1) NOT NULL DEFAULT 0,
    `fecha_creacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `novedad` BIT(1) NOT NULL DEFAULT b'0',
    `url_hero` VARCHAR(255) DEFAULT NULL,
    `director` VARCHAR(150) DEFAULT NULL,
    `valoracion_imdb` DOUBLE DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `perfil_generos` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `perfil_id` INT(11) NOT NULL,
    `genero_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `mi_lista` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `perfil_id` INT(11) NOT NULL,
    `pelicula_id` INT(11) NOT NULL,
    `fecha_agregado` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- RESTRICCIONES E ÍNDICES
-- =============================================

ALTER TABLE `usuarios`
    ADD CONSTRAINT `uq_usuarios_email` UNIQUE (`email`);

ALTER TABLE `generos`
    ADD CONSTRAINT `uq_generos_nombre` UNIQUE (`nombre`);

ALTER TABLE `perfil_generos`
    ADD CONSTRAINT `uq_perfil_genero` UNIQUE (`perfil_id`, `genero_id`);

ALTER TABLE `mi_lista`
    ADD CONSTRAINT `uq_mi_lista` UNIQUE (`perfil_id`, `pelicula_id`);

CREATE INDEX idx_perfiles_usuario_id      ON perfiles(usuario_id);
CREATE INDEX idx_peliculas_titulo         ON peliculas(titulo);
CREATE INDEX idx_peliculas_genero_id      ON peliculas(genero_id);
CREATE INDEX idx_perfil_generos_perfil_id ON perfil_generos(perfil_id);
CREATE INDEX idx_perfil_generos_genero_id ON perfil_generos(genero_id);
CREATE INDEX idx_mi_lista_perfil_id       ON mi_lista(perfil_id);
CREATE INDEX idx_mi_lista_pelicula_id     ON mi_lista(pelicula_id);

-- =============================================
-- RELACIONES / FOREIGN KEYS
-- =============================================

ALTER TABLE `perfiles`
    ADD CONSTRAINT `fk_perfiles_usuario`
    FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`) ON DELETE CASCADE;

ALTER TABLE `peliculas`
    ADD CONSTRAINT `fk_peliculas_genero`
    FOREIGN KEY (`genero_id`) REFERENCES `generos`(`id`);

ALTER TABLE `perfil_generos`
    ADD CONSTRAINT `fk_perfil_generos_perfil`
    FOREIGN KEY (`perfil_id`) REFERENCES `perfiles`(`id`) ON DELETE CASCADE;

ALTER TABLE `perfil_generos`
    ADD CONSTRAINT `fk_perfil_generos_genero`
    FOREIGN KEY (`genero_id`) REFERENCES `generos`(`id`);

ALTER TABLE `mi_lista`
    ADD CONSTRAINT `fk_mi_lista_perfil`
    FOREIGN KEY (`perfil_id`) REFERENCES `perfiles`(`id`) ON DELETE CASCADE;

ALTER TABLE `mi_lista`
    ADD CONSTRAINT `fk_mi_lista_pelicula`
    FOREIGN KEY (`pelicula_id`) REFERENCES `peliculas`(`id`);

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- El usuario admin se crea automáticamente al
-- arrancar la aplicación (DatabaseSeederConfig.java).
-- Credenciales: admin@cinetrack.com / admin123
-- =============================================

