package com.progen.motor_rutinas.model;

import java.util.List;
import java.util.Map;

public class RoutineResponse {
    private String message;
    private Map<String, List<RoutineExercise>> routine;

    // Constructor manual
    public RoutineResponse(String message, Map<String, List<RoutineExercise>> routine) {
        this.message = message;
        this.routine = routine;
    }

    // Getters y Setters manuales
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<RoutineExercise>> getRoutine() {
        return routine;
    }

    public void setRoutine(Map<String, List<RoutineExercise>> routine) {
        this.routine = routine;
    }
}