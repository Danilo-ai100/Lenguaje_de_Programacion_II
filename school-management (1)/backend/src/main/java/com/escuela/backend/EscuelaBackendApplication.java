package com.escuela.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicación Principal del Sistema de Gestión Escolar DAYIVA
 * 
 * @author Sistema Escolar
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class EscuelaBackendApplication {

    public static void main(String[] args) {
        System.out.println("🏫 ===================================");
        System.out.println("🎓 SISTEMA ESCOLAR DAYIVA - BACKEND");
        System.out.println("📚 Iniciando aplicación...");
        System.out.println("🏫 ===================================");
        
        SpringApplication.run(EscuelaBackendApplication.class, args);
        
        System.out.println("✅ ===================================");
        System.out.println("🚀 APLICACIÓN INICIADA EXITOSAMENTE");
        System.out.println("🌐 Backend API: http://localhost:8080");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("📖 API Docs: http://localhost:8080/api");
        System.out.println("✅ ===================================");
    }
}
