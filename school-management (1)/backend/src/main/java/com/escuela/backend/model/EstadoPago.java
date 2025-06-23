package com.escuela.backend.model;

public enum EstadoPago {
    PENDIENTE("Pendiente", "Pago pendiente de realizar"),
    AL_DIA("Al día", "Pago realizado correctamente"),
    VENCIDO("Vencido", "Pago vencido sin realizar");
    
    private final String descripcion;
    private final String detalle;
    
    EstadoPago(String descripcion, String detalle) {
        this.descripcion = descripcion;
        this.detalle = detalle;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getDetalle() {
        return detalle;
    }
}
