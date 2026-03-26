# Roadmap del Proyecto CineTrack

## 1. Objetivo

Este documento define la planificación evolutiva del desarrollo de CineTrack.

No se trata de un plan cerrado, sino de una hoja de ruta flexible que permite organizar el proyecto por fases, versiones e hitos, adaptándose a mejoras, cambios de enfoque y nuevas funcionalidades que puedan surgir durante el desarrollo.

El objetivo es mantener siempre un equilibrio entre:

* calidad técnica
* coherencia del sistema
* viabilidad temporal
* valor académico y profesional

---

## 2. Principios del roadmap

El desarrollo del proyecto se regirá por los siguientes principios:

* El roadmap es **flexible y evolutivo**
* Se podrán añadir, modificar o reorganizar versiones si aporta valor
* Las decisiones se tomarán con enfoque profesional, no improvisado
* Las mejoras deberán integrarse sin romper la arquitectura existente
* Se priorizará siempre la claridad, la coherencia y la calidad

---

## 3. Estructura del desarrollo

El proyecto se organizará en **versiones incrementales**, donde cada versión añade valor funcional o técnico al sistema.

Cada versión deberá cumplir al menos uno de estos objetivos:

* introducir una nueva funcionalidad
* mejorar una funcionalidad existente
* reforzar la arquitectura o la calidad del sistema
* preparar el proyecto para la siguiente fase

---

## 4. Versiones base (plan inicial)

### v0.1 — Fundaciones del proyecto

Objetivo: establecer una base sólida y profesional.

Incluye:

* creación del repositorio
* estructura de carpetas
* definición del sistema Atlas
* configuración inicial de Git y workflow
* base Docker con MySQL
* organización del proyecto

---

### v0.2 — Diseño de base de datos

Objetivo: construir un modelo de datos coherente y defendible.

Incluye:

* definición de entidades principales
* modelo Entidad-Relación
* modelo relacional
* naming conventions
* scripts SQL iniciales
* organización de la base de datos

---

### v0.3 — Arranque del backend

Objetivo: preparar la base técnica del sistema.

Incluye:

* creación del proyecto Spring Boot
* estructura MVC base
* conexión con MySQL
* configuración inicial de JPA
* organización de paquetes

---

### v0.4 — Catálogo de películas

Objetivo: implementar la entidad principal del sistema.

Incluye:

* CRUD completo de películas
* relación con géneros
* listado de películas
* vista de detalle

---

### v0.5 — Sistema de usuarios

Objetivo: introducir personalización en la aplicación.

Incluye:

* registro de usuarios
* inicio de sesión
* gestión básica de usuarios
* asociación de datos al usuario

---

### v0.6 — Historial de visualización

Objetivo: añadir lógica de negocio real.

Incluye:

* registro de visualización de películas
* almacenamiento de progreso
* consulta de historial

---

### v0.7 — Continuar viendo

Objetivo: mejorar la experiencia del usuario.

Incluye:

* reanudación de películas desde el último punto
* integración en la página principal
* lógica de personalización

---

### v0.8 — Mejora visual y usabilidad

Objetivo: elevar la calidad de la interfaz.

Incluye:

* mejora del diseño con Bootstrap
* interfaz responsive completa
* mejora de navegación
* optimización de formularios

---

### v0.9 — Documentación y despliegue

Objetivo: preparar el proyecto para entrega.

Incluye:

* README final
* memoria técnica
* diagramas (clases y ER)
* Dockerfile final
* docker-compose final
* revisión global

---

### v1.0 — Entrega final

Objetivo: versión estable y defendible.

Incluye:

* sistema funcional completo
* documentación cerrada
* estructura limpia y coherente
* preparación de defensa

---

## 5. Gestión de mejoras y funcionalidades adicionales

Durante el desarrollo podrán surgir nuevas ideas o mejoras.

Estas se gestionarán de la siguiente forma:

1. Se evaluará su valor técnico, visual y académico
2. Se comprobará su encaje en la arquitectura existente
3. Se decidirá si:

   * se añade a una versión actual
   * se pospone a una versión posterior
   * se descarta por falta de impacto o viabilidad

---

## 6. Versiones incrementales y refinamiento

Se podrán crear subversiones para mejorar funcionalidades existentes, por ejemplo:

* v0.4.1 → mejora del catálogo (filtros, diseño)
* v0.6.1 → mejora del historial (barra de progreso)
* v0.8.1 → mejoras visuales adicionales

Esto permitirá refinar el proyecto sin romper la estructura principal.

---

## 7. Funcionalidades candidatas (no obligatorias)

Estas funcionalidades podrán añadirse si aportan valor real:

* buscador de películas
* películas destacadas en portada
* recomendaciones por género
* barra de progreso visual
* roles de usuario (admin / user)
* mejoras de UX en reproducción
* optimización SEO adicional

Su implementación dependerá del avance del proyecto y del tiempo disponible.

---

## 8. Filosofía de desarrollo

CineTrack no se desarrollará como una lista cerrada de tareas, sino como un sistema progresivo y bien organizado.

La prioridad será:

* hacer menos cosas, pero bien hechas
* mantener coherencia en todo el sistema
* evitar soluciones improvisadas
* construir un proyecto defendible y profesional

---

## 9. Meta final

El objetivo no es solo completar el proyecto, sino desarrollar una aplicación:

* sólida
* bien estructurada
* técnicamente coherente
* visualmente cuidada
* y capaz de destacar frente al resto
