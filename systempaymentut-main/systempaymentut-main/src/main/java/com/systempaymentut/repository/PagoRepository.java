package com.systempaymentut.repository;

import com.systempaymentut.model.entity.Pago;
import com.systempaymentut.model.enums.PagoStatus;
import com.systempaymentut.model.enums.TypePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByEstudianteId(Long estudianteId);
    List<Pago> findByEstado(PagoStatus estado);
    List<Pago> findByTipoPago(TypePago tipoPago);
    List<Pago> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Pago> findByEstudianteIdAndEstado(Long estudianteId, PagoStatus estado);
} 