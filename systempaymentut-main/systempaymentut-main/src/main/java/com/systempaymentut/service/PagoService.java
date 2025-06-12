package com.systempaymentut.service;

import com.systempaymentut.model.dto.PagoDTO;
import com.systempaymentut.model.enums.PagoStatus;
import com.systempaymentut.model.enums.TypePago;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {
    PagoDTO crearPago(PagoDTO pagoDTO);
    PagoDTO actualizarEstadoPago(Long id, PagoStatus nuevoEstado);
    List<PagoDTO> listarPagosPorEstudiante(Long estudianteId);
    List<PagoDTO> listarTodosLosPagos();
    List<PagoDTO> filtrarPagos(PagoStatus estado, TypePago tipoPago, Long estudianteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
} 