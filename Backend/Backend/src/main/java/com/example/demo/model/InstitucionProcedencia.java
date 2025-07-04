package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre", "nivel"})})
public class InstitucionProcedencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String nivel; // INICIAL o PRIMARIA

    @Column(nullable = false)
    private String codigoModular;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getCodigoModular() { return codigoModular; }
    public void setCodigoModular(String codigoModular) { this.codigoModular = codigoModular; }
}
