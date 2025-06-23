package com.escuela.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private TipoPago tipoPago;
    
    @Column(name = "monto", nullable = false)
    private Double monto;
    
    @Column(name = "monto_original")
    private Double montoOriginal;
    
    @Column(name = "descuento_aplicado")
    private Double descuentoAplicado = 0.0;
    
    @Column(name = "tiene_descuento_hermanos")
    private Boolean tieneDescuentoHermanos = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;
    
    @Column(name = "mes_correspondiente")
    private String mesCorrespondiente;
    
    @Column(name = "ano_correspondiente")
    private Integer anoCorrespondiente;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "numero_comprobante")
    private String numeroComprobante;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Constructores
    public Pago() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public TipoPago getTipoPago() { return tipoPago; }
    public void setTipoPago(TipoPago tipoPago) { this.tipoPago = tipoPago; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public Double getMontoOriginal() { return montoOriginal; }
    public void setMontoOriginal(Double montoOriginal) { this.montoOriginal = montoOriginal; }
    
    public Double getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(Double descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    
    public Boolean getTieneDescuentoHermanos() { return tieneDescuentoHermanos; }
    public void setTieneDescuentoHermanos(Boolean tieneDescuentoHermanos) { this.tieneDescuentoHermanos = tieneDescuentoHermanos; }
    
    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }
    
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    
    public String getMesCorrespondiente() { return mesCorrespondiente; }
    public void setMesCorrespondiente(String mesCorrespondiente) { this.mesCorrespondiente = mesCorrespondiente; }
    
    public Integer getAnoCorrespondiente() { return anoCorrespondiente; }
    public void setAnoCorrespondiente(Integer anoCorrespondiente) { this.anoCorrespondiente = anoCorrespondiente; }
    
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String numeroComprobante) { this.numeroComprobante = numeroComprobante; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    // Métodos de utilidad
    public void aplicarDescuentoHermanos() {
        if (this.tipoPago == TipoPago.PENSION_REGULAR) {
            this.montoOriginal = this.monto;
            this.monto = NivelEducativo.PENSION_DESCUENTO_HERMANOS;
            this.descuentoAplicado = NivelEducativo.PENSION_REGULAR - NivelEducativo.PENSION_DESCUENTO_HERMANOS;
            this.tieneDescuentoHermanos = true;
            this.tipoPago = TipoPago.PENSION_DESCUENTO;
        }
    }
    
    public boolean estaVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now()) && estadoPago == EstadoPago.PENDIENTE;
    }
}
