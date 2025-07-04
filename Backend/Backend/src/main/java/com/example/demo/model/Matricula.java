package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @Column(name = "anio_escolar", nullable = false)
    private Integer anioEscolar;

    @Column(name = "fecha_matricula", nullable = false)
    private Date fechaMatricula;

    @Column(name = "tipo_matricula", length = 50)
    private String tipoMatricula;

    @Column(name = "modalidad", length = 50)
    private String modalidad;

    @Column(name = "estado", length = 30)
    private String estado = "EN PROCESO";

    @Column(name = "documentos", columnDefinition = "json")
    private String documentos;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Integer getAnioEscolar() { return anioEscolar; }
    public void setAnioEscolar(Integer anioEscolar) { this.anioEscolar = anioEscolar; }

    public java.sql.Date getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(java.sql.Date fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getTipoMatricula() { return tipoMatricula; }
    public void setTipoMatricula(String tipoMatricula) { this.tipoMatricula = tipoMatricula; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDocumentos() { return documentos; }
    public void setDocumentos(String documentos) { this.documentos = documentos; }
}
