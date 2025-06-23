package com.escuela.backend.controller;

import com.escuela.backend.dto.EstudianteRequest;
import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.NivelEducativo;
import com.escuela.backend.model.EstadoPago;
import com.escuela.backend.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "http://localhost:4200")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        System.out.println("📚 Obteniendo todos los estudiantes");
        List<Estudiante> estudiantes = estudianteService.findAll();
        return ResponseEntity.ok(estudiantes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getEstudianteById(@PathVariable Long id) {
        System.out.println("👁️ Obteniendo estudiante ID: " + id);
        
        Estudiante estudiante = estudianteService.findById(id);
        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(estudiante);
    }
    
    @PostMapping
    public ResponseEntity<?> createEstudiante(@Valid @RequestBody EstudianteRequest request) {
        try {
            System.out.println("➕ Creando nuevo estudiante: " + request.getNombres() + " " + request.getApellidos());
            
            // Validar DNI único
            if (request.getDni() != null && !request.getDni().isEmpty()) {
                if (estudianteService.existsByDni(request.getDni())) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ya existe un estudiante con este DNI"));
                }
            }
            
            Estudiante estudiante = estudianteService.createFromRequest(request);
            Estudiante savedEstudiante = estudianteService.save(estudiante);
            
            System.out.println("✅ Estudiante creado con ID: " + savedEstudiante.getId());
            
            return ResponseEntity.ok(savedEstudiante);
            
        } catch (Exception e) {
            System.out.println("❌ Error creando estudiante: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error creando estudiante: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEstudiante(@PathVariable Long id, @Valid @RequestBody EstudianteRequest request) {
        try {
            System.out.println("🔄 Actualizando estudiante ID: " + id);
            
            Estudiante existingEstudiante = estudianteService.findById(id);
            if (existingEstudiante == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Validar DNI único (excluyendo el estudiante actual)
            if (request.getDni() != null && !request.getDni().isEmpty()) {
                if (estudianteService.existsByDniAndIdNot(request.getDni(), id)) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ya existe un estudiante con este DNI"));
                }
            }
            
            Estudiante updatedEstudiante = estudianteService.updateFromRequest(existingEstudiante, request);
            Estudiante savedEstudiante = estudianteService.save(updatedEstudiante);
            
            System.out.println("✅ Estudiante actualizado: " + savedEstudiante.getNombreCompleto());
            
            return ResponseEntity.ok(savedEstudiante);
            
        } catch (Exception e) {
            System.out.println("❌ Error actualizando estudiante: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error actualizando estudiante: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstudiante(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Eliminando estudiante ID: " + id);
            
            Estudiante estudiante = estudianteService.findById(id);
            if (estudiante == null) {
                return ResponseEntity.notFound().build();
            }
            
            estudianteService.deleteById(id);
            
            System.out.println("✅ Estudiante eliminado: " + estudiante.getNombreCompleto());
            
            return ResponseEntity.ok(Map.of("message", "Estudiante eliminado exitosamente"));
            
        } catch (Exception e) {
            System.out.println("❌ Error eliminando estudiante: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error eliminando estudiante: " + e.getMessage()));
        }
    }
    
    @GetMapping("/niveles")
    public ResponseEntity<?> getNivelesEducativos() {
        Map<String, Object> response = new HashMap<>();
        response.put("niveles", NivelEducativo.values());
        response.put("iniciales", NivelEducativo.getNivelesIniciales());
        response.put("primaria", NivelEducativo.getNivelesPrimaria());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<?> getEstadisticas() {
        Map<String, Object> stats = estudianteService.getEstadisticas();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Estudiante>> buscarEstudiantes(
            @RequestParam(required = false) String termino,
            @RequestParam(required = false) NivelEducativo nivel,
            @RequestParam(required = false) EstadoPago estado) {
        
        System.out.println("🔍 Buscando estudiantes - Término: " + termino + ", Nivel: " + nivel + ", Estado: " + estado);
        
        List<Estudiante> estudiantes = estudianteService.buscarConFiltros(termino, nivel, estado);
        return ResponseEntity.ok(estudiantes);
    }
}
