package com.progen.motor_rutinas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Progen Workout Engine.
 * Spring Boot will scan all sub-packages (service, controller, model) from here.
 */
@SpringBootApplication
public class MotorRutinasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotorRutinasApplication.class, args);
    }
}