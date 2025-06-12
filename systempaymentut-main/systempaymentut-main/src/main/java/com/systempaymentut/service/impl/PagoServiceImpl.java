package com.systempaymentut.service.impl;

import com.systempaymentut.exception.ResourceNotFoundException;
import com.systempaymentut.mapper.PagoMapper;
import com.systempaymentut.model.dto.PagoDTO;
import com.systempaymentut.model.entity.Estudiante;
import com.systempaymentut.model.entity.Pago;
import com.systempaymentut.model.enums.PagoStatus;
import com.systempaymentut.model.enums.TypePago;
import com.systempaymentut.repository.EstudianteRepository;
import com.systempaymentut.repository.PagoRepository;
import com.systempaymentut.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final EstudianteRepository estudianteRepository;
    private final PagoMapper pagoMapper;

    @Override
    @Transactional
    public PagoDTO crearPago(PagoDTO pagoDTO) {
        Estudiante estudiante = estudianteRepository.findById(pagoDTO.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + pagoDTO.getEstudianteId()));

        Pago pago = pagoMapper.toEntity(pagoDTO);
        pago.setEstudiante(estudiante);
        pago.setEstado(PagoStatus.CREADO);
        
        pago = pagoRepository.save(pago);
        return pagoMapper.toDTO(pago);
    }

    @Override
    @Transactional
    public PagoDTO actualizarEstadoPago(Long id, PagoStatus nuevoEstado) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));

        pago.setEstado(nuevoEstado);
        pago = pagoRepository.save(pago);
        return pagoMapper.toDTO(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> listarPagosPorEstudiante(Long estudianteId) {
        return pagoRepository.findByEstudianteId(estudianteId).stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> listarTodosLosPagos() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> filtrarPagos(PagoStatus estado, TypePago tipoPago, Long estudianteId, 
                                     LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pago> pagos;
        
        if (estudianteId != null) {
            if (estado != null && tipoPago != null) {
                pagos = pagoRepository.findByEstudianteIdAndEstado(estudianteId, estado);
            } else if (estado != null) {
                pagos = pagoRepository.findByEstado(estado);
            } else if (tipoPago != null) {
                pagos = pagoRepository.findByTipoPago(tipoPago);
            } else {
                pagos = pagoRepository.findByEstudianteId(estudianteId);
            }
        } else {
            if (fechaInicio != null && fechaFin != null) {
                pagos = pagoRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
            } else {
                pagos = pagoRepository.findAll();
            }
        }
        
        return pagos.stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }
} 