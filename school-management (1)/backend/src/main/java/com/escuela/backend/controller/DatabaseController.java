package com.escuela.backend.controller;

import com.escuela.backend.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/database")
@CrossOrigin(origins = "http://localhost:4200")
public class DatabaseController {
    
    @Autowired
    private DatabaseService databaseService;
    
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> verificarEstado() {
        Map<String, Object> status = databaseService.verificarConexion();
        return ResponseEntity.ok(status);
    }
    
    @PostMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> probarConexion() {
        try {
            Map<String, Object> resultado = databaseService.verificarConexion();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "estado", "ERROR",
                "mensaje", "Error de conexión: " + e.getMessage()
            ));
        }
    }
}
