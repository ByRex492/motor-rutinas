package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.model.Exercise;
import com.progen.motor_rutinas.model.RoutineExercise;
import com.progen.motor_rutinas.repository.EjercicioRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RoutineService {

    private final EjercicioRepository ejercicioRepository;

    // Constructor para inyectar el repositorio sin usar Lombok
    public RoutineService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    public Map<String, List<RoutineExercise>> generarRutinaPersonalizada(String equipo) {
        
        // 1. Buscamos en la base de datos
        List<Exercise> dbExercises = ejercicioRepository.findAll();
        
        // 2. Filtramos por equipo
        List<Exercise> filtered = dbExercises.stream()
        .filter(e -> e.getEquipoNecesario() != null && e.getEquipoNecesario().equalsIgnoreCase(equipo))
        .toList();

        Map<String, List<RoutineExercise>> weeklyRoutine = new LinkedHashMap<>();
        String[] days = {"Monday", "Wednesday", "Friday"};

        for (String day : days) {
            List<RoutineExercise> dailyList = new ArrayList<>();
            
            List<Exercise> shuffleList = new ArrayList<>(filtered);
            Collections.shuffle(shuffleList);
            
            // 3. Creamos objetos RoutineExercise (del modelo) y los metemos en la lista
            shuffleList.stream().limit(3).forEach(e -> 
                dailyList.add(new RoutineExercise(e, 3, 12))
            );

            weeklyRoutine.put(day, dailyList);
        }

        return weeklyRoutine;
    }
}