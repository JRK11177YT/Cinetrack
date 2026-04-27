USE cinetrack_db;

-- =============================================
-- Géneros cinematográficos
-- =============================================
INSERT INTO generos (id, nombre, descripcion) VALUES
(1, 'Acción',          'Películas con ritmo intenso, enfrentamientos y secuencias dinámicas'),
(2, 'Drama',           'Historias centradas en conflictos emocionales y desarrollo de personajes'),
(3, 'Ciencia Ficción', 'Películas con tecnología avanzada, futuro o elementos imaginarios'),
(4, 'Comedia',         'Películas orientadas al humor y al entretenimiento'),
(5, 'Thriller',        'Historias de tensión, suspense e intriga'),
(6, 'Aventura',        'Películas centradas en exploración, viajes y desafíos');


-- =============================================
-- Catálogo de películas
-- Géneros: 1=Acción, 2=Drama, 3=Ciencia Ficción, 4=Comedia, 5=Thriller, 6=Aventura
-- =============================================

INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Top Gun', 'Maverick es un joven e impulsivo piloto de la Armada que ingresa en la prestigiosa escuela de vuelo Top Gun, donde competirá contra los mejores aviadores del país mientras lidia con la sombra del legado de su padre.', 110, 1986, '0a627a93-f51d-4059-ac02-867accfa338a.jpg', '0589f4f2-26df-446f-b89a-3321d9dbd004.mp4', 1, TRUE),
('Top Gun: Maverick', 'Treinta años después, Pete Mitchell sigue volando como uno de los mejores pilotos de la Armada. Ahora debe entrenar a una nueva generación de pilotos para una misión casi imposible que exige el sacrificio definitivo.', 131, 2022, '2f956e55-b432-4c56-81c7-d39fd57b5b92.png', 'c41a4a21-c7c8-486c-9aba-fd4f96f2d709.mp4', 1, TRUE),
('Saving Private Ryan', 'Tras el desembarco de Normandía, el capitán Miller recibe la orden de encontrar y traer de vuelta al soldado James Ryan, cuyos tres hermanos han muerto en combate, en una arriesgada misión tras las líneas enemigas.', 169, 1998, '30e0cb91-b2ad-4a22-a897-b7a6be58b285.jpg', NULL, 1, TRUE),
('Pearl Harbor', 'Dos amigos pilotos se enfrentan a la guerra y al amor cuando el ataque japonés a Pearl Harbor cambia sus vidas para siempre, arrastrándolos a uno de los capítulos más devastadores de la Segunda Guerra Mundial.', 183, 2001, '383faafa-1e57-48cf-9316-d86299f3e570.jpg', NULL, 1, FALSE),
('Gladiator', 'Máximo, el general más leal de Roma, es traicionado por el nuevo emperador y reducido a esclavitud. Convertido en gladiador, luchará en la arena del Coliseo para vengar a su familia y devolver la justicia al imperio.', 155, 2000, '39b4ed6c-5266-49cc-8faf-31abe79571b6.jpg', NULL, 1, TRUE),
('Braveheart', 'William Wallace, un guerrero escocés del siglo XIII, lidera una feroz rebelión contra la tiranía del rey Eduardo I de Inglaterra en una lucha épica por la libertad de su pueblo.', 178, 1995, '4353a68f-81e8-4c65-a349-06b2c30229c2.webp', NULL, 1, FALSE),
('The Avengers', 'Cuando una amenaza global sin precedentes pone en peligro la Tierra, Nick Fury reúne a un equipo de superhéroes extraordinarios para salvar al mundo.', 143, 2012, '47dcfc35-b2b9-4e2a-af11-89050d3b40d2.webp', NULL, 1, TRUE),
('The Godfather', 'Don Vito Corleone dirige la familia mafiosa más poderosa de Nueva York. Cuando un intento de asesinato lo deja al borde de la muerte, su hijo menor Michael se ve arrastrado al oscuro mundo del crimen organizado.', 175, 1972, '4f1c56b7-7b2a-426b-a87a-9b83e3aef9d5.jpg', NULL, 2, TRUE),
('La Lista de Schindler', 'Oskar Schindler, un empresario alemán, arriesga su fortuna y su vida para salvar a más de mil judíos del Holocausto empleándolos en su fábrica durante la ocupación nazi de Polonia.', 195, 1993, '54782809-ce9c-4820-afd9-e9036c190a8a.jpg', NULL, 2, TRUE),
('La Sociedad de la Nieve', 'En 1972, un avión con 45 pasajeros se estrella en los Andes. Los supervivientes deben enfrentarse al frío extremo, el hambre y decisiones imposibles para mantenerse con vida durante 72 días en la montaña.', 144, 2023, '584393df-ccee-4e47-ae25-54046476121b.jpg', NULL, 2, TRUE),
('La La Land', 'En Los Ángeles, una aspirante a actriz y un pianista de jazz se enamoran mientras persiguen sus sueños. Pero el éxito profesional pondrá a prueba una relación que parecía destinada a durar para siempre.', 128, 2016, '5e016bdf-1fe0-4baf-ade7-232112a8bbf4.jpg', NULL, 2, FALSE),
('Forrest Gump', 'Forrest Gump, un hombre de buen corazón y capacidad intelectual limitada, vive una vida extraordinaria que lo lleva a ser testigo y protagonista involuntario de los momentos más importantes de la historia de Estados Unidos.', 142, 1994, '7396e7e1-102b-4adc-9e92-e275fd4198b3.jpg', NULL, 2, TRUE),
('The Shawshank Redemption', 'Andy Dufresne, un banquero condenado injustamente por el asesinato de su esposa, forma una inesperada amistad con Red dentro de la prisión de Shawshank mientras mantiene viva la esperanza de recuperar su libertad.', 142, 1994, '76a78391-ffbc-4274-b9df-eb60cf9b1a26.jpg', NULL, 2, TRUE),
('Goodfellas', 'Henry Hill crece en el seno de la mafia neoyorquina y asciende junto a sus compañeros Tommy y Jimmy hasta lo más alto del crimen organizado, en un mundo donde la lealtad y la traición conviven a diario.', 146, 1990, '7895a0de-69c5-4fde-ac84-20bd122e2d0f.jpg', NULL, 2, FALSE),
('Interstellar', 'En un futuro donde la Tierra agoniza, un grupo de astronautas viaja a través de un agujero de gusano en busca de un nuevo hogar para la humanidad, enfrentándose a la dilatación del tiempo y a decisiones que desafían el amor y la física.', 169, 2014, '7ce3ed35-28e6-46ec-b05e-38a4515d30b6.jpg', '0589f4f2-26df-446f-b89a-3321d9dbd004.mp4', 3, TRUE),
('The Matrix', 'Thomas Anderson descubre que la realidad que conoce es una simulación creada por máquinas. Bajo el nombre de Neo, se une a un grupo de rebeldes para liberar a la humanidad de su prisión digital.', 136, 1999, '83f88905-e194-46a2-a0f4-3b568da7109a.jpg', NULL, 3, TRUE),
('Inception', 'Dom Cobb es un ladrón especializado en infiltrarse en los sueños para robar secretos. Para volver a casa con sus hijos, acepta una misión imposible: implantar una idea en la mente de alguien a través de múltiples niveles de sueños.', 148, 2010, '875caf70-45ee-4f11-ac71-8f3c390a7621.webp', NULL, 3, FALSE),
('Blade Runner 2049', 'El oficial K, un replicante de nueva generación, descubre un secreto enterrado durante décadas que podría desatar una guerra entre humanos y replicantes, y que lo lleva a buscar a Rick Deckard, desaparecido hace treinta años.', 164, 2017, '87bb3526-653d-4bec-be13-2fd17a034a4c.jpg', NULL, 3, FALSE),
('Back to the Future', 'Marty McFly viaja accidentalmente a 1955 en un DeLorean convertido en máquina del tiempo por el excéntrico Doc Brown. Ahora debe asegurarse de que sus padres se enamoren o él dejará de existir.', 116, 1985, '8d2eb9c7-252d-4fa2-8570-bb8e8a177216.jpg', NULL, 3, TRUE),
('The Grand Budapest Hotel', 'Gustave H., el legendario conserje de un lujoso hotel europeo de los años 30, se ve envuelto en el robo de un cuadro renacentista y una batalla por una enorme fortuna familiar.', 99, 2014, '93f20357-236c-4e3d-a2a5-f99695124924.jpg', NULL, 4, TRUE),
('Intocable', 'Philippe, un millonario tetrapléjico, contrata como cuidador a Driss, un joven de los suburbios sin experiencia. Lo que comienza como una relación improbable se convierte en una amistad transformadora.', 112, 2011, '96c4ba88-510a-4932-8e41-e0d1d3fe3bb1.jpg', NULL, 4, TRUE),
('Superbad', 'Dos amigos inseparables del instituto intentan conseguir alcohol para una fiesta antes de la graduación, desencadenando una noche de locura y aprendizaje sobre la amistad.', 113, 2007, '9f82aaf6-e3b6-4eca-8e41-7e6460e9fefd.jpg', NULL, 4, FALSE),
('Seven', 'Dos detectives, uno veterano y otro novato, persiguen a un asesino en serie cuyos crímenes están inspirados en los siete pecados capitales.', 127, 1995, 'b0be8487-6001-4117-9d30-2c9d54b8e818.jpg', NULL, 5, TRUE),
('El Silencio de los Corderos', 'Una joven agente del FBI debe recurrir a la ayuda de un brillante y peligroso psicópata, el Dr. Hannibal Lecter, para atrapar a otro asesino en serie que despelleja a sus víctimas.', 118, 1991, 'b1a8024a-875b-4b95-925c-06953368cad4.jpg', NULL, 5, TRUE),
('The Dark Knight', 'Batman se enfrenta al Joker, un genio criminal que busca sumir a Gotham City en el caos y poner a prueba los límites morales del Caballero Oscuro.', 152, 2008, 'ba1ba8c6-42c3-4da9-a09e-4324d461161c.jpg', NULL, 5, TRUE),
('Pulp Fiction', 'Las vidas de several criminales de Los Ángeles se entrelazan en tres historias violentas y surrealistas que mezclan humor negro, filosofía y situaciones extremas.', 154, 1994, 'ba2b6f7f-1bc0-4644-8506-4be36d43164c.jpg', NULL, 5, FALSE),
('El Señor de los Anillos: El Retorno del Rey', 'Frodo y Sam se acercan al Monte del Destino para destruir el Anillo Único, mientras Aragorn lidera a las fuerzas del bien en la batalla final por la Tierra Media.', 201, 2003, 'c70e2625-1e7b-435d-852f-89035b00f165.jpg', NULL, 6, TRUE),
('Indiana Jones: En busca del arca perdida', 'El arqueólogo Indiana Jones es contratado por el gobierno de EE. UU. para encontrar el Arca de la Alianza antes de que los nazis la utilicen para sus oscuros fines.', 115, 1981, 'd11acd9f-42e4-4515-b2a6-08084e0224b9.jpg', NULL, 6, TRUE),
('Jurassic Park', 'Un multimillonario crea un parque temático con dinosaurios clonados, pero un fallo en el sistema de seguridad desata el caos y pone en peligro la vida de los visitantes.', 127, 1993, 'db54a3e2-7589-4cb4-aef4-ee74af38c5d7.jpg', NULL, 6, FALSE),
('Avatar', 'En un futuro lejano, un marine parapléjico es enviado al planeta Pandora, donde se ve dividido entre sus órdenes y el deseo de proteger el mundo y la cultura de los Na''vi.', 162, 2009, 'df5266ec-e553-447e-a3c3-949d6a0e0813.jpg', NULL, 6, TRUE),
('The Revenant', 'Un trampero herido de muerte es abandonado por sus compañeros tras el ataque de un oso. Su lucha por sobrevivir se convierte en una épica historia de superación y venganza.', 156, 2015, 'e8a3b846-3c0c-45a5-b2b9-89ff8afd3b91.jpg', NULL, 6, FALSE),
('John Wick', 'Un asesino a sueldo retirado regresa al mundo del crimen después de que el hijo de un mafioso robe su coche y mate a su perro, el último regalo de su esposa fallecida.', 101, 2014, 'eae85653-d01e-47c0-bc7e-f0c101ef4496.jpg', NULL, 1, TRUE);
