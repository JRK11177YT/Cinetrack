package com.cinetrack.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_visualizacion")
public class HistorialVisualizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // A qué usuario pertenece este progreso
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Qué película estaba viendo
    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @Column(name = "fecha_ultima_visualizacion", nullable = false)
    private LocalDateTime fechaUltimaVisualizacion;

    @Column(name = "progreso_segundos", nullable = false)
    private Integer progresoSegundos;

    @Column(nullable = false)
    private Boolean completada;

    public HistorialVisualizacion() {
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaUltimaVisualizacion == null) {
            this.fechaUltimaVisualizacion = LocalDateTime.now();
        }
        if (this.progresoSegundos == null) {
            this.progresoSegundos = 0;
        }
        if (this.completada == null) {
            this.completada = false;
        }
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public LocalDateTime getFechaUltimaVisualizacion() {
        return fechaUltimaVisualizacion;
    }

    public void setFechaUltimaVisualizacion(LocalDateTime fechaUltimaVisualizacion) {
        this.fechaUltimaVisualizacion = fechaUltimaVisualizacion;
    }

    public Integer getProgresoSegundos() {
        return progresoSegundos;
    }

    public void setProgresoSegundos(Integer progresoSegundos) {
        this.progresoSegundos = progresoSegundos;
    }

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
}
