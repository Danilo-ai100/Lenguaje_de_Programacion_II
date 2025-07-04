package com.example.demo.controller;


import com.example.demo.dto.MatriculaRequest;
import com.example.demo.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {
    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<?> registrarMatricula(@RequestBody MatriculaRequest request) {
        try {
            MatriculaService.MatriculaResult result = matriculaService.registrarMatricula(request);
            Map<String, Object> response = new HashMap<>();
            response.put("matricula", result.matricula);
            if (result.passwordGenerada != null) {
                response.put("passwordGenerada", result.passwordGenerada);
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
