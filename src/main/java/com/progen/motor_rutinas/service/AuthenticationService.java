package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.dto.UserRegistrationDTO;
import com.progen.motor_rutinas.model.Role;
import com.progen.motor_rutinas.model.User;
import com.progen.motor_rutinas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtResponse signup(UserRegistrationDTO request) {
        User user = new User();
        
        // MAPEAMOS: El 'name' del DTO va al 'username' de la Entidad
        user.setUsername(request.getName()); 
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return JwtResponse.builder()
                .token(jwt)
                .build();
    }
}