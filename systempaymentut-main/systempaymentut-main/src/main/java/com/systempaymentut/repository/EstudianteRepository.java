package com.systempaymentut.repository;

import com.systempaymentut.model.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByNumeroDocumento(String numeroDocumento);
    Optional<Estudiante> findByCodigoInstitucional(String codigoInstitucional);
    boolean existsByNumeroDocumento(String numeroDocumento);
    boolean existsByCodigoInstitucional(String codigoInstitucional);
    boolean existsByCorreoElectronico(String correoElectronico);
} 