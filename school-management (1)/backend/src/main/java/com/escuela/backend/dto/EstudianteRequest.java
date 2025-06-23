package com.escuela.backend.dto;

import com.escuela.backend.model.NivelEducativo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EstudianteRequest {
    
    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;
    
    private String dni;
    
    private LocalDate fechaNacimiento;
    
    @NotNull(message = "El nivel educativo es obligatorio")
    private NivelEducativo nivelEducativo;
    
    private String nombreApoderado;
    private String telefonoApoderado;
    private String emailApoderado;
    private String direccion;
    private String informacionMedica;
    private String anoAcademico;
    private Boolean esTraslado = false;
    private String codigoModular;
    private String institucionAnterior;
    private String motivoTraslado;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    
    // Constructores
    public EstudianteRequest() {}
    
    // Getters y Setters
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
    
    public String getAnoAcademico() { return anoAcademico; }
    public void setAnoAcademico(String anoAcademico) { this.anoAcademico = anoAcademico; }
    
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
}
