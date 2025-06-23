package com.escuela.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Column(nullable = false)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false)
    private String apellidos;
    
    @Column(unique = true)
    private String dni;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelEducativo nivelEducativo;
    
    @Column(name = "nombre_apoderado")
    private String nombreApoderado;
    
    @Column(name = "telefono_apoderado")
    private String telefonoApoderado;
    
    @Column(name = "email_apoderado")
    private String emailApoderado;
    
    private String direccion;
    
    @Column(name = "informacion_medica", columnDefinition = "TEXT")
    private String informacionMedica;
    
    @Column(name = "fecha_matricula")
    private LocalDateTime fechaMatricula = LocalDateTime.now();
    
    @Column(name = "ano_academico")
    private String anoAcademico;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;
    
    // Campos para traslados
    @Column(name = "es_traslado")
    private Boolean esTraslado = false;
    
    @Column(name = "codigo_modular")
    private String codigoModular;
    
    @Column(name = "institucion_anterior")
    private String institucionAnterior;
    
    @Column(name = "motivo_traslado", columnDefinition = "TEXT")
    private String motivoTraslado;
    
    // Contactos de emergencia
    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;
    
    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;
    
    // Relación con el padre (usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    private Usuario padre;
    
    // Relación con pagos
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;
    
    // Constructores
    public Estudiante() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public NivelEducativo getNivelEducativo() { return nivelEducativo; }
    public void setNivelEducativo(NivelEducativo nivelEducativo) { this.nivelEducativo = nivelEducativo; }
    
    public String getNombreApoderado() { return nombreApoderado; }
    public void setNombreApoderado(String nombreApoderado) { this.nombreApoderado = nombreApoderado; }
    
    public String getTelefonoApoderado() { return telefonoApoderado; }
    public void setTelefonoApoderado(String telefonoApoderado) { this.telefonoApoderado = telefonoApoderado; }
    
    public String getEmailApoderado() { return emailApoderado; }
    public void setEmailApoderado(String emailApoderado) { this.emailApoderado = emailApoderado; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getInformacionMedica() { return informacionMedica; }
    public void setInformacionMedica(String informacionMedica) { this.informacionMedica = informacionMedica; }
    
    public LocalDateTime getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDateTime fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    
    public String getAnoAcademico() { return anoAcademico; }
    public void setAnoAcademico(String anoAcademico) { this.anoAcademico = anoAcademico; }
    
    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }
    
    public Boolean getEsTraslado() { return esTraslado; }
    public void setEsTraslado(Boolean esTraslado) { this.esTraslado = esTraslado; }
    
    public String getCodigoModular() { return codigoModular; }
    public void setCodigoModular(String codigoModular) { this.codigoModular = codigoModular; }
    
    public String getInstitucionAnterior() { return institucionAnterior; }
    public void setInstitucionAnterior(String institucionAnterior) { this.institucionAnterior = institucionAnterior; }
    
    public String getMotivoTraslado() { return motivoTraslado; }
    public void setMotivoTraslado(String motivoTraslado) { this.motivoTraslado = motivoTraslado; }
    
    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }
    
    public String getTelefonoEmergencia() { return telefonoEmergencia; }
    public void setTelefonoEmergencia(String telefonoEmergencia) { this.telefonoEmergencia = telefonoEmergencia; }
    
    public Usuario getPadre() { return padre; }
    public void setPadre(Usuario padre) { this.padre = padre; }
    
    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    public double getPensionMensual() {
        return nivelEducativo != null ? nivelEducativo.getPensionMensual() : 0.0;
    }
}
