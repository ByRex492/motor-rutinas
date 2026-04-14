package com.progen.motor_rutinas.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Projection (non-entity) that pairs an {@link Exercise} with the
 * prescribed volume (sets × reps) for a given training day.
 */
@Getter
@RequiredArgsConstructor
public class RoutineExercise {

    private final Exercise ejercicio;
    private final int series;
    private final int repeticiones;
}