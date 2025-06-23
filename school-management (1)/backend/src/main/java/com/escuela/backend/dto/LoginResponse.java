package com.escuela.backend.dto;

import com.escuela.backend.model.TipoUsuario;

public class LoginResponse {
    
    private String token;
    private String username;
    private String nombre;
    private String email;
    private TipoUsuario tipo;
    
    // Constructores
    public LoginResponse() {}
    
    public LoginResponse(String token, String username, String nombre, String email, TipoUsuario tipo) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}
