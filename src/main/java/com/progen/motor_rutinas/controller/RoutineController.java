package com.progen.motor_rutinas.controller;

import com.progen.motor_rutinas.model.RoutineExercise;
import com.progen.motor_rutinas.service.PdfService;
import com.progen.motor_rutinas.service.RoutineService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rutinas")
@CrossOrigin(origins = "*") 
public class RoutineController {

    private final RoutineService routineService;
    private final PdfService pdfService;

    public RoutineController(RoutineService routineService, PdfService pdfService) {
        this.routineService = routineService;
        this.pdfService = pdfService;
    }

    @PostMapping("/generar")
    public ResponseEntity<Map<String, List<RoutineExercise>>> generarRutina(
            @RequestBody Map<String, String> requestData) { 
        
        // ¡LA CLAVE ESTÁ AQUÍ! Le pasamos 'requestData' entero al servicio, no solo el equipo.
        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(requestData);

        if (rutina == null || rutina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rutina);
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @RequestBody Map<String, String> requestData) { 

        // Aquí también le pasamos TODO al servicio para que el PDF salga bien
        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(requestData);
        byte[] pdfBytes = pdfService.generarPdfRutina(rutina);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"rutina_progen.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}