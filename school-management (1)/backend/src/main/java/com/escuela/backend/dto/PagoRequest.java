package com.escuela.backend.dto;

import com.escuela.backend.model.TipoPago;
import com.escuela.backend.model.MetodoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagoRequest {
    
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;
    
    @NotNull(message = "El tipo de pago es obligatorio")
    private TipoPago tipoPago;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    private MetodoPago metodoPago;
    private String numeroComprobante;
    private String observaciones;
    private String mesCorrespondiente;
    private Integer anoCorrespondiente;
    private java.time.LocalDate fechaVencimiento;

    // Constructores
    public PagoRequest() {}
    
    // Getters y Setters
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    
    public TipoPago getTipoPago() { return tipoPago; }
    public void setTipoPago(TipoPago tipoPago) { this.tipoPago = tipoPago; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    
    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String numeroComprobante) { this.numeroComprobante = numeroComprobante; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public String getMesCorrespondiente() { return mesCorrespondiente; }
    public void setMesCorrespondiente(String mesCorrespondiente) { this.mesCorrespondiente = mesCorrespondiente; }
    
    public Integer getAnoCorrespondiente() { return anoCorrespondiente; }
    public void setAnoCorrespondiente(Integer anoCorrespondiente) { this.anoCorrespondiente = anoCorrespondiente; }

    public java.time.LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(java.time.LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
