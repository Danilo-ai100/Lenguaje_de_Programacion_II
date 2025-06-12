package com.systempaymentut.service.impl;

import com.systempaymentut.exception.ResourceNotFoundException;
import com.systempaymentut.mapper.EstudianteMapper;
import com.systempaymentut.model.dto.EstudianteDTO;
import com.systempaymentut.model.entity.Estudiante;
import com.systempaymentut.repository.EstudianteRepository;
import com.systempaymentut.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    @Transactional
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) {
        if (estudianteRepository.existsByNumeroDocumento(estudianteDTO.getNumeroDocumento())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este número de documento");
        }
        if (estudianteRepository.existsByCodigoInstitucional(estudianteDTO.getCodigoInstitucional())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este código institucional");
        }
        if (estudianteRepository.existsByCorreoElectronico(estudianteDTO.getCorreoElectronico())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este correo electrónico");
        }

        Estudiante estudiante = estudianteMapper.toEntity(estudianteDTO);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));

        if (!estudiante.getNumeroDocumento().equals(estudianteDTO.getNumeroDocumento()) &&
                estudianteRepository.existsByNumeroDocumento(estudianteDTO.getNumeroDocumento())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este número de documento");
        }

        if (!estudiante.getCodigoInstitucional().equals(estudianteDTO.getCodigoInstitucional()) &&
                estudianteRepository.existsByCodigoInstitucional(estudianteDTO.getCodigoInstitucional())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este código institucional");
        }

        if (!estudiante.getCorreoElectronico().equals(estudianteDTO.getCorreoElectronico()) &&
                estudianteRepository.existsByCorreoElectronico(estudianteDTO.getCorreoElectronico())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este correo electrónico");
        }

        estudianteMapper.updateEntity(estudianteDTO, estudiante);
        estudiante = estudianteRepository.save(estudiante);
        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional
    public void eliminarEstudiante(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estudiante no encontrado con id: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteDTO obtenerEstudiantePorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteDTO obtenerEstudiantePorDocumento(String numeroDocumento) {
        Estudiante estudiante = estudianteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con documento: " + numeroDocumento));
        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteDTO obtenerEstudiantePorCodigo(String codigoInstitucional) {
        Estudiante estudiante = estudianteRepository.findByCodigoInstitucional(codigoInstitucional)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con código: " + codigoInstitucional));
        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteDTO> listarTodosLosEstudiantes() {
        return estudianteRepository.findAll().stream()
                .map(estudianteMapper::toDTO)
                .collect(Collectors.toList());
    }
} 