package com.escuela.backend.repository;

import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.NivelEducativo;
import com.escuela.backend.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    // Búsquedas básicas
    boolean existsByDni(String dni);
    boolean existsByDniAndIdNot(String dni, Long id);
    
    // Búsquedas por nombre
    List<Estudiante> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);
    
    // Búsquedas por nivel y estado
    List<Estudiante> findByNivelEducativo(NivelEducativo nivelEducativo);
    List<Estudiante> findByEstadoPago(EstadoPago estadoPago);
    List<Estudiante> findByNivelEducativoAndEstadoPago(NivelEducativo nivelEducativo, EstadoPago estadoPago);
    
    // Conteos por nivel y estado
    long countByNivelEducativo(NivelEducativo nivelEducativo);
    long countByEstadoPago(EstadoPago estadoPago);
    
    // Búsqueda de hermanos por apoderado
    List<Estudiante> findByNombreApoderadoIgnoreCaseAndIdNot(String nombreApoderado, Long id);
    List<Estudiante> findByNombreApoderadoIgnoreCase(String nombreApoderado);
    
    // Búsquedas por año académico
    List<Estudiante> findByAnoAcademico(String anoAcademico);
    List<Estudiante> findByAnoAcademicoAndNivelEducativo(String anoAcademico, NivelEducativo nivelEducativo);
    
    // Búsquedas por contacto
    List<Estudiante> findByTelefonoApoderadoOrEmailApoderado(String telefono, String email);
    
    // Consultas personalizadas para estadísticas
    @Query("SELECT COUNT(e) FROM Estudiante e WHERE e.nombreApoderado = :apoderado")
    long countByApoderado(@Param("apoderado") String apoderado);
    
    @Query("SELECT e.nombreApoderado, COUNT(e) FROM Estudiante e GROUP BY e.nombreApoderado HAVING COUNT(e) > 1")
    List<Object[]> findApoderadosConMultiplesHijos();
    
    @Query("SELECT e FROM Estudiante e WHERE e.nombreApoderado IN " +
           "(SELECT e2.nombreApoderado FROM Estudiante e2 GROUP BY e2.nombreApoderado HAVING COUNT(e2) > 1)")
    List<Estudiante> findEstudiantesConHermanos();
    
    // Búsquedas por traslado
    List<Estudiante> findByEsTraslado(Boolean esTraslado);
    List<Estudiante> findByCodigoModular(String codigoModular);
    
    // Búsquedas combinadas
    @Query("SELECT e FROM Estudiante e WHERE " +
           "(:termino IS NULL OR LOWER(e.nombres) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(e.dni) LIKE LOWER(CONCAT('%', :termino, '%'))) AND " +
           "(:nivel IS NULL OR e.nivelEducativo = :nivel) AND " +
           "(:estado IS NULL OR e.estadoPago = :estado)")
    List<Estudiante> buscarConFiltros(@Param("termino") String termino, 
                                     @Param("nivel") NivelEducativo nivel, 
                                     @Param("estado") EstadoPago estado);
}
