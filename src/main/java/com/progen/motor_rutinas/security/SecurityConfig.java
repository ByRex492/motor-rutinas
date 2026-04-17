package com.progen.motor_rutinas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // 1. Configuraciones básicas (CORS y CSRF)
        .cors(cors -> cors.configure(http))
        .csrf(csrf -> csrf.disable()) // Vital desactivarlo en APIs REST
        
        // 2. Las rutas (El "Portero")
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/error", "/favicon.ico", "/h2-console/**").permitAll()
            .requestMatchers("/", "/index.html", "/acceso.html", "/css/**", "/js/**").permitAll()
            .requestMatchers("/api/usuarios/**").permitAll()
            .anyRequest().authenticated()
        ) 
        
        // 3. ¡EL FILTRO VA AQUÍ AFUERA! (Enchufado directamente a 'http')
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    // Permitir consola H2
    http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

    return http.build();
}

    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    // ✅ FIX: allowedOriginPatterns en lugar de allowedOrigins
    // allowedOrigins("*") falla con Content-Type: application/json
    // porque el navegador prohíbe wildcard con cabeceras no-simples.
    config.setAllowedOriginPatterns(List.of("*"));

    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedOrigins(List.of(
        "http://localhost:8080",
        "http://127.0.0.1:8080"
    ));
    // Opcional pero recomendado: cachear el preflight 1 hora
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
}
}