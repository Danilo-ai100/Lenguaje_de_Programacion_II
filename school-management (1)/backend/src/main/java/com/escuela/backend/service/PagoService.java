package com.escuela.backend.service;

import com.escuela.backend.model.*;
import com.escuela.backend.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private DescuentoService descuentoService;
    
    /**
     * Genera el pago de matrícula para un estudiante
     */
    public Pago generarPagoMatricula(Estudiante estudiante) {
        // Verificar si ya tiene pago de matrícula
        Optional<Pago> matriculaExistente = pagoRepository.findByEstudianteAndTipoPago(estudiante, TipoPago.MATRICULA);
        if (matriculaExistente.isPresent()) {
            return matriculaExistente.get();
        }
        
        Pago pagoMatricula = new Pago();
        pagoMatricula.setEstudiante(estudiante);
        pagoMatricula.setTipoPago(TipoPago.MATRICULA);
        pagoMatricula.setMonto(NivelEducativo.COSTO_MATRICULA);
        pagoMatricula.setMontoOriginal(NivelEducativo.COSTO_MATRICULA);
        pagoMatricula.setFechaVencimiento(LocalDate.now().plusDays(30)); // 30 días para pagar matrícula
        pagoMatricula.setAnoCorrespondiente(LocalDate.now().getYear());
        pagoMatricula.setObservaciones("Pago único de matrícula");
        
        return pagoRepository.save(pagoMatricula);
    }
    
    /**
     * Genera pagos mensuales para un estudiante
     */
    public List<Pago> generarPagosMensuales(Estudiante estudiante, int año) {
        List<Pago> pagos = new ArrayList<>();
        String[] meses = {"Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", 
                         "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        
        boolean tieneHermanos = descuentoService.tieneHermanos(estudiante);
        double montoPension = tieneHermanos ? 
            NivelEducativo.PENSION_DESCUENTO_HERMANOS : NivelEducativo.PENSION_REGULAR;
        TipoPago tipoPago = tieneHermanos ? TipoPago.PENSION_DESCUENTO : TipoPago.PENSION_REGULAR;
        
        for (int i = 0; i < meses.length; i++) {
            String mes = meses[i];
            
            // Verificar si ya existe el pago para este mes
            Optional<Pago> pagoExistente = pagoRepository.findByEstudianteAndMesCorrespondienteAndAnoCorrespondiente(
                estudiante, mes, año);
            
            if (pagoExistente.isEmpty()) {
                Pago pago = new Pago();
                pago.setEstudiante(estudiante);
                pago.setTipoPago(tipoPago);
                pago.setMonto(montoPension);
                pago.setMontoOriginal(NivelEducativo.PENSION_REGULAR);
                pago.setMesCorrespondiente(mes);
                pago.setAnoCorrespondiente(año);
                
                if (tieneHermanos) {
                    pago.setTieneDescuentoHermanos(true);
                    pago.setDescuentoAplicado(NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS);
                }
                
                // Fecha de vencimiento: día 10 de cada mes
                LocalDate fechaVencimiento = LocalDate.of(año, i + 3, 10); // Marzo = mes 3
                pago.setFechaVencimiento(fechaVencimiento);
                
                pagos.add(pagoRepository.save(pago));
            }
        }
        
        return pagos;
    }
    
    /**
     * Procesa un pago
     */
    public Pago procesarPago(Long pagoId, MetodoPago metodoPago, String numeroComprobante, String observaciones) {
        Optional<Pago> pagoOpt = pagoRepository.findById(pagoId);
        if (pagoOpt.isEmpty()) {
            throw new RuntimeException("Pago no encontrado");
        }
        
        Pago pago = pagoOpt.get();
        pago.setEstadoPago(EstadoPago.AL_DIA);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMetodoPago(metodoPago);
        pago.setNumeroComprobante(numeroComprobante);
        if (observaciones != null && !observaciones.trim().isEmpty()) {
            pago.setObservaciones(observaciones);
        }
        
        return pagoRepository.save(pago);
    }
    
    /**
     * Obtiene todos los pagos de un estudiante
     */
    public List<Pago> obtenerPagosEstudiante(Long estudianteId) {
        return pagoRepository.findByEstudianteIdOrderByFechaCreacionDesc(estudianteId);
    }
    
    /**
     * Obtiene pagos pendientes de un estudiante
     */
    public List<Pago> obtenerPagosPendientes(Long estudianteId) {
        return pagoRepository.findByEstudianteIdAndEstadoPago(estudianteId, EstadoPago.PENDIENTE);
    }
    
    /**
     * Obtiene el resumen de pagos de un estudiante
     */
    public Map<String, Object> obtenerResumenPagos(Long estudianteId) {
        Map<String, Object> resumen = new HashMap<>();
        
        List<Pago> todosPagos = obtenerPagosEstudiante(estudianteId);
        List<Pago> pagosPendientes = obtenerPagosPendientes(estudianteId);
        List<Pago> pagosVencidos = pagoRepository.findPagosVencidos(estudianteId);
        
        double totalPagado = todosPagos.stream()
            .filter(p -> p.getEstadoPago() == EstadoPago.AL_DIA)
            .mapToDouble(Pago::getMonto)
            .sum();
        
        double totalPendiente = pagosPendientes.stream()
            .mapToDouble(Pago::getMonto)
            .sum();
        
        double totalDescuentos = todosPagos.stream()
            .mapToDouble(p -> p.getDescuentoAplicado() != null ? p.getDescuentoAplicado() : 0.0)
            .sum();
        
        resumen.put("totalPagos", todosPagos.size());
        resumen.put("pagosPendientes", pagosPendientes.size());
        resumen.put("pagosVencidos", pagosVencidos.size());
        resumen.put("totalPagado", totalPagado);
        resumen.put("totalPendiente", totalPendiente);
        resumen.put("totalDescuentos", totalDescuentos);
        resumen.put("pagos", todosPagos);
        
        return resumen;
    }
    
    /**
     * Actualiza descuentos por hermanos para todos los estudiantes
     */
    public void actualizarDescuentosHermanos() {
        List<Pago> pagosPendientes = pagoRepository.findByEstadoPago(EstadoPago.PENDIENTE);
        
        for (Pago pago : pagosPendientes) {
            if (pago.getTipoPago() == TipoPago.PENSION_REGULAR || pago.getTipoPago() == TipoPago.PENSION_DESCUENTO) {
                boolean tieneHermanos = descuentoService.tieneHermanos(pago.getEstudiante());
                
                if (tieneHermanos && pago.getTipoPago() == TipoPago.PENSION_REGULAR) {
                    // Aplicar descuento
                    pago.setMontoOriginal(pago.getMonto());
                    pago.setMonto(NivelEducativo.PENSION_DESCUENTO_HERMANOS);
                    pago.setDescuentoAplicado(NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS);
                    pago.setTieneDescuentoHermanos(true);
                    pago.setTipoPago(TipoPago.PENSION_DESCUENTO);
                    pagoRepository.save(pago);
                } else if (!tieneHermanos && pago.getTipoPago() == TipoPago.PENSION_DESCUENTO) {
                    // Quitar descuento
                    pago.setMonto(NivelEducativo.PENSION_REGULAR);
                    pago.setMontoOriginal(NivelEducativo.PENSION_REGULAR);
                    pago.setDescuentoAplicado(0.0);
                    pago.setTieneDescuentoHermanos(false);
                    pago.setTipoPago(TipoPago.PENSION_REGULAR);
                    pagoRepository.save(pago);
                }
            }
        }
    }
    
    /**
     * Obtiene estadísticas generales de pagos
     */
    public Map<String, Object> obtenerEstadisticasPagos() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalPagos = pagoRepository.count();
        long pagosAlDia = pagoRepository.countByEstadoPago(EstadoPago.AL_DIA);
        long pagosPendientes = pagoRepository.countByEstadoPago(EstadoPago.PENDIENTE);
        long pagosVencidos = pagoRepository.countPagosVencidos();
        
        double ingresosTotales = pagoRepository.sumMontoByEstadoPago(EstadoPago.AL_DIA);
        double pendientesTotales = pagoRepository.sumMontoByEstadoPago(EstadoPago.PENDIENTE);
        double descuentosTotales = pagoRepository.sumDescuentosAplicados();
        
        stats.put("totalPagos", totalPagos);
        stats.put("pagosAlDia", pagosAlDia);
        stats.put("pagosPendientes", pagosPendientes);
        stats.put("pagosVencidos", pagosVencidos);
        stats.put("ingresosTotales", ingresosTotales);
        stats.put("pendientesTotales", pendientesTotales);
        stats.put("descuentosTotales", descuentosTotales);
        
        // Estadísticas por tipo de pago
        Map<String, Object> porTipo = new HashMap<>();
        for (TipoPago tipo : TipoPago.values()) {
            long count = pagoRepository.countByTipoPago(tipo);
            double monto = pagoRepository.sumMontoByTipoPago(tipo);
            porTipo.put(tipo.name(), Map.of("cantidad", count, "monto", monto));
        }
        stats.put("porTipoPago", porTipo);
        
        return stats;
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public List<Pago> generarMensualidades(Estudiante estudiante) {
        int año = java.time.LocalDate.now().getYear();
        return generarPagosMensuales(estudiante, año);
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public List<Pago> findByPadre(Usuario padre) {
        return pagoRepository.findByEstudiante_Padre(padre);
    }

    public List<Pago> findByEstudiante(Estudiante estudiante) {
        return pagoRepository.findByEstudiante(estudiante);
    }

    public Pago createFromRequest(com.escuela.backend.dto.PagoRequest request, Estudiante estudiante) {
        Pago pago = new Pago();
        pago.setEstudiante(estudiante);
        pago.setTipoPago(request.getTipoPago());
        pago.setMonto(request.getMonto());
        pago.setFechaVencimiento(request.getFechaVencimiento());
        pago.setAnoCorrespondiente(request.getAnoCorrespondiente());
        pago.setMesCorrespondiente(request.getMesCorrespondiente());
        pago.setObservaciones(request.getObservaciones());
        return pago;
    }
}
