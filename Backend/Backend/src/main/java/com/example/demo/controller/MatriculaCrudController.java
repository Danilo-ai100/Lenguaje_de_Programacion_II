package com.example.demo.controller;

import com.example.demo.model.Matricula;
import com.example.demo.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaCrudController {
    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping
    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> obtenerMatricula(@PathVariable Long id) {
        Optional<Matricula> matricula = matriculaRepository.findById(id);
        return matricula.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMatricula(@PathVariable Long id, @RequestBody Matricula data) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            matricula.setAnioEscolar(data.getAnioEscolar());
            matricula.setFechaMatricula(data.getFechaMatricula());
            matricula.setTipoMatricula(data.getTipoMatricula());
            matricula.setModalidad(data.getModalidad());
            matricula.setEstado(data.getEstado());
            matricula.setDocumentos(data.getDocumentos());
            matriculaRepository.save(matricula);
            return ResponseEntity.ok(matricula);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMatricula(@PathVariable Long id) {
        if (matriculaRepository.existsById(id)) {
            matriculaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
