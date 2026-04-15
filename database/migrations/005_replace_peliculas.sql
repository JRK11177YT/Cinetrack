USE cinetrack_db;

-- =============================================
-- Migración: Reemplazar catálogo de películas
-- Limpia datos dependientes y carga películas reales
-- =============================================

-- 1. Limpiar tablas que referencian peliculas (sin CASCADE)
DELETE FROM historial_visualizacion;
DELETE FROM mi_lista;

-- 2. Borrar todas las películas existentes
DELETE FROM peliculas;

-- 3. Resetear autoincrement
ALTER TABLE peliculas AUTO_INCREMENT = 1;

-- ── Acción (genero_id = 1) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Top Gun', 'Maverick es un joven e impulsivo piloto de la Armada que ingresa en la prestigiosa escuela de vuelo Top Gun, donde competirá contra los mejores aviadores del país mientras lidia con la sombra del legado de su padre.', 110, 1986, NULL, NULL, 1, TRUE),
('Top Gun: Maverick', 'Treinta años después, Pete Mitchell sigue volando como uno de los mejores pilotos de la Armada. Ahora debe entrenar a una nueva generación de pilotos para una misión casi imposible que exige el sacrificio definitivo.', 131, 2022, NULL, NULL, 1, TRUE),
('Saving Private Ryan', 'Tras el desembarco de Normandía, el capitán Miller recibe la orden de encontrar y traer de vuelta al soldado James Ryan, cuyos tres hermanos han muerto en combate, en una arriesgada misión tras las líneas enemigas.', 169, 1998, NULL, NULL, 1, TRUE),
('Pearl Harbor', 'Dos amigos pilotos se enfrentan a la guerra y al amor cuando el ataque japonés a Pearl Harbor cambia sus vidas para siempre, arrastrándolos a uno de los capítulos más devastadores de la Segunda Guerra Mundial.', 183, 2001, NULL, NULL, 1, FALSE),
('Gladiator', 'Máximo, el general más leal de Roma, es traicionado por el nuevo emperador y reducido a esclavitud. Convertido en gladiador, luchará en la arena del Coliseo para vengar a su familia y devolver la justicia al imperio.', 155, 2000, NULL, NULL, 1, TRUE),
('Braveheart', 'William Wallace, un guerrero escocés del siglo XIII, lidera una feroz rebelión contra la tiranía del rey Eduardo I de Inglaterra en una lucha épica por la libertad de su pueblo.', 178, 1995, NULL, NULL, 1, FALSE),
('The Avengers', 'Cuando una amenaza global sin precedentes pone en peligro la Tierra, Nick Fury reúne a un equipo de superhéroes extraordinarios — Iron Man, Capitán América, Thor, Hulk, Viuda Negra y Ojo de Halcón — para salvar al mundo.', 143, 2012, NULL, NULL, 1, TRUE);

-- ── Drama (genero_id = 2) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('The Godfather', 'Don Vito Corleone dirige la familia mafiosa más poderosa de Nueva York. Cuando un intento de asesinato lo deja al borde de la muerte, su hijo menor Michael se ve arrastrado al oscuro mundo del crimen organizado.', 175, 1972, NULL, NULL, 2, TRUE),
('La Lista de Schindler', 'Oskar Schindler, un empresario alemán, arriesga su fortuna y su vida para salvar a más de mil judíos del Holocausto empleándolos en su fábrica durante la ocupación nazi de Polonia.', 195, 1993, NULL, NULL, 2, TRUE),
('La Sociedad de la Nieve', 'En 1972, un avión con 45 pasajeros se estrella en los Andes. Los supervivientes deben enfrentarse al frío extremo, el hambre y decisiones imposibles para mantenerse con vida durante 72 días en la montaña.', 144, 2023, NULL, NULL, 2, TRUE),
('La La Land', 'En Los Ángeles, una aspirante a actriz y un pianista de jazz se enamoran mientras persiguen sus sueños. Pero el éxito profesional pondrá a prueba una relación que parecía destinada a durar para siempre.', 128, 2016, NULL, NULL, 2, FALSE),
('Forrest Gump', 'Forrest Gump, un hombre de buen corazón y capacidad intelectual limitada, vive una vida extraordinaria que lo lleva a ser testigo y protagonista involuntario de los momentos más importantes de la historia de Estados Unidos.', 142, 1994, NULL, NULL, 2, TRUE),
('The Shawshank Redemption', 'Andy Dufresne, un banquero condenado injustamente por el asesinato de su esposa, forma una inesperada amistad con Red dentro de la prisión de Shawshank mientras mantiene viva la esperanza de recuperar su libertad.', 142, 1994, NULL, NULL, 2, TRUE),
('Goodfellas', 'Henry Hill crece en el seno de la mafia neoyorquina y asciende junto a sus compañeros Tommy y Jimmy hasta lo más alto del crimen organizado, en un mundo donde la lealtad y la traición conviven a diario.', 146, 1990, NULL, NULL, 2, FALSE);

