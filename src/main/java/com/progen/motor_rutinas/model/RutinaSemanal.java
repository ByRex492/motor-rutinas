package com.progen.motor_rutinas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rutinas_semanales")
public class RutinaSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    private String diaSemana;
    private String nombreEjercicio;
    private int series;
    private int repeticiones;
}