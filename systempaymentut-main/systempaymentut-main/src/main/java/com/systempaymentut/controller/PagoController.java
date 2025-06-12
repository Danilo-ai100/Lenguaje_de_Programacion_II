package com.systempaymentut.controller;

import com.systempaymentut.model.dto.PagoDTO;
import com.systempaymentut.model.enums.PagoStatus;
import com.systempaymentut.model.enums.TypePago;
import com.systempaymentut.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "API para la gestión de pagos")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo pago")
    public ResponseEntity<PagoDTO> crearPago(@Valid @RequestBody PagoDTO pagoDTO) {
        return new ResponseEntity<>(pagoService.crearPago(pagoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar el estado de un pago")
    public ResponseEntity<PagoDTO> actualizarEstadoPago(
            @PathVariable Long id,
            @RequestParam PagoStatus nuevoEstado) {
        return ResponseEntity.ok(pagoService.actualizarEstadoPago(id, nuevoEstado));
    }

    @GetMapping("/estudiante/{estudianteId}")
    @Operation(summary = "Listar pagos por estudiante")
    public ResponseEntity<List<PagoDTO>> listarPagosPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(pagoService.listarPagosPorEstudiante(estudianteId));
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    public ResponseEntity<List<PagoDTO>> listarTodosLosPagos() {
        return ResponseEntity.ok(pagoService.listarTodosLosPagos());
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar pagos por diferentes criterios")
    public ResponseEntity<List<PagoDTO>> filtrarPagos(
            @RequestParam(required = false) PagoStatus estado,
            @RequestParam(required = false) TypePago tipoPago,
            @RequestParam(required = false) Long estudianteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return ResponseEntity.ok(pagoService.filtrarPagos(estado, tipoPago, estudianteId, fechaInicio, fechaFin));
    }
} 