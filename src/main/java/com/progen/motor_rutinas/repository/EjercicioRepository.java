package com.progen.motor_rutinas.repository;

import com.progen.motor_rutinas.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Exercise, Long> {
    
    // CAMBIA ESTO: de findByEquipamientoIgnoreCase a findByEquipoNecesarioIgnoreCase
    List<Exercise> findByEquipoNecesarioIgnoreCase(String equipo);
}