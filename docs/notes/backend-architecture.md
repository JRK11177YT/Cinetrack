# Arquitectura backend de CineTrack

## 1. Objetivo

El backend de CineTrack se desarrollará como una aplicación monolítica en Java con Spring Boot, siguiendo el patrón MVC y una separación clara por capas.

El objetivo es construir una base técnica limpia, mantenible y escalable, capaz de soportar las funcionalidades principales de una plataforma de streaming inspirada en Netflix, sin introducir complejidad innecesaria para el alcance académico del proyecto.

---

## 2. Enfoque arquitectónico

Se utilizará una arquitectura por capas con las siguientes responsabilidades:

### Controladores (`controller`)

Gestionan las peticiones HTTP, reciben datos desde las vistas, invocan la lógica de negocio y devuelven la respuesta correspondiente.

### Servicios (`service`)

Contienen la lógica de negocio de la aplicación. Actúan como intermediarios entre controladores y repositorios.

### Repositorios (`repository`)

Gestionan el acceso a datos mediante Spring Data JPA.

### Modelos (`model`)

Representan las entidades del sistema y su mapeo a la base de datos.

### Configuración (`config`)

Contendrá configuraciones generales del proyecto, incluyendo seguridad, inicialización o ajustes técnicos futuros.

### DTOs (`dto`)

Se utilizarán cuando sea necesario separar los datos de entrada y salida de las entidades persistentes.

---

## 3. Patrón MVC aplicado a CineTrack

El patrón MVC se aplicará de la siguiente forma:

* **Modelo**: entidades JPA como Usuario, Pelicula o Genero
* **Vista**: plantillas Thymeleaf renderizadas en el servidor
* **Controlador**: clases anotadas con `@Controller` que gestionan el flujo entre frontend y backend

Este enfoque permite mantener separadas la lógica de negocio, la presentación y el acceso a datos.

---

## 4. Entidades principales del backend

El backend se apoyará inicialmente en las siguientes entidades:

### Usuario

Representa la cuenta de cada usuario registrado.

### Genero

Representa las categorías del catálogo.

### Pelicula

Representa el contenido principal de la plataforma.

### UsuarioGenero

Representa los gustos o preferencias del usuario.

### HistorialVisualizacion

Representa el progreso de visualización de una película por parte de un usuario y sirve como base para la funcionalidad de “seguir viendo”.

### MiLista

Representa las películas guardadas por un usuario para ver en el futuro.

---

## 5. Estructura de paquetes prevista

La estructura inicial del proyecto backend será la siguiente:

```text
src/main/java/com/cinetrack/
├── controller/
├── service/
├── repository/
├── model/
├── config/
├── dto/
└── CineTrackApplication.java
```

Esta organización responde a una arquitectura clara, legible y fácil de mantener.

---

## 6. Orden de implementación previsto

El desarrollo del backend se realizará de forma progresiva.

### Fase 1 — Base del catálogo

Se implementarán primero las entidades y funcionalidades relacionadas con el catálogo:

* Genero
* Pelicula

Objetivo:

* disponer de una base funcional del sistema
* poder listar películas
* consultar detalles
* construir el CRUD principal

### Fase 2 — Usuarios

Se implementará la entidad Usuario y la lógica básica asociada:

* registro
* inicio de sesión
* perfil

### Fase 3 — Personalización

Se implementarán las funcionalidades asociadas al comportamiento del usuario:

* MiLista
* HistorialVisualizacion
* UsuarioGenero

### Fase 4 — Refinamiento

Se añadirán mejoras visuales, validaciones, optimizaciones y posibles funcionalidades extra si aportan valor real.

---

## 7. Primera versión funcional del backend

La primera versión del backend debe centrarse en el núcleo del sistema.

### Prioridad inicial

* conexión con MySQL
* configuración de JPA
* entidad Genero
* entidad Pelicula
* repositorios
* servicios
* controlador del catálogo

Esto permitirá disponer cuanto antes de una base funcional sobre la que construir el resto.

---

## 8. Criterios de calidad

Durante el desarrollo del backend se mantendrán los siguientes criterios:

* separación clara de responsabilidades
* código legible y consistente
* nombres coherentes en clases y métodos
* entidades alineadas con la base de datos real
* lógica de negocio ubicada en servicios
* controladores ligeros
* estructura fácil de defender académicamente

---

## 9. Evolución futura

La arquitectura planteada permitirá incorporar en el futuro nuevas funcionalidades sin romper la base del sistema.

Entre las posibles ampliaciones se contemplan:

* valoraciones
* comentarios
* recomendaciones
* favoritos
* listas personalizadas
* mejora del sistema de búsqueda

---

## 10. Conclusión

La arquitectura backend de CineTrack se ha diseñado para mantener un equilibrio entre realismo, calidad técnica y viabilidad de implementación.

El objetivo no es solo cumplir con los requisitos académicos, sino construir una base profesional, coherente y preparada para evolucionar.