-- ── Ciencia Ficción (genero_id = 3) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Interstellar', 'En un futuro donde la Tierra agoniza, un grupo de astronautas viaja a través de un agujero de gusano en busca de un nuevo hogar para la humanidad, enfrentándose a la dilatación del tiempo y a decisiones que desafían el amor y la física.', 169, 2014, NULL, NULL, 3, TRUE),
('The Matrix', 'Thomas Anderson descubre que la realidad que conoce es una simulación creada por máquinas. Bajo el nombre de Neo, se une a un grupo de rebeldes para liberar a la humanidad de su prisión digital.', 136, 1999, NULL, NULL, 3, TRUE),
('Inception', 'Dom Cobb es un ladrón especializado en infiltrarse en los sueños para robar secretos. Para volver a casa con sus hijos, acepta una misión imposible: implantar una idea en la mente de alguien a través de múltiples niveles de sueños.', 148, 2010, NULL, NULL, 3, FALSE),
('Blade Runner 2049', 'El oficial K, un replicante de nueva generación, descubre un secreto enterrado durante décadas que podría desatar una guerra entre humanos y replicantes, y que lo lleva a buscar a Rick Deckard, desaparecido hace treinta años.', 164, 2017, NULL, NULL, 3, FALSE),
('Back to the Future', 'Marty McFly viaja accidentalmente a 1955 en un DeLorean convertido en máquina del tiempo por el excéntrico Doc Brown. Ahora debe asegurarse de que sus padres se enamoren o él dejará de existir.', 116, 1985, NULL, NULL, 3, TRUE);

-- ── Comedia (genero_id = 4) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('The Grand Budapest Hotel', 'Gustave H., el legendario conserje de un lujoso hotel europeo de los años 30, se ve envuelto en el robo de un cuadro renacentista y una batalla por una enorme fortuna familiar, acompañado por su fiel botones.', 99, 2014, NULL, NULL, 4, TRUE),
('Intocable', 'Philippe, un millonario tetrapléjico, contrata como cuidador a Driss, un joven de los suburbios sin experiencia. Lo que comienza como una relación improbable se convierte en una amistad que transformará la vida de ambos.', 112, 2011, NULL, NULL, 4, TRUE),
('Superbad', 'Dos amigos inseparables del instituto intentan conseguir alcohol para una fiesta antes de la graduación, desencadenando una noche caótica de aventuras absurdas que pondrá a prueba su amistad.', 113, 2007, NULL, NULL, 4, FALSE),
('Ferris Bueller''s Day Off', 'Ferris Bueller, un carismático estudiante de secundaria, se inventa un día de enfermedad épico y arrastra a su mejor amigo y a su novia a una aventura por Chicago mientras su director intenta cazarlo.', 103, 1986, NULL, NULL, 4, FALSE);

-- ── Thriller (genero_id = 5) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Pulp Fiction', 'Las vidas de dos sicarios, un boxeador, la esposa de un gánster y un par de atracadores se entrelazan en una serie de episodios de violencia y redención narrados fuera de orden cronológico en el bajo mundo de Los Ángeles.', 154, 1994, NULL, NULL, 5, TRUE),
('The Silence of the Lambs', 'La agente en prácticas del FBI Clarice Starling busca la ayuda del brillante y peligroso psiquiatra caníbal Hannibal Lecter, encarcelado, para atrapar a un asesino en serie que desolla a sus víctimas.', 118, 1991, NULL, NULL, 5, TRUE),
('Se7en', 'Dos detectives — el veterano Somerset y el impulsivo Mills — investigan una serie de macabros asesinatos inspirados en los siete pecados capitales, adentrándose en la mente de un asesino meticuloso y despiadado.', 127, 1995, NULL, NULL, 5, FALSE),
('Shutter Island', 'El agente federal Teddy Daniels investiga la desaparición de una paciente en un hospital psiquiátrico situado en una isla remota. A medida que avanza la investigación, la línea entre cordura y locura se difumina.', 138, 2010, NULL, NULL, 5, TRUE);

-- ── Aventura (genero_id = 6) ──
INSERT INTO peliculas (titulo, descripcion, duracion, anio, url_imagen, url_video, genero_id, destacada) VALUES
('Indiana Jones: En Busca del Arca Perdida', 'El arqueólogo Indiana Jones se embarca en una carrera contrarreloj contra los nazis para encontrar el Arca de la Alianza, una reliquia bíblica de inmenso poder, en una aventura que lo lleva de Nepal a Egipto.', 115, 1981, NULL, NULL, 6, TRUE),
('Jurassic Park', 'Un multimillonario crea un parque temático con dinosaurios clonados a partir de ADN fósil. Cuando los sistemas de seguridad fallan, un grupo de visitantes debe sobrevivir a los depredadores más letales que la Tierra ha conocido.', 127, 1993, NULL, NULL, 6, TRUE),
('The Lord of the Rings: The Fellowship of the Ring', 'El joven hobbit Frodo Bolsón hereda un anillo de poder que debe ser destruido en el corazón de Mordor. Acompañado por la Comunidad del Anillo, emprende un viaje épico por la Tierra Media contra las fuerzas del Señor Oscuro.', 178, 2001, NULL, NULL, 6, TRUE),
('Pirates of the Caribbean: The Curse of the Black Pearl', 'El excéntrico capitán Jack Sparrow une fuerzas con el herrero Will Turner para rescatar a la hija del gobernador de las garras de una tripulación de piratas malditos que se convierten en esqueletos bajo la luz de la luna.', 143, 2003, NULL, NULL, 6, FALSE);
