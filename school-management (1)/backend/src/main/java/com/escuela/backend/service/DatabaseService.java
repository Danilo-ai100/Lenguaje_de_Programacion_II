package com.escuela.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DatabaseService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Map<String, Object> verificarConexion() {
        try {
            // Verificar conexión básica
            String version = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
            String database = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            
            // Contar registros
            Integer usuarios = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios", Integer.class);
            Integer estudiantes = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM estudiantes", Integer.class);
            Integer pagos = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pagos", Integer.class);
            
            return Map.of(
                "estado", "CONECTADO",
                "version", version != null ? version : "Desconocida",
                "database", database != null ? database : "Desconocida",
                "usuarios", usuarios != null ? usuarios : 0,
                "estudiantes", estudiantes != null ? estudiantes : 0,
                "pagos", pagos != null ? pagos : 0,
                "mensaje", "✅ Conexión a MySQL exitosa"
            );
            
        } catch (Exception e) {
            return Map.of(
                "estado", "ERROR",
                "mensaje", "❌ Error de conexión: " + e.getMessage(),
                "error", e.getClass().getSimpleName()
            );
        }
    }
    
    public void ejecutarScript(String script) {
        try {
            jdbcTemplate.execute(script);
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando script: " + e.getMessage(), e);
        }
    }
}
