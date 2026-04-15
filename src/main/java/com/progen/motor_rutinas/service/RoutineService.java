package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.model.RoutineExercise;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoutineService {

    public Map<String, List<RoutineExercise>> generarRutinaPersonalizada(String equipo) {
        // El mapa guardará los días de entrenamiento como "clave" y la lista de ejercicios como "valor"
        Map<String, List<RoutineExercise>> rutinaCompleta = new HashMap<>();
        List<RoutineExercise> dia1 = new ArrayList<>();

        // LÓGICA PROCEDURAL BÁSICA
        if ("mancuernas".equalsIgnoreCase(equipo)) {
            dia1.add(new RoutineExercise("Curl de Bíceps alterno", 3, 12, "Mantén los codos pegados al cuerpo."));
            dia1.add(new RoutineExercise("Press de Pecho en suelo", 4, 10, "Baja los brazos hasta tocar el suelo."));
            dia1.add(new RoutineExercise("Zancadas con peso", 3, 15, "Espalda recta y paso firme."));
        } 
        else if ("sin_equipamiento".equalsIgnoreCase(equipo)) {
            dia1.add(new RoutineExercise("Flexiones de pecho", 4, 15, "Baja hasta casi tocar el suelo."));
            dia1.add(new RoutineExercise("Sentadillas al aire", 4, 20, "Rompe el paralelo al bajar."));
            dia1.add(new RoutineExercise("Plancha abdominal", 3, 60, "Tiempo en segundos. Mantén el core tenso."));
        } 
        else {
            // Por defecto (Máquinas de gimnasio)
            dia1.add(new RoutineExercise("Press de Banca con Barra", 4, 10, "Controla la fase excéntrica."));
            dia1.add(new RoutineExercise("Jalón al pecho", 4, 12, "Saca pecho al tirar de la polea."));
            dia1.add(new RoutineExercise("Prensa de piernas", 4, 15, "No bloquees las rodillas arriba."));
        }

        // Añadimos la lista de ejercicios al día 1
        rutinaCompleta.put("Día 1: Acondicionamiento Total", dia1);

        return rutinaCompleta;
    }
}