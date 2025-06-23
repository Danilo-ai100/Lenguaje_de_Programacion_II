package com.escuela.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El username es obligatorio")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @Email(message = "Email debe tener formato válido")
    @Column(nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Relación con estudiantes (para padres)
    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Estudiante> hijos;
    
    // Constructores
    public Usuario() {}
    
    public Usuario(String username, String password, String nombre, String email, TipoUsuario tipo) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<Estudiante> getHijos() { return hijos; }
    public void setHijos(List<Estudiante> hijos) { this.hijos = hijos; }
}
