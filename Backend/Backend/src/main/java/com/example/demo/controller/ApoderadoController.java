package com.example.demo.controller;

import com.example.demo.model.Apoderado;
import com.example.demo.repository.ApoderadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apoderados")
public class ApoderadoController {
    @Autowired
    private ApoderadoRepository apoderadoRepository;

    @GetMapping
    public List<Apoderado> listarApoderados() {
        return apoderadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apoderado> obtenerApoderado(@PathVariable Long id) {
        Optional<Apoderado> apoderado = apoderadoRepository.findById(id);
        return apoderado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarApoderado(@PathVariable Long id, @RequestBody Apoderado data) {
        Optional<Apoderado> apoderadoOpt = apoderadoRepository.findById(id);
        if (apoderadoOpt.isPresent()) {
            Apoderado apoderado = apoderadoOpt.get();
            apoderado.setNombres(data.getNombres());
            apoderado.setApellidoPaterno(data.getApellidoPaterno());
            apoderado.setApellidoMaterno(data.getApellidoMaterno());
            apoderado.setDni(data.getDni());
            apoderado.setTelefono(data.getTelefono());
            apoderado.setEmail(data.getEmail());
            apoderadoRepository.save(apoderado);
            return ResponseEntity.ok(apoderado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarApoderado(@PathVariable Long id) {
        if (apoderadoRepository.existsById(id)) {
            apoderadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
