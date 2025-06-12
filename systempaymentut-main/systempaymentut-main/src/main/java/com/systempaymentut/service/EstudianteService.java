package com.systempaymentut.service;

import com.systempaymentut.model.dto.EstudianteDTO;
import java.util.List;

public interface EstudianteService {
    EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO);
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO);
    void eliminarEstudiante(Long id);
    EstudianteDTO obtenerEstudiantePorId(Long id);
    EstudianteDTO obtenerEstudiantePorDocumento(String numeroDocumento);
    EstudianteDTO obtenerEstudiantePorCodigo(String codigoInstitucional);
    List<EstudianteDTO> listarTodosLosEstudiantes();
} 