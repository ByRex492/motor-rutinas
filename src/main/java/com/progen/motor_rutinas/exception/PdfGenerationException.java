package com.progen.motor_rutinas.exception;

// Heredamos de RuntimeException para que sea una "Unchecked Exception"
public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}