package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cobranza_id")
    private Cobranza cobranza;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    @Column(name = "monto_pagado", precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "comprobante_url", columnDefinition = "text")
    private String comprobanteUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_validacion", length = 30)
    private EstadoValidacion estadoValidacion = EstadoValidacion.PENDIENTE;

    public enum EstadoValidacion {
        PENDIENTE, VALIDADO, RECHAZADO
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cobranza getCobranza() { return cobranza; }
    public void setCobranza(Cobranza cobranza) { this.cobranza = cobranza; }
    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }
    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }
    public String getComprobanteUrl() { return comprobanteUrl; }
    public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }
    public EstadoValidacion getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(EstadoValidacion estadoValidacion) { this.estadoValidacion = estadoValidacion; }
}
