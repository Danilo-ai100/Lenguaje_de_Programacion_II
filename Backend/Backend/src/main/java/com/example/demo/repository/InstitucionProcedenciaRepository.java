package com.example.demo.repository;

import com.example.demo.model.InstitucionProcedencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstitucionProcedenciaRepository extends JpaRepository<InstitucionProcedencia, Long> {
    List<InstitucionProcedencia> findByNombreContainingIgnoreCase(String nombre);
    List<InstitucionProcedencia> findByNombreAndNivel(String nombre, String nivel);
}
