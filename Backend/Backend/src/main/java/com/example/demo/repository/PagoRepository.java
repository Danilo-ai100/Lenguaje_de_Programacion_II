package com.example.demo.repository;

import com.example.demo.model.Pago;
import com.example.demo.model.Cobranza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCobranza(Cobranza cobranza);
    List<Pago> findByEstadoValidacion(Pago.EstadoValidacion estadoValidacion);
}
