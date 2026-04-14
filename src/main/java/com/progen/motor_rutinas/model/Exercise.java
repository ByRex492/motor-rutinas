package com.progen.motor_rutinas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ejercicios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;
    private String grupoMuscular;
    private String equipoNecesario;
    private String videoUrl;

    // NO añadas otra clase aquí dentro. 
    // Si tienes "public class Exercise" otra vez aquí, bórralo.
}