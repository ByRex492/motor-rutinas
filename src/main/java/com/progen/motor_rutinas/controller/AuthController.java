package com.progen.motor_rutinas.controller;

import com.progen.motor_rutinas.dto.LoginRequestDTO;
import com.progen.motor_rutinas.dto.UserRegistrationDTO;
import com.progen.motor_rutinas.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Importante para que el navegador no bloquee
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registrar")
    public ResponseEntity<com.progen.motor_rutinas.service.JwtResponse> registrar(@RequestBody UserRegistrationDTO request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<com.progen.motor_rutinas.service.JwtResponse> login(@RequestBody LoginRequestDTO request) {
        // ¡AHORA SÍ llama a login y usa el DTO correcto!
        return ResponseEntity.ok(authenticationService.login(request));  
    }
}