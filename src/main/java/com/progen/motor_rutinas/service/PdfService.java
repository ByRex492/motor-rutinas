package com.progen.motor_rutinas.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.progen.motor_rutinas.exception.PdfGenerationException;
import com.progen.motor_rutinas.model.RoutineExercise;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.awt.Color; 

/**
 * Generates a formatted PDF for a given weekly routine using OpenPDF.
 */
@Slf4j
@Service
public class PdfService {

    // Colour palette
    private static final Color COLOR_ACCENT = new Color(0, 242, 254);
    private static final Color COLOR_DARK   = new Color(15, 23, 42);
    private static final Color COLOR_MUTED  = new Color(100, 116, 139);

    // Fonts (initialised once)
    private static final Font FONT_TITLE  = buildFont(FontFactory.HELVETICA_BOLD,  22, COLOR_DARK);
    private static final Font FONT_DAY    = buildFont(FontFactory.HELVETICA_BOLD,  14, COLOR_ACCENT);
    private static final Font FONT_EXNAME = buildFont(FontFactory.HELVETICA_BOLD,  11, Color.BLACK);
    private static final Font FONT_DETAIL = buildFont(FontFactory.HELVETICA,         10, COLOR_MUTED);
    private static final Font FONT_FOOTER = buildFont(FontFactory.HELVETICA_OBLIQUE, 9, COLOR_MUTED);

    /**
     * Converts a weekly routine map into a PDF byte array.
     *
     * @param rutina map of day → exercises
     * @return raw PDF bytes
     * @throws PdfGenerationException if PDF generation fails
     */
    public byte[] generarPdfRutina(Map<String, List<RoutineExercise>> rutina) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 60, 60, 60, 60);

        try {
            // Bind the document to the output stream
            PdfWriter.getInstance(doc, out);
            
            // Open the document to start writing
            doc.open(); 

            // Add Main Header
            Paragraph title = new Paragraph("PROGEN | Rutina Semanal", FONT_TITLE);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            doc.add(title);

            // Add the routine content and footer
            addRoutineContent(doc, rutina);
            addFooter(doc);

        } catch (DocumentException e) {
            log.error("Technical error while building PDF document", e);
            // Throw our custom exception instead of a generic one
            throw new PdfGenerationException("Could not generate routine file", e);
        } finally {
            // Ensure the document is closed to flush the content to the byte array
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
        }
        return out.toByteArray();
    }

    // ------------------------------------------------------------------ //
    //  Private helpers                                                   //
    // ------------------------------------------------------------------ //

    /**
     * Iterates through the routine map and adds formatted text to the document.
     * @param doc the active PDF document
     * @param rutina the data to write
     * @throws DocumentException if writing fails
     */
    private void addRoutineContent(Document doc, Map<String, List<RoutineExercise>> rutina)
            throws DocumentException {

        for (Map.Entry<String, List<RoutineExercise>> entry : rutina.entrySet()) {
            // Day heading
            Paragraph dayHeading = new Paragraph(entry.getKey().toUpperCase(), FONT_DAY);
            dayHeading.setSpacingBefore(16f);
            dayHeading.setSpacingAfter(4f);
            doc.add(dayHeading);

            doc.add(new Paragraph("─────────────────────────────────────────────", FONT_DETAIL));

            // Exercises
            for (RoutineExercise ej : entry.getValue()) {
                Paragraph name = new Paragraph("  • " + ej.getEjercicio().getNombre(), FONT_EXNAME);
                name.setSpacingBefore(6f);
                doc.add(name);

                Paragraph detail = new Paragraph(
                        "      " + ej.getSeries() + " series × " + ej.getRepeticiones() + " reps",
                        FONT_DETAIL);
                doc.add(detail);
            }
        }
    }

    /**
     * Adds a simple footer at the bottom of the routine.
     * @param doc the active PDF document
     * @throws DocumentException if writing fails
     */
    private void addFooter(Document doc) throws DocumentException {
        Paragraph footer = new Paragraph("\nGenerado por PROGEN · AI Fitness", FONT_FOOTER);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(32f);
        doc.add(footer);
    }

    /**
     * Helper method to build a font with a specific color.
     */
    private static Font buildFont(String name, float size, Color color) {
        Font f = FontFactory.getFont(name, size);
        f.setColor(color);
        return f;
    }
}