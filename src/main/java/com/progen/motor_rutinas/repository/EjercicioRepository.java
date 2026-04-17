package com.progen.motor_rutinas.repository;

import com.progen.motor_rutinas.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    
    // HEMOS CAMBIADO 'EquipoNecesario' POR 'Equipamiento'
    List<Ejercicio> findByEquipamientoIgnoreCase(String equipamiento);
    
    // Si tenías otros métodos de búsqueda aquí, déjalos, 
    // pero asegúrate de que usen las palabras nuevas (ej: findByGrupoMuscular)
}