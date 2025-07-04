package com.example.demo.controller;

import com.example.demo.repository.PagoRepository;
import com.example.demo.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/total-validados")
    public ResponseEntity<Double> totalValidados() {
        List<Pago> validados = pagoRepository.findByEstadoValidacion(Pago.EstadoValidacion.VALIDADO);
        double total = validados.stream()
            .map(Pago::getMontoPagado)
            .filter(java.util.Objects::nonNull)
            .mapToDouble(java.math.BigDecimal::doubleValue)
            .sum();
        return ResponseEntity.ok(total);
    }
}
