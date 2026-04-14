package com.progen.motor_rutinas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Smoke test — verifies that the Spring application context loads correctly.
 */
@SpringBootTest
@ActiveProfiles("test")
class MotorRutinasApplicationTests {

    @Test
    void contextLoads() {
        // Si este test pasa, significa que tu seguridad, base de datos y beans están OK.
    }
}