package com.progen.motor_rutinas.dto;

import lombok.Data;

@Data // Esto de Lombok te ahorra escribir los getters y setters a mano
public class LoginRequestDTO {
    private String username;
    private String password;
}