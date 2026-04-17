package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.model.RoutineExercise;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RoutineService {

    public Map<String, List<RoutineExercise>> generarRutinaPersonalizada(Map<String, String> datos) {
        
        // 1. Extraemos las variables del mapa que viene del Frontend
        String equipo = datos.getOrDefault("equipo", "sin_equipamiento");
        String genero = datos.getOrDefault("genero", "Hombre");
        int peso = Integer.parseInt(datos.getOrDefault("peso", "70"));
        int altura = Integer.parseInt(datos.getOrDefault("altura", "170"));
        int edad = Integer.parseInt(datos.getOrDefault("edad", "25"));
        System.out.println("DATOS RECIBIDOS EN JAVA: " + datos);
        Map<String, List<RoutineExercise>> rutinaCompleta = new HashMap<>();
        List<RoutineExercise> ejercicios = new ArrayList<>();

        // ===========================================================
        // REGLA 1: CASO INFANTIL (Ej: Niño de 7 años o menos de 135cm)
        // ===========================================================
        if (edad < 12 || altura < 140) {
            ejercicios.add(new RoutineExercise("Salto de rana", 2, 8, "Enfoque lúdico y coordinación."));
            ejercicios.add(new RoutineExercise("Caminata de oso", 2, 10, "Fortalecimiento natural sin pesas."));
            rutinaCompleta.put("Rutina Infantil Adaptada (Seguridad Máxima)", ejercicios);
            return rutinaCompleta;
        }

        // ===========================================================
        // REGLA 2: CÁLCULO DE INTENSIDAD SEGÚN IMC (Peso y Altura)
        // ===========================================================
        double alturaMetros = altura / 100.0;
        double imc = peso / (alturaMetros * alturaMetros);

        int series = 3;
        int repeticiones = 12;

        if (imc > 25) {
            // Caso Sobrepeso: Más repeticiones para aumentar gasto calórico
            series = 4;
            repeticiones = 15;
        } else if (imc < 18.5) {
            // Caso Bajo Peso: Menos repeticiones para ganar masa muscular
            series = 3;
            repeticiones = 8;
        }

        // ===========================================================
        // REGLA 3: ADAPTACIÓN POR GÉNERO
        // ===========================================================
        String enfoque = "General";
        if ("Mujer".equalsIgnoreCase(genero)) {
            enfoque = "Tonificación y Tren Inferior";
            // Ajuste leve de volumen
            repeticiones += 2; 
        }

        // ===========================================================
        // REGLA 4: SELECCIÓN POR EQUIPAMIENTO
        // ===========================================================
        if ("mancuernas".equalsIgnoreCase(equipo)) {
            ejercicios.add(new RoutineExercise("Press militar", series, repeticiones, "Espalda apoyada."));
            ejercicios.add(new RoutineExercise("Sentadilla con copa", series, repeticiones, "Mancuerna al pecho."));
        } else {
            ejercicios.add(new RoutineExercise("Flexiones", series, repeticiones, "Codos a 45 grados."));
            ejercicios.add(new RoutineExercise("Sentadillas", series, repeticiones + 5, "Sin peso extra."));
        }

        rutinaCompleta.put("Rutina " + enfoque + " (IMC: " + String.format("%.1f", imc) + ")", ejercicios);
        return rutinaCompleta;
    }
}