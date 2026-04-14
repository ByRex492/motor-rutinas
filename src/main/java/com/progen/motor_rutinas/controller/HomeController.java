package com.progen.motor_rutinas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "<h1>🚀 Motor de Rutinas ProGen Online</h1><p>El servidor está funcionando y la seguridad JWT está activa.</p>";
    }
}