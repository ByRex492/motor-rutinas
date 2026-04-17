package com.progen.motor_rutinas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data // Si usas Lombok, esto te ahorra los Getters y Setters
@Entity
@Table(name = "ejercicios")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonProperty("grupo_muscular")
    private String grupoMuscular;

    private String equipamiento;
    private String nivel;

    @Column(length = 2000) // Importante para que quepa toda la descripción
    private String descripcion;

    @ElementCollection
    @CollectionTable(name = "ejercicio_instrucciones", joinColumns = @JoinColumn(name = "ejercicio_id"))
    private List<String> instrucciones;

    @ElementCollection
    @CollectionTable(name = "ejercicio_musculos_secundarios", joinColumns = @JoinColumn(name = "ejercicio_id"))
    @JsonProperty("musculos_secundarios")
    private List<String> musculosSecundarios;

    private String repeticiones;
    private String tipo;
}