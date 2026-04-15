package com.progen.motor_rutinas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExercise {
    private String nombre;
    private int series;
    private int repeticiones;
    private String descripcion;
}