package com.escuela.backend.service;

import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.Pago;
import com.escuela.backend.model.TipoPago;
import com.escuela.backend.model.NivelEducativo;
import com.escuela.backend.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class DescuentoService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    /**
     * Verifica si un estudiante tiene hermanos matriculados
     */
    public boolean tieneHermanos(Estudiante estudiante) {
        if (estudiante.getNombreApoderado() == null || estudiante.getNombreApoderado().trim().isEmpty()) {
            return false;
        }
        
        // Buscar estudiantes con el mismo apoderado
        List<Estudiante> hermanos = estudianteRepository.findByNombreApoderadoIgnoreCaseAndIdNot(
            estudiante.getNombreApoderado().trim(), 
            estudiante.getId() != null ? estudiante.getId() : 0L
        );
        
        return !hermanos.isEmpty();
    }
    
    /**
     * Obtiene la lista de hermanos de un estudiante
     */
    public List<Estudiante> obtenerHermanos(Estudiante estudiante) {
        if (estudiante.getNombreApoderado() == null || estudiante.getNombreApoderado().trim().isEmpty()) {
            return List.of();
        }
        
        return estudianteRepository.findByNombreApoderadoIgnoreCaseAndIdNot(
            estudiante.getNombreApoderado().trim(),
            estudiante.getId() != null ? estudiante.getId() : 0L
        );
    }
    
    /**
     * Calcula el monto de pensión considerando descuento por hermanos
     */
    public double calcularMontoPension(Estudiante estudiante) {
        boolean tieneHermanos = tieneHermanos(estudiante);
        return tieneHermanos ? NivelEducativo.PENSION_DESCUENTO_HERMANOS : NivelEducativo.PENSION_REGULAR;
    }
    
    /**
     * Aplica descuento por hermanos a un pago
     */
    public void aplicarDescuentoHermanos(Pago pago) {
        if (pago.getTipoPago() == TipoPago.PENSION_REGULAR && tieneHermanos(pago.getEstudiante())) {
            pago.aplicarDescuentoHermanos();
        }
    }
    
    /**
     * Obtiene información de descuentos para un estudiante
     */
    public Map<String, Object> obtenerInformacionDescuentos(Estudiante estudiante) {
        Map<String, Object> info = new HashMap<>();
        
        boolean tieneHermanos = tieneHermanos(estudiante);
        List<Estudiante> hermanos = obtenerHermanos(estudiante);
        
        info.put("tieneHermanos", tieneHermanos);
        info.put("cantidadHermanos", hermanos.size());
        info.put("hermanos", hermanos);
        info.put("pensionRegular", NivelEducativo.PENSION_REGULAR);
        info.put("pensionConDescuento", NivelEducativo.PENSION_DESCUENTO_HERMANOS);
        info.put("descuentoMensual", NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS);
        info.put("costoMatricula", NivelEducativo.COSTO_MATRICULA);
        
        return info;
    }
    
    /**
     * Calcula el ahorro anual por descuento de hermanos
     */
    public double calcularAhorroAnual(Estudiante estudiante) {
        if (tieneHermanos(estudiante)) {
            double descuentoMensual = NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS;
            return descuentoMensual * 10; // 10 meses de clases
        }
        return 0.0;
    }
    
    /**
     * Obtiene estadísticas de descuentos por hermanos
     */
    public Map<String, Object> obtenerEstadisticasDescuentos() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Estudiante> todosEstudiantes = estudianteRepository.findAll();
        long estudiantesConHermanos = todosEstudiantes.stream()
            .mapToLong(e -> tieneHermanos(e) ? 1 : 0)
            .sum();
        
        double ahorroTotalMensual = estudiantesConHermanos * 
            (NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS);
        
        stats.put("totalEstudiantes", todosEstudiantes.size());
        stats.put("estudiantesConHermanos", estudiantesConHermanos);
        stats.put("porcentajeConDescuento", 
            todosEstudiantes.size() > 0 ? (estudiantesConHermanos * 100.0 / todosEstudiantes.size()) : 0);
        stats.put("ahorroTotalMensual", ahorroTotalMensual);
        stats.put("ahorroTotalAnual", ahorroTotalMensual * 10);
        
        return stats;
    }
}
