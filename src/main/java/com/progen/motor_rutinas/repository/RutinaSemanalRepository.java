package com.progen.motor_rutinas.repository;

import com.progen.motor_rutinas.model.RutinaSemanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaSemanalRepository extends JpaRepository<RutinaSemanal, Long> {
    List<RutinaSemanal> findByUsuarioId(Long userId);
}