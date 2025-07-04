package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminDashboardController {
    @GetMapping("/api/admin/dashboard")
    public ResponseEntity<?> getDashboardStats() {
        // Estos valores deben ser reemplazados por consultas reales a la base de datos
        Map<String, Object> stats = new HashMap<>();
        stats.put("estudiantes", 120); // Ejemplo
        stats.put("padres", 80); // Ejemplo
        stats.put("deuda", 15000.0); // Ejemplo en moneda
        return ResponseEntity.ok(stats);
    }
}
