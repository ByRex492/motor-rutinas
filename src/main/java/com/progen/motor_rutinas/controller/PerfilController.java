package com.progen.motor_rutinas.controller;

import com.progen.motor_rutinas.model.Ejercicio;
import com.progen.motor_rutinas.model.RutinaSemanal;
import com.progen.motor_rutinas.model.User;
import com.progen.motor_rutinas.repository.EjercicioRepository;
import com.progen.motor_rutinas.repository.RutinaSemanalRepository;
import com.progen.motor_rutinas.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final RutinaSemanalRepository rutinaRepository;
    private final UserRepository userRepository;
    private final EjercicioRepository ejercicioRepository;

    public PerfilController(RutinaSemanalRepository rutinaRepository, UserRepository userRepository, EjercicioRepository ejercicioRepository) {
        this.rutinaRepository = rutinaRepository;
        this.userRepository = userRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMiPerfil(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("username", user.getUsername(), "email", user.getEmail()));
    }

    @GetMapping("/rutina")
    public ResponseEntity<List<RutinaSemanal>> getMiRutina(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rutinaRepository.findByUsuarioId(user.getId()));
    }

    @PostMapping("/rutina")
    public ResponseEntity<?> guardarEjercicio(@RequestBody RutinaSemanal item, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        item.setUsuario(user);
        rutinaRepository.save(item);
        return ResponseEntity.ok().build();
    }

    // Para llenar el desplegable de la web con tus 100 ejercicios
    @GetMapping("/ejercicios")
    public ResponseEntity<List<Ejercicio>> getTodosLosEjercicios() {
        return ResponseEntity.ok(ejercicioRepository.findAll());
    }
}