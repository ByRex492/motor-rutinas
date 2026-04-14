package com.progen.motor_rutinas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rutinas_guardadas")
public class SavedRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String equipo;
    private LocalDateTime fechaGeneracion;

    @Column(columnDefinition = "TEXT")
    private String contenidoJson;

    public SavedRoutine() { /* document why this constructor is empty */ }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getEquipo() { return equipo; }
    public void setEquipo(String equipo) { this.equipo = equipo; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public String getContenidoJson() { return contenidoJson; }
    public void setContenidoJson(String contenidoJson) { this.contenidoJson = contenidoJson; }
}