package com.escuela.backend.model;

public enum TipoPago {
    MATRICULA("Matrícula", "Pago único de matrícula anual"),
    PENSION_REGULAR("Pensión Regular", "Pensión mensual sin descuento"),
    PENSION_DESCUENTO("Pensión con Descuento", "Pensión mensual con descuento por hermanos");
    
    private final String descripcion;
    private final String detalle;
    
    TipoPago(String descripcion, String detalle) {
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
