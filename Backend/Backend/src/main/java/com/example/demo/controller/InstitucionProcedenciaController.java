package com.example.demo.controller;

import com.example.demo.model.InstitucionProcedencia;
import com.example.demo.repository.InstitucionProcedenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituciones")
public class InstitucionProcedenciaController {
    @Autowired
    private InstitucionProcedenciaRepository institucionRepo;

    @GetMapping("/sugerencias")
    public ResponseEntity<List<InstitucionProcedencia>> sugerencias(@RequestParam String nombre) {
        return ResponseEntity.ok(institucionRepo.findByNombreContainingIgnoreCase(nombre));
    }

    @PostMapping
    public ResponseEntity<InstitucionProcedencia> guardar(@RequestBody InstitucionProcedencia institucion) {
        // Si ya existe con ese nombre y nivel, retorna el existente
        List<InstitucionProcedencia> existentes = institucionRepo.findByNombreAndNivel(institucion.getNombre(), institucion.getNivel());
        if (!existentes.isEmpty()) {
            return ResponseEntity.ok(existentes.get(0));
        }
        return ResponseEntity.ok(institucionRepo.save(institucion));
    }
}
