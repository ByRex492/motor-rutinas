package com.progen.motor_rutinas.controller;

import com.progen.motor_rutinas.model.RoutineExercise;
import com.progen.motor_rutinas.service.PdfService;
import com.progen.motor_rutinas.service.RoutineService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rutina")
public class RoutineController {

    // 1. Declaramos las variables SIN iniciarlas (= null o = new...)
    private final RoutineService routineService;
    private final PdfService pdfService;

    // 2. CONSTRUCTOR: Por aquí Spring Boot nos inyecta los servicios reales
    public RoutineController(RoutineService routineService, PdfService pdfService) {
        this.routineService = routineService;
        this.pdfService = pdfService;
    }

    @GetMapping("/generar")
    public ResponseEntity<Map<String, List<RoutineExercise>>> generarRutina(
            @RequestParam String equipo) { 

        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(equipo);

        if (rutina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rutina);
    }

    @SuppressWarnings("null")
    @GetMapping("/descargar")
    public ResponseEntity<byte[]> descargarPdf(
            @RequestParam String equipo) { 

        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(equipo);
        byte[] pdfBytes = pdfService.generarPdfRutina(rutina);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"rutina_progen.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}