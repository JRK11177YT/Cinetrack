-- =============================================
-- CineTrack — Script de Inicialización Completo
-- Ejecutar con (PowerShell):
--   Get-Content database/init.sql | mysql -u root
-- O desde XAMPP: source database/init.sql
--
-- IMPORTANTE: Este script es la fuente de verdad
-- canónica del proyecto. Cualquier clon de GitHub
-- que ejecute este script obtendrá exactamente el
-- mismo estado que el portátil original.
-- =============================================

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS cinetrack_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE cinetrack_db;

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

CREATE TABLE IF NOT EXISTS `historial_visualizacion` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `perfil_id` INT(11) NOT NULL,
    `pelicula_id` INT(11) NOT NULL,
    `fecha_ultima_visualizacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `progreso_segundos` INT(11) NOT NULL DEFAULT 0,
    `completada` TINYINT(1) NOT NULL DEFAULT 0,
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

ALTER TABLE `historial_visualizacion`
    ADD CONSTRAINT `uq_historial_perfil_pelicula` UNIQUE (`perfil_id`, `pelicula_id`);

CREATE INDEX idx_perfiles_usuario_id      ON perfiles(usuario_id);
CREATE INDEX idx_peliculas_titulo         ON peliculas(titulo);
CREATE INDEX idx_peliculas_genero_id      ON peliculas(genero_id);
CREATE INDEX idx_perfil_generos_perfil_id ON perfil_generos(perfil_id);
CREATE INDEX idx_perfil_generos_genero_id ON perfil_generos(genero_id);
CREATE INDEX idx_mi_lista_perfil_id       ON mi_lista(perfil_id);
CREATE INDEX idx_mi_lista_pelicula_id     ON mi_lista(pelicula_id);
CREATE INDEX idx_historial_perfil_id      ON historial_visualizacion(perfil_id);
CREATE INDEX idx_historial_pelicula_id    ON historial_visualizacion(pelicula_id);

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

ALTER TABLE `historial_visualizacion`
    ADD CONSTRAINT `fk_historial_perfil`
    FOREIGN KEY (`perfil_id`) REFERENCES `perfiles`(`id`) ON DELETE CASCADE;

ALTER TABLE `historial_visualizacion`
    ADD CONSTRAINT `fk_historial_pelicula`
    FOREIGN KEY (`pelicula_id`) REFERENCES `peliculas`(`id`);


-- =============================================
-- DATOS INICIALES
-- =============================================

INSERT INTO `generos` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Acción',          'Películas con ritmo intenso, enfrentamientos y secuencias dinámicas'),
(2, 'Drama',           'Historias centradas en conflictos emocionales y desarrollo de personajes'),
(3, 'Ciencia Ficción', 'Películas con tecnología avanzada, futuro o elementos imaginarios'),
(4, 'Comedia',         'Películas orientadas al humor y al entretenimiento'),
(5, 'Thriller',        'Historias de tensión, suspense e intriga'),
(6, 'Aventura',        'Películas centradas en exploración, viajes y desafíos');

ALTER TABLE `generos` MODIFY `id` INT(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

-- ============================================================
-- PELÍCULAS — con URLs, director y valoración IMDb correctos.
-- Los ficheros UUID referenciados están en uploads/ (trackeado
-- en git), por lo que cualquier clon tendrá los archivos.
-- ============================================================

INSERT INTO `peliculas` (`id`, `titulo`, `descripcion`, `duracion`, `anio`, `url_imagen`, `url_video`, `genero_id`, `destacada`, `novedad`, `url_hero`, `director`, `valoracion_imdb`) VALUES
(1,  'Top Gun',                'Maverick es un joven e impulsivo piloto de la Armada que ingresa en la prestigiosa escuela de vuelo Top Gun, donde competirá contra los mejores aviadores del país mientras lidia con la sombra del legado de su padre.', 110, 1986, '/uploads/peliculas/imagenes/db54a3e2-7589-4cb4-aef4-ee74af38c5d7.jpg',  '/uploads/peliculas/videos/c41a4a21-c7c8-486c-9aba-fd4f96f2d709.mp4', 1, 1, b'1', '/uploads/peliculas/heroes/14b3865c-5cfd-4c88-a459-44f69b082148.webp', 'Tony Scott',                        6.9),
(2,  'Top Gun: Maverick',      'Treinta años después, Pete Mitchell sigue volando como uno de los mejores pilotos de la Armada. Ahora debe entrenar a una nueva generación de pilotos para una misión casi imposible que exige el sacrificio definitivo.', 131, 2022, '/uploads/peliculas/imagenes/ba2b6f7f-1bc0-4644-8506-4be36d43164c.jpg',  '/uploads/peliculas/videos/0589f4f2-26df-446f-b89a-3321d9dbd004.mp4', 1, 1, b'0', '/uploads/peliculas/heroes/41ec24fc-06ef-4dbf-b2bf-406cfe1b08a3.webp', 'Joseph Kosinski',                   8.3),
(3,  'Saving Private Ryan',    'Tras el desembarco de Normandía, el capitán Miller recibe la orden de encontrar y traer de vuelta al soldado James Ryan, cuyos tres hermanos han muerto en combate, en una arriesgada misión tras las líneas enemigas.', 169, 1998, '/uploads/peliculas/imagenes/54782809-ce9c-4820-afd9-e9036c190a8a.jpg',  NULL, 1, 1, b'1', '/uploads/peliculas/heroes/2d9a09e0-5fa1-4ba2-8da6-cbb42ba4d806.jpg',  'Steven Spielberg',                  8.6),
(4,  'Pearl Harbor',           'Dos amigos pilotos se enfrentan a la guerra y al amor cuando el ataque japonés a Pearl Harbor cambia sus vidas para siempre, arrastrándolos a uno de los capítulos más devastadores de la Segunda Guerra Mundial.', 183, 2001, '/uploads/peliculas/imagenes/47dcfc35-b2b9-4e2a-af11-89050d3b40d2.webp', NULL, 1, 0, b'0', NULL, 'Michael Bay',                       6.0),
(5,  'Gladiator',              'Máximo, el general más leal de Roma, es traicionado por el nuevo emperador y reducido a esclavitud. Convertido en gladiador, luchará en la arena del Coliseo para vengar a su familia y devolver la justicia al imperio.', 155, 2000, '/uploads/peliculas/imagenes/30e0cb91-b2ad-4a22-a897-b7a6be58b285.jpg',  NULL, 1, 1, b'0', NULL, 'Ridley Scott',                      8.5),
(6,  'Braveheart',             'William Wallace, un guerrero escocés del siglo XIII, lidera una feroz rebelión contra la tiranía del rey Eduardo I de Inglaterra en una lucha épica por la libertad de su pueblo.', 178, 1995, '/uploads/peliculas/imagenes/76a78391-ffbc-4274-b9df-eb60cf9b1a26.jpg',  NULL, 1, 0, b'0', NULL, 'Mel Gibson',                        8.3),
(7,  'The Avengers',           'Cuando una amenaza global sin precedentes pone en peligro la Tierra, Nick Fury reúne a un equipo de superhéroes extraordinarios — Iron Man, Capitán América, Thor, Hulk, Viuda Negra y Ojo de Halcón — para salvar al mundo.', 143, 2012, '/uploads/peliculas/imagenes/eae85653-d01e-47c0-bc7e-f0c101ef4496.jpg',  NULL, 1, 1, b'0', NULL, 'Joss Whedon',                       8.0),
(8,  'The Godfather',          'Don Vito Corleone dirige la familia mafiosa más poderosa de Nueva York. Cuando un intento de asesinato lo deja al borde de la muerte, su hijo menor Michael se ve arrastrado al oscuro mundo del crimen organizado.', 175, 1972, '/uploads/peliculas/imagenes/e8a3b846-3c0c-45a5-b2b9-89ff8afd3b91.jpg',  NULL, 2, 1, b'0', '/uploads/peliculas/heroes/a328b4f3-c769-418e-80c1-036a1fd51153.jpg',  'Francis Ford Coppola',              9.2),
(9,  'La Lista de Schindler',  'Oskar Schindler, un empresario alemán, arriesga su fortuna y su vida para salvar a más de mil judíos del Holocausto empleándolos en su fábrica durante la ocupación nazi de Polonia.', 195, 1993, '/uploads/peliculas/imagenes/7895a0de-69c5-4fde-ac84-20bd122e2d0f.jpg',  NULL, 2, 1, b'0', NULL, 'Steven Spielberg',                  8.9),
(10, 'La Sociedad de la Nieve','En 1972, un avión con 45 pasajeros se estrella en los Andes. Los supervivientes deben enfrentarse al frío extremo, el hambre y decisiones imposibles para mantenerse con vida durante 72 días en la montaña.', 144, 2023, '/uploads/peliculas/imagenes/875caf70-45ee-4f11-ac71-8f3c390a7621.webp', NULL, 2, 1, b'0', NULL, 'J.A. Bayona',                       8.0),
(11, 'La La Land',             'En Los Ángeles, una aspirante a actriz y un pianista de jazz se enamoran mientras persiguen sus sueños. Pero el éxito profesional pondrá a prueba una relación que parecía destinada a durar para siempre.', 128, 2016, '/uploads/peliculas/imagenes/c70e2625-1e7b-435d-852f-89035b00f165.jpg',  NULL, 2, 0, b'0', NULL, 'Damien Chazelle',                   8.0),
(12, 'Forrest Gump',           'Forrest Gump, un hombre de buen corazón y capacidad intelectual limitada, vive una vida extraordinaria que lo lleva a ser testigo y protagonista involuntario de los momentos más importantes de la historia de Estados Unidos.', 142, 1994, '/uploads/peliculas/imagenes/b0be8487-6001-4117-9d30-2c9d54b8e818.jpg',  NULL, 2, 1, b'0', NULL, 'Robert Zemeckis',                   8.8),
(13, 'The Shawshank Redemption','Andy Dufresne, un banquero condenado injustamente por el asesinato de su esposa, forma una inesperada amistad con Red dentro de la prisión de Shawshank mientras mantiene viva la esperanza de recuperar su libertad.', 142, 1994, '/uploads/peliculas/imagenes/8d2eb9c7-252d-4fa2-8570-bb8e8a177216.jpg',  NULL, 2, 1, b'0', NULL, 'Frank Darabont',                    9.3),
(14, 'Goodfellas',             'Henry Hill crece en el seno de la mafia neoyorquina y asciende junto a sus compañeros Tommy y Jimmy hasta lo más alto del crimen organizado, en un mundo donde la lealtad y la traición conviven a diario.', 146, 1990, '/uploads/peliculas/imagenes/9f82aaf6-e3b6-4eca-8e41-7e6460e9fefd.jpg',  NULL, 2, 0, b'0', NULL, 'Martin Scorsese',                   8.7),
(15, 'Interstellar',           'En un futuro donde la Tierra agoniza, un grupo de astronautas viaja a través de un agujero de gusano en busca de un nuevo hogar para la humanidad, enfrentándose a la dilatación del tiempo y a decisiones que desafían el amor y la física.', 169, 2014, '/uploads/peliculas/imagenes/584393df-ccee-4e47-ae25-54046476121b.jpg',  NULL, 3, 1, b'0', NULL, 'Christopher Nolan',                 8.7),
(16, 'The Matrix',             'Thomas Anderson descubre que la realidad que conoce es una simulación creada por máquinas. Bajo el nombre de Neo, se une a un grupo de rebeldes para liberar a la humanidad de su prisión digital.', 136, 1999, '/uploads/peliculas/imagenes/df5266ec-e553-447e-a3c3-949d6a0e0813.jpg',  NULL, 3, 1, b'0', NULL, 'Lana y Lilly Wachowski',            8.7),
(17, 'Inception',              'Dom Cobb es un ladrón especializado en infiltrarse en los sueños para robar secretos. Para volver a casa con sus hijos, acepta una misión imposible: implantar una idea en la mente de alguien a través de múltiples niveles de sueños.', 148, 2010, '/uploads/peliculas/imagenes/7ce3ed35-28e6-46ec-b05e-38a4515d30b6.jpg',  NULL, 3, 0, b'0', NULL, 'Christopher Nolan',                 8.8),
(18, 'Blade Runner 2049',      'El oficial K, un replicante de nueva generación, descubre un secreto enterrado durante décadas que podría desatar una guerra entre humanos y replicantes, y que lo lleva a buscar a Rick Deckard, desaparecido hace treinta años.', 164, 2017, '/uploads/peliculas/imagenes/4f1c56b7-7b2a-426b-a87a-9b83e3aef9d5.jpg',  NULL, 3, 0, b'0', '/uploads/peliculas/heroes/8ab636b6-b590-457b-8a95-84273452167a.jpg',  'Denis Villeneuve',                  8.0),
(19, 'Back to the Future',     'Marty McFly viaja accidentalmente a 1955 en un DeLorean convertido en máquina del tiempo por el excéntrico Doc Brown. Ahora debe asegurarse de que sus padres se enamoren o él dejará de existir.', 116, 1985, '/uploads/peliculas/imagenes/87bb3526-653d-4bec-be13-2fd17a034a4c.jpg',  NULL, 3, 1, b'0', NULL, 'Robert Zemeckis',                   8.5),
(20, 'The Grand Budapest Hotel','Gustave H., el legendario conserje de un lujoso hotel europeo de los años 30, se ve envuelto en el robo de un cuadro renacentista y una batalla por una enorme fortuna familiar, acompañado por su fiel botones.', 99, 2014,  '/uploads/peliculas/imagenes/ba1ba8c6-42c3-4da9-a09e-4324d461161c.jpg',  NULL, 4, 1, b'0', NULL, 'Wes Anderson',                      8.1),
(21, 'Intocable',              'Philippe, un millonario tetrapléjico, contrata como cuidador a Driss, un joven de los suburbios sin experiencia. Lo que comienza como una relación improbable se convierte en una amistad que transformará la vida de ambos.', 112, 2011, '/uploads/peliculas/imagenes/0a627a93-f51d-4059-ac02-867accfa338a.jpg',  NULL, 4, 1, b'0', NULL, 'Olivier Nakache y Eric Toledano',   8.5),
(22, 'Superbad',               'Dos amigos inseparables del instituto intentan conseguir alcohol para una fiesta antes de la graduación, desencadenando una noche caótica de aventuras absurdas que pondrá a prueba su amistad.', 113, 2007, '/uploads/peliculas/imagenes/383faafa-1e57-48cf-9316-d86299f3e570.jpg',  NULL, 4, 0, b'0', '/uploads/peliculas/heroes/23ece26b-85d1-41f0-96a6-665838c14782.jpg',  'Greg Mottola',                      7.6),
(23, "Ferris Bueller's Day Off",'Ferris Bueller, un carismático estudiante de secundaria, se inventa un día de enfermedad épico y arrastra a su mejor amigo y a su novia a una aventura por Chicago mientras su director intenta cazarlo.', 103, 1986, '/uploads/peliculas/imagenes/b1a8024a-875b-4b95-925c-06953368cad4.jpg',  NULL, 4, 0, b'0', '/uploads/peliculas/heroes/53fa10f9-6da0-42e0-8a08-f7a27a9494d3.jpg',  'John Hughes',                       7.8),
(24, 'Pulp Fiction',           'Las vidas de dos sicarios, un boxeador, la esposa de un gánster y un par de atracadores se entrelazan en una serie de episodios de violencia y redención narrados fuera de orden cronológico en el bajo mundo de Los Ángeles.', 154, 1994, '/uploads/peliculas/imagenes/4353a68f-81e8-4c65-a349-06b2c30229c2.webp', NULL, 5, 1, b'0', NULL, 'Quentin Tarantino',                 8.9),
(25, 'The Silence of the Lambs','La agente en prácticas del FBI Clarice Starling busca la ayuda del brillante y peligroso psiquiatra caníbal Hannibal Lecter, encarcelado, para atrapar a un asesino en serie que desolla a sus víctimas.', 118, 1991, '/uploads/peliculas/imagenes/93f20357-236c-4e3d-a2a5-f99695124924.jpg',  NULL, 5, 1, b'0', NULL, 'Jonathan Demme',                    8.6),
(26, 'Se7en',                  'Dos detectives — el veterano Somerset y el impulsivo Mills — investigan una serie de macabros asesinatos inspirados en los siete pecados capitales, adentrándose en la mente de un asesino meticuloso y despiadado.', 127, 1995, '/uploads/peliculas/imagenes/96c4ba88-510a-4932-8e41-e0d1d3fe3bb1.jpg',  NULL, 5, 0, b'0', '/uploads/peliculas/heroes/5c0d593e-6fa7-42dd-9c07-5e46b120c605.webp', 'David Fincher',                     8.6),
(27, 'Shutter Island',         'El agente federal Teddy Daniels investiga la desaparición de una paciente en un hospital psiquiátrico situado en una isla remota. A medida que avanza la investigación, la línea entre cordura y locura se difumina.', 138, 2010, '/uploads/peliculas/imagenes/5e016bdf-1fe0-4baf-ade7-232112a8bbf4.jpg',  NULL, 5, 1, b'0', NULL, 'Martin Scorsese',                   8.1),
(28, 'Indiana Jones: En Busca del Arca Perdida','El arqueólogo Indiana Jones se embarca en una carrera contrarreloj contra los nazis para encontrar el Arca de la Alianza, una reliquia bíblica de inmenso poder, en una aventura que lo lleva de Nepal a Egipto.', 115, 1981, '/uploads/peliculas/imagenes/83f88905-e194-46a2-a0f4-3b568da7109a.jpg',  NULL, 6, 1, b'0', NULL, 'Steven Spielberg',                  8.4),
(29, 'Jurassic Park',          'Un multimillonario crea un parque temático con dinosaurios clonados a partir de ADN fósil. Cuando los sistemas de seguridad fallan, un grupo de visitantes debe sobrevivir a los depredadores más letales que la Tierra ha conocido.', 127, 1993, '/uploads/peliculas/imagenes/d11acd9f-42e4-4515-b2a6-08084e0224b9.jpg',  NULL, 6, 1, b'0', NULL, 'Steven Spielberg',                  8.2),
(30, 'The Lord of the Rings: The Fellowship of the Ring','El joven hobbit Frodo Bolsón hereda un anillo de poder que debe ser destruido en el corazón de Mordor. Acompañado por la Comunidad del Anillo, emprende un viaje épico por la Tierra Media contra las fuerzas del Señor Oscuro.', 178, 2001, '/uploads/peliculas/imagenes/7396e7e1-102b-4adc-9e92-e275fd4198b3.jpg',  NULL, 6, 1, b'0', NULL, 'Peter Jackson',                     8.8),
(31, 'Pirates of the Caribbean: The Curse of the Black Pearl','El excéntrico capitán Jack Sparrow une fuerzas con el herrero Will Turner para rescatar a la hija del gobernador de las garras de una tripulación de piratas malditos que se convierten en esqueletos bajo la luz de la luna.', 143, 2003, '/uploads/peliculas/imagenes/2f956e55-b432-4c56-81c7-d39fd57b5b92.png',  NULL, 6, 0, b'0', '/uploads/peliculas/heroes/4c63edab-944c-43bd-a5fc-63ebecd80e9f.webp', 'Gore Verbinski',                    8.0);

ALTER TABLE `peliculas` MODIFY `id` INT(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- El usuario admin se crea automáticamente al
-- arrancar la aplicación (DatabaseSeederConfig.java).
-- Credenciales: admin@cinetrack.com / admin123
-- =============================================

