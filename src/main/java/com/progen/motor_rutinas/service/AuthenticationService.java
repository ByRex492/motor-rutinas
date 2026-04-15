package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.dto.LoginRequestDTO;
import com.progen.motor_rutinas.dto.UserRegistrationDTO;
import com.progen.motor_rutinas.model.Role;
import com.progen.motor_rutinas.model.User;
import com.progen.motor_rutinas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse signup(UserRegistrationDTO request) {
        User user = new User();
        user.setUsername(request.getName()); 
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        
        return JwtResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public JwtResponse login(LoginRequestDTO request) {
        // Comprobamos usuario y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Si es correcto, buscamos sus datos
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Devolvemos su token
        return JwtResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}