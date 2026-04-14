package com.progen.motor_rutinas.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    // Este es el "pasaporte" que el usuario usará en cada petición
    private String token;
}