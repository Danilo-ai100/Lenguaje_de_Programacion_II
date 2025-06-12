package com.systempaymentut.controller;

import com.systempaymentut.model.dto.EstudianteDTO;
import com.systempaymentut.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@Tag(name = "Estudiantes", description = "API para la gestión de estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    @Operation(summary = "Crear un nuevo estudiante")
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) {
        return new ResponseEntity<>(estudianteService.crearEstudiante(estudianteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un estudiante existente")
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteDTO estudianteDTO) {
        return ResponseEntity.ok(estudianteService.actualizarEstudiante(id, estudianteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un estudiante")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un estudiante por ID")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.obtenerEstudiantePorId(id));
    }

    @GetMapping("/documento/{numeroDocumento}")
    @Operation(summary = "Obtener un estudiante por número de documento")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorDocumento(@PathVariable String numeroDocumento) {
        return ResponseEntity.ok(estudianteService.obtenerEstudiantePorDocumento(numeroDocumento));
    }

    @GetMapping("/codigo/{codigoInstitucional}")
    @Operation(summary = "Obtener un estudiante por código institucional")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorCodigo(@PathVariable String codigoInstitucional) {
        return ResponseEntity.ok(estudianteService.obtenerEstudiantePorCodigo(codigoInstitucional));
    }

    @GetMapping
    @Operation(summary = "Listar todos los estudiantes")
    public ResponseEntity<List<EstudianteDTO>> listarTodosLosEstudiantes() {
        return ResponseEntity.ok(estudianteService.listarTodosLosEstudiantes());
    }
} 