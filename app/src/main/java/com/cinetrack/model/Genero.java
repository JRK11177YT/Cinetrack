package com.cinetrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    // Constructor vacío requerido por JPA
    public Genero() {
    }

    // ==========================================
    // GETTERS Y SETTERS (Estilo tradicional)
    // ==========================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el nombre del género en formato CSS (ej: "Ciencia Ficción" -> "ciencia-ficcion")
     * Muy útil para aplicar clases CSS dinámicas en el frontend.
     */
    public String getSlug() {
        if (this.nombre == null) return "";
        return this.nombre.toLowerCase()
                .replace(" ", "-")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replaceAll("[^a-z0-9-]", "");
    }

    @Override
    public String toString() {
        return "Genero{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
