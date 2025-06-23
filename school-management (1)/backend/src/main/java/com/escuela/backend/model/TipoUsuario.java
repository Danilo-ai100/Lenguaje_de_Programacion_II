package com.escuela.backend.model;

public enum TipoUsuario {
    ADMIN("ROLE_ADMIN", "Administrador"),
    PADRE("ROLE_PADRE", "Padre de Familia");
    
    private final String role;
    private final String descripcion;
    
    TipoUsuario(String role, String descripcion) {
        this.role = role;
        this.descripcion = descripcion;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
