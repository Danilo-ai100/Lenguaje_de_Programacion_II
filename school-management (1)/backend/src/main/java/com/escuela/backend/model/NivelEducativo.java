package com.escuela.backend.model;

public enum NivelEducativo {
    INICIAL_3_ANOS("Inicial 3 años", 280.0),
    INICIAL_4_ANOS("Inicial 4 años", 280.0),
    INICIAL_5_ANOS("Inicial 5 años", 280.0),
    PRIMARIO_PRIMER_ANO("Primario 1er año", 280.0),
    PRIMARIO_SEGUNDO_ANO("Primario 2do año", 280.0),
    PRIMARIO_TERCER_ANO("Primario 3er año", 280.0),
    PRIMARIO_CUARTO_ANO("Primario 4to año", 280.0),
    PRIMARIO_QUINTO_ANO("Primario 5to año", 280.0),
    PRIMARIO_SEXTO_ANO("Primario 6to año", 280.0);
    
    // Constantes de precios
    public static final double COSTO_MATRICULA = 200.0;
    public static final double PENSION_REGULAR = 280.0;
    public static final double PENSION_DESCUENTO_HERMANOS = 250.0;
    
    private final String descripcion;
    private final double pensionMensual;
    
    NivelEducativo(String descripcion, double pensionMensual) {
        this.descripcion = descripcion;
        this.pensionMensual = pensionMensual;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getPensionMensual() {
        return pensionMensual;
    }
    
    public static NivelEducativo[] getNivelesIniciales() {
        return new NivelEducativo[]{INICIAL_3_ANOS, INICIAL_4_ANOS, INICIAL_5_ANOS};
    }
    
    public static NivelEducativo[] getNivelesPrimaria() {
        return new NivelEducativo[]{
            PRIMARIO_PRIMER_ANO, PRIMARIO_SEGUNDO_ANO, PRIMARIO_TERCER_ANO,
            PRIMARIO_CUARTO_ANO, PRIMARIO_QUINTO_ANO, PRIMARIO_SEXTO_ANO
        };
    }
}
