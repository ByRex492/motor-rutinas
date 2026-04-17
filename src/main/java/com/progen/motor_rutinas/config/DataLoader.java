package com.progen.motor_rutinas.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progen.motor_rutinas.model.Ejercicio;
import com.progen.motor_rutinas.repository.EjercicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final EjercicioRepository ejercicioRepository;

    public DataLoader(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Comprueba si la base de datos está vacía antes de meter los datos
        if (ejercicioRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Ejercicio>> typeReference = new TypeReference<List<Ejercicio>>(){};
            
            // Busca el archivo ejercicios.json en la carpeta resources
            InputStream inputStream = TypeReference.class.getResourceAsStream("/ejercicios.json");
            
            try {
                if (inputStream != null) {
                    List<Ejercicio> ejercicios = mapper.readValue(inputStream, typeReference);
                    
                    // 👉 ¡EL TRUCO ESTÁ AQUÍ! 
                    // Ponemos el ID a null para obligar a la base de datos a crearlos como nuevos
                    for (Ejercicio ej : ejercicios) {
                        ej.setId(null);
                    }
                    
                    ejercicioRepository.saveAll(ejercicios);
                    System.out.println("✅ ¡ÉXITO! Se han cargado " + ejercicios.size() + " ejercicios en la base de datos H2.");
                } else {
                    System.out.println("❌ ERROR: No se ha encontrado el archivo ejercicios.json en la carpeta resources.");
                }
            } catch (Exception e) {
                System.out.println("❌ ERROR al leer el JSON: " + e.getMessage());
            }
        }
    }
}