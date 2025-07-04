package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "cobranza")
public class Cobranza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @Column(name = "concepto", length = 100)
    private String concepto;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private java.math.BigDecimal monto;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "estado", length = 30)
    private String estado = "PENDIENTE";

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public java.math.BigDecimal getMonto() { return monto; }
    public void setMonto(java.math.BigDecimal monto) { this.monto = monto; }

    public java.sql.Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(java.sql.Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
