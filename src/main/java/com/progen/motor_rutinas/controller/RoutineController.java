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

    // 1. Cambiado a POST y usamos Map para recibir el JSON
    @PostMapping("/generar")
    public ResponseEntity<Map<String, List<RoutineExercise>>> generarRutina(
            @RequestBody Map<String, String> requestData) { 
        
        // Sacamos el valor "equipo" que nos envía el Javascript
        String equipo = requestData.get("equipo"); 
        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(equipo);

        if (rutina == null || rutina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rutina);
    }

    // 2. Cambiado a POST y la ruta a "/pdf"
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @RequestBody Map<String, String> requestData) { 

        String equipo = requestData.get("equipo");
        Map<String, List<RoutineExercise>> rutina = routineService.generarRutinaPersonalizada(equipo);
        byte[] pdfBytes = pdfService.generarPdfRutina(rutina);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"rutina_progen.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}