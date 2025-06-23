package com.escuela.backend.service;

import com.escuela.backend.dto.EstudianteRequest;
import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.NivelEducativo;
import com.escuela.backend.model.EstadoPago;
import com.escuela.backend.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }
    
    public Estudiante findById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }
    
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }
    
    public boolean existsByDni(String dni) {
        return estudianteRepository.existsByDni(dni);
    }
    
    public boolean existsByDniAndIdNot(String dni, Long id) {
        return estudianteRepository.existsByDniAndIdNot(dni, id);
    }
    
    public Estudiante createFromRequest(EstudianteRequest request) {
        Estudiante estudiante = new Estudiante();
        updateEstudianteFromRequest(estudiante, request);
        estudiante.setFechaMatricula(LocalDateTime.now());
        estudiante.setAnoAcademico(request.getAnoAcademico() != null ? request.getAnoAcademico() : "2024");
        return estudiante;
    }
    
    public Estudiante updateFromRequest(Estudiante estudiante, EstudianteRequest request) {
        updateEstudianteFromRequest(estudiante, request);
        return estudiante;
    }
    
    private void updateEstudianteFromRequest(Estudiante estudiante, EstudianteRequest request) {
        estudiante.setNombres(request.getNombres());
        estudiante.setApellidos(request.getApellidos());
        estudiante.setDni(request.getDni());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setNivelEducativo(request.getNivelEducativo());
        estudiante.setNombreApoderado(request.getNombreApoderado());
        estudiante.setTelefonoApoderado(request.getTelefonoApoderado());
        estudiante.setEmailApoderado(request.getEmailApoderado());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setInformacionMedica(request.getInformacionMedica());
        estudiante.setAnoAcademico(request.getAnoAcademico());
        estudiante.setEsTraslado(request.getEsTraslado());
        estudiante.setCodigoModular(request.getCodigoModular());
        estudiante.setInstitucionAnterior(request.getInstitucionAnterior());
        estudiante.setMotivoTraslado(request.getMotivoTraslado());
        estudiante.setContactoEmergencia(request.getContactoEmergencia());
        estudiante.setTelefonoEmergencia(request.getTelefonoEmergencia());
    }
    
    public List<Estudiante> buscarConFiltros(String termino, NivelEducativo nivel, EstadoPago estado) {
        if (termino != null && !termino.trim().isEmpty()) {
            return estudianteRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                termino.trim(), termino.trim());
        }
        
        if (nivel != null && estado != null) {
            return estudianteRepository.findByNivelEducativoAndEstadoPago(nivel, estado);
        }
        
        if (nivel != null) {
            return estudianteRepository.findByNivelEducativo(nivel);
        }
        
        if (estado != null) {
            return estudianteRepository.findByEstadoPago(estado);
        }
        
        return findAll();
    }
    
    public Map<String, Object> getEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalEstudiantes = estudianteRepository.count();
        long estudiantesAlDia = estudianteRepository.countByEstadoPago(EstadoPago.AL_DIA);
        long estudiantesPendientes = estudianteRepository.countByEstadoPago(EstadoPago.PENDIENTE);
        long estudiantesVencidos = estudianteRepository.countByEstadoPago(EstadoPago.VENCIDO);
        
        // Estudiantes por nivel
        Map<String, Long> porNivel = new HashMap<>();
        for (NivelEducativo nivel : NivelEducativo.values()) {
            long count = estudianteRepository.countByNivelEducativo(nivel);
            porNivel.put(nivel.name(), count);
        }
        
        // Ingresos mensuales estimados
        double ingresosMensuales = 0.0;
        for (NivelEducativo nivel : NivelEducativo.values()) {
            long count = estudianteRepository.countByNivelEducativo(nivel);
            ingresosMensuales += count * nivel.getPensionMensual();
        }
        
        stats.put("totalEstudiantes", totalEstudiantes);
        stats.put("estudiantesAlDia", estudiantesAlDia);
        stats.put("estudiantesPendientes", estudiantesPendientes);
        stats.put("estudiantesVencidos", estudiantesVencidos);
        stats.put("porNivel", porNivel);
        stats.put("ingresosMensuales", ingresosMensuales);
        
        return stats;
    }

    public void actualizarEstadoPago(Estudiante estudiante) {
        // Implementación básica: podrías actualizar el estado según pagos realizados
        // Aquí solo es un placeholder, ajusta según tu lógica de negocio
        estudianteRepository.save(estudiante);
    }
}
