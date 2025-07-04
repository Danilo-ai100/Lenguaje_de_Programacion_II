package com.example.demo.controller;

import com.example.demo.model.Cobranza;
import com.example.demo.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cobranzas")
public class CobranzaController {
    @Autowired
    private CobranzaRepository cobranzaRepository;

    @GetMapping
    public List<Cobranza> listarCobranzas() {
        return cobranzaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cobranza> obtenerCobranza(@PathVariable Long id) {
        Optional<Cobranza> cobranza = cobranzaRepository.findById(id);
        return cobranza.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCobranza(@PathVariable Long id, @RequestBody Cobranza data) {
        Optional<Cobranza> cobranzaOpt = cobranzaRepository.findById(id);
        if (cobranzaOpt.isPresent()) {
            Cobranza cobranza = cobranzaOpt.get();
            cobranza.setConcepto(data.getConcepto());
            cobranza.setMonto(data.getMonto());
            cobranza.setFechaEmision(data.getFechaEmision());
            cobranza.setEstado(data.getEstado());
            cobranzaRepository.save(cobranza);
            return ResponseEntity.ok(cobranza);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCobranza(@PathVariable Long id) {
        if (cobranzaRepository.existsById(id)) {
            cobranzaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
