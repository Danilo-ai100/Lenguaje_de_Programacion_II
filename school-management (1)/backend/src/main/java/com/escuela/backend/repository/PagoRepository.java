package com.escuela.backend.repository;

import com.escuela.backend.model.Pago;
import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.TipoPago;
import com.escuela.backend.model.EstadoPago;
import com.escuela.backend.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    // Búsquedas por estudiante
    List<Pago> findByEstudianteIdOrderByFechaCreacionDesc(Long estudianteId);
    List<Pago> findByEstudianteIdAndEstadoPago(Long estudianteId, EstadoPago estadoPago);
    List<Pago> findByEstudiante(Estudiante estudiante);
    List<Pago> findByEstudiante_Padre(com.escuela.backend.model.Usuario padre);

    // Búsquedas por tipo de pago
    List<Pago> findByTipoPago(TipoPago tipoPago);
    Optional<Pago> findByEstudianteAndTipoPago(Estudiante estudiante, TipoPago tipoPago);
    
    // Búsquedas por estado
    List<Pago> findByEstadoPago(EstadoPago estadoPago);
    
    // Búsquedas por mes y año
    Optional<Pago> findByEstudianteAndMesCorrespondienteAndAnoCorrespondiente(
        Estudiante estudiante, String mes, Integer año);
    List<Pago> findByMesCorrespondienteAndAnoCorrespondiente(String mes, Integer año);
    List<Pago> findByAnoCorrespondiente(Integer año);
    
    // Búsquedas por método de pago
    List<Pago> findByMetodoPago(MetodoPago metodoPago);
    
    // Conteos
    long countByEstadoPago(EstadoPago estadoPago);
    long countByTipoPago(TipoPago tipoPago);
    long countByEstudianteId(Long estudianteId);
    
    // Consultas de suma
    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p WHERE p.estadoPago = :estado")
    double sumMontoByEstadoPago(@Param("estado") EstadoPago estado);
    
    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p WHERE p.tipoPago = :tipo")
    double sumMontoByTipoPago(@Param("tipo") TipoPago tipo);
    
    @Query("SELECT COALESCE(SUM(p.descuentoAplicado), 0) FROM Pago p WHERE p.descuentoAplicado > 0")
    double sumDescuentosAplicados();
    
    // Pagos vencidos
    @Query("SELECT p FROM Pago p WHERE p.fechaVencimiento < CURRENT_DATE AND p.estadoPago = 'PENDIENTE'")
    List<Pago> findPagosVencidos();
    
    @Query("SELECT p FROM Pago p WHERE p.estudiante.id = :estudianteId AND p.fechaVencimiento < CURRENT_DATE AND p.estadoPago = 'PENDIENTE'")
    List<Pago> findPagosVencidos(@Param("estudianteId") Long estudianteId);
    
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.fechaVencimiento < CURRENT_DATE AND p.estadoPago = 'PENDIENTE'")
    long countPagosVencidos();
    
    // Pagos con descuento por hermanos
    List<Pago> findByTieneDescuentoHermanos(Boolean tieneDescuento);
    
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.tieneDescuentoHermanos = true")
    long countPagosConDescuento();
    
    // Estadísticas por rango de fechas
    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<Pago> findByFechaPagoBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                     @Param("fechaFin") LocalDate fechaFin);
    
    // Ingresos por mes
    @Query("SELECT p.mesCorrespondiente, SUM(p.monto) FROM Pago p WHERE p.estadoPago = 'AL_DIA' AND p.anoCorrespondiente = :año GROUP BY p.mesCorrespondiente")
    List<Object[]> findIngresosPorMes(@Param("año") Integer año);
    
    // Pagos por apoderado (para familias)
    @Query("SELECT p FROM Pago p WHERE p.estudiante.nombreApoderado = :apoderado ORDER BY p.fechaCreacion DESC")
    List<Pago> findByApoderado(@Param("apoderado") String apoderado);
}
