package com.escuela.backend.model;

public enum MetodoPago {
    EFECTIVO("Efectivo", "Pago en efectivo"),
    TRANSFERENCIA("Transferencia", "Transferencia bancaria"),
    DEPOSITO("Depósito", "Depósito bancario"),
    TARJETA("Tarjeta", "Pago con tarjeta"),
    YAPE("Yape", "Pago con Yape"),
    PLIN("Plin", "Pago con Plin");
    
    private final String descripcion;
    private final String detalle;
    
    MetodoPago(String descripcion, String detalle) {
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
