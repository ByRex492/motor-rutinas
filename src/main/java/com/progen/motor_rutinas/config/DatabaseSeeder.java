package com.progen.motor_rutinas.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.progen.motor_rutinas.model.Exercise; 
import com.progen.motor_rutinas.repository.EjercicioRepository;

import java.util.List;

/**
 * Populates the database with a base catalogue of exercises on every startup.
 *
 * <p>The existing data is a first to avoid duplicates across restarts.
 * In a production environment this seed would be replaced by SQL migration
 * scripts (e.g. Flyway / Liquibase).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final EjercicioRepository repository;

    @SuppressWarnings("null")
    @Override
    public void run(String... args) {
        repository.deleteAll();
        repository.saveAll(buildCatalogue());
        log.info("✅ Exercise catalogue loaded — {} exercises inserted.", repository.count());
    }

    // ------------------------------------------------------------------ //
    //  Catalogue definition                                                 //
    // ------------------------------------------------------------------ //

    private List<Exercise> buildCatalogue() {
        return List.of(

            // ── EMPUJE (Pecho · Hombros · Tríceps) ─────────────────────

            // Gimnasio Completo
            gym("Press Banca con Barra",       "Pecho",    "Empuje",  "Compuesto"),
            gym("Press Militar con Barra",      "Hombros",  "Empuje",  "Compuesto"),
            gym("Press de Pecho en Máquina",    "Pecho",    "Empuje",  "Compuesto"),
            gym("Cruces en Polea",              "Pecho",    "Empuje",  "Aislamiento"),

            // Mancuernas
            mdb("Press con Mancuernas Plano",    "Pecho",    "Empuje",  "Compuesto"),
            mdb("Press Inclinado con Mancuernas","Pecho",    "Empuje",  "Compuesto"),
            mdb("Press Arnold",                  "Hombros",  "Empuje",  "Compuesto"),
            mdb("Aperturas con Mancuernas",      "Pecho",    "Empuje",  "Aislamiento"),
            mdb("Elevaciones Laterales",         "Hombros",  "Empuje",  "Aislamiento"),
            mdb("Copa de Tríceps",               "Brazos",   "Empuje",  "Aislamiento"),

            // Peso Corporal
            bw("Flexiones de Brazo",             "Pecho",    "Empuje",  "Compuesto"),
            bw("Fondos en Paralelas",            "Pecho",    "Empuje",  "Compuesto"),
            bw("Flexiones Diamante",             "Brazos",   "Empuje",  "Aislamiento"),
            bw("Flexiones de Pica",              "Hombros",  "Empuje",  "Compuesto"),

            // ── TIRÓN (Espalda · Bíceps) ────────────────────────────────

            // Gimnasio Completo
            gym("Remo con Barra",               "Espalda",  "Tiron",   "Compuesto"),
            gym("Jalón al Pecho",               "Espalda",  "Tiron",   "Compuesto"),
            gym("Remo en Polea Baja",           "Espalda",  "Tiron",   "Compuesto"),
            gym("Facepull",                     "Hombros",  "Tiron",   "Aislamiento"),

            // Mancuernas
            mdb("Remo con Mancuerna a una mano","Espalda",  "Tiron",   "Compuesto"),
            mdb("Remo Yates con Mancuernas",    "Espalda",  "Tiron",   "Compuesto"),
            mdb("Curl de Bíceps con Mancuernas","Brazos",   "Tiron",   "Aislamiento"),
            mdb("Curl Martillo",                "Brazos",   "Tiron",   "Aislamiento"),
            mdb("Pájaro con Mancuernas",        "Hombros",  "Tiron",   "Aislamiento"),

            // Peso Corporal
            bw("Dominadas Pronas",              "Espalda",  "Tiron",   "Compuesto"),
            bw("Dominadas Supinas",             "Brazos",   "Tiron",   "Compuesto"),
            bw("Remo Invertido en Barra",       "Espalda",  "Tiron",   "Compuesto"),

            // ── PIERNA (Cuádriceps · Isquios · Glúteo) ──────────────────

            // Gimnasio Completo
            gym("Sentadilla con Barra",         "Pierna",   "Pierna",  "Compuesto"),
            gym("Prensa de Piernas",            "Pierna",   "Pierna",  "Compuesto"),
            gym("Peso Muerto Convencional",     "Pierna",   "Pierna",  "Compuesto"),
            gym("Extensión de Cuádriceps",      "Pierna",   "Pierna",  "Aislamiento"),
            gym("Curl Femoral en Máquina",      "Pierna",   "Pierna",  "Aislamiento"),

            // Mancuernas
            mdb("Sentadilla Goblet",            "Pierna",   "Pierna",  "Compuesto"),
            mdb("Zancadas con Mancuerna",       "Pierna",   "Pierna",  "Compuesto"),
            mdb("Sentadilla Búlgara",           "Pierna",   "Pierna",  "Compuesto"),
            mdb("Peso Muerto Rumano con Mancuernas","Pierna","Pierna", "Compuesto"),

            // Peso Corporal
            bw("Sentadilla Aérea",              "Pierna",   "Pierna",  "Compuesto"),
            bw("Puente de Glúteo",              "Pierna",   "Pierna",  "Aislamiento"),
            bw("Zancadas Laterales",            "Pierna",   "Pierna",  "Compuesto"),
            bw("Subidas al Cajón (Step-up)",    "Pierna",   "Pierna",  "Compuesto")
        );
    }

    // ── Shorthand factory methods ──────────────────────────────────────

    private Exercise gym(String nombre, String grupo, String patron, String tipo) {
        return new Exercise(null, nombre, grupo, "Gimnasio Completo", patron, tipo);
    }

    private Exercise mdb(String nombre, String grupo, String patron, String tipo) {
        return new Exercise(null, nombre, grupo, "Mancuernas", patron, tipo);
    }

    private Exercise bw(String nombre, String grupo, String patron, String tipo) {
        return new Exercise(null, nombre, grupo, "Peso corporal", patron, tipo);
    }
}