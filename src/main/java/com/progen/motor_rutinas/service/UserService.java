package com.progen.motor_rutinas.service;

import com.progen.motor_rutinas.dto.UserRegistrationDTO;
import com.progen.motor_rutinas.exception.UsernameAlreadyExistsException;
import com.progen.motor_rutinas.model.User;
import com.progen.motor_rutinas.repository.UserRepository;
import com.progen.motor_rutinas.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyectamos el repositorio e inicializamos el encriptador de contraseñas
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Registra un nuevo usuario encriptando su contraseña
     */
    public void registrar(UserRegistrationDTO dto) {
        if (userRepository.findByUsername(dto.getName()).isPresent()) {
            throw new UsernameAlreadyExistsException("El nombre de usuario '" + dto.getName() + "' ya está en uso.");
        }
        User nuevoUsuario = new User();
        nuevoUsuario.setUsername(dto.getName());
        
        nuevoUsuario.setEmail(dto.getEmail()); 

        String hashPassword = passwordEncoder.encode(dto.getPassword());
        nuevoUsuario.setPassword(hashPassword);
        userRepository.save(nuevoUsuario);
    }
    
    /**
     * Devuelve una implementación de UserDetailsService que Spring Security
     * usa para cargar el usuario durante la autenticación JWT.
     */
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    /**
     * Valida si la contraseña plana coincide con el Hash de la BD
     */
    public boolean validarCredenciales(String username, String rawPassword) {
        // 1. Buscamos al usuario
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 2. Comparamos la contraseña que ha escrito con el Hash guardado
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        
        return false; // El usuario no existe
    }
}