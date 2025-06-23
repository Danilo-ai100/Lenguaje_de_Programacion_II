package com.escuela.backend.controller;

import com.escuela.backend.dto.UsuarioRequest;
import com.escuela.backend.model.Usuario;
import com.escuela.backend.model.TipoUsuario;
import com.escuela.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        System.out.println("👥 Obteniendo todos los usuarios");
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioRequest request) {
        try {
            System.out.println("➕ Creando nuevo usuario: " + request.getUsername());
            
            // Validar que el username no exista
            if (usuarioService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya existe un usuario con este username"));
            }
            
            // Validar que el email no exista
            if (usuarioService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya existe un usuario con este email"));
            }
            
            Usuario usuario = usuarioService.createFromRequest(request);
            Usuario savedUsuario = usuarioService.save(usuario);
            
            System.out.println("✅ Usuario creado: " + savedUsuario.getUsername());
            
            return ResponseEntity.ok(Map.of(
                "message", "Usuario creado exitosamente",
                "usuario", savedUsuario,
                "passwordGenerado", request.getPassword() // Solo para mostrar al admin
            ));
            
        } catch (Exception e) {
            System.out.println("❌ Error creando usuario: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error creando usuario: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        try {
            System.out.println("🔄 Actualizando usuario ID: " + id);
            
            Usuario existingUsuario = usuarioService.findById(id);
            if (existingUsuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Validar username único (excluyendo el usuario actual)
            if (usuarioService.existsByUsernameAndIdNot(request.getUsername(), id)) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya existe un usuario con este username"));
            }
            
            // Validar email único (excluyendo el usuario actual)
            if (usuarioService.existsByEmailAndIdNot(request.getEmail(), id)) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya existe un usuario con este email"));
            }
            
            Usuario updatedUsuario = usuarioService.updateFromRequest(existingUsuario, request);
            Usuario savedUsuario = usuarioService.save(updatedUsuario);
            
            System.out.println("✅ Usuario actualizado: " + savedUsuario.getUsername());
            
            return ResponseEntity.ok(Map.of(
                "message", "Usuario actualizado exitosamente",
                "usuario", savedUsuario
            ));
            
        } catch (Exception e) {
            System.out.println("❌ Error actualizando usuario: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error actualizando usuario: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Eliminando usuario ID: " + id);
            
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            // No permitir eliminar el admin principal
            if (usuario.getUsername().equals("admin")) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "No se puede eliminar el usuario administrador principal"));
            }
            
            usuarioService.deleteById(id);
            
            System.out.println("✅ Usuario eliminado: " + usuario.getUsername());
            
            return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
            
        } catch (Exception e) {
            System.out.println("❌ Error eliminando usuario: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error eliminando usuario: " + e.getMessage()));
        }
    }
    
    @PostMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            usuario.setActivo(!usuario.getActivo());
            usuarioService.save(usuario);
            
            String status = usuario.getActivo() ? "activado" : "desactivado";
            System.out.println("🔄 Usuario " + status + ": " + usuario.getUsername());
            
            return ResponseEntity.ok(Map.of(
                "message", "Usuario " + status + " exitosamente",
                "usuario", usuario
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error cambiando estado del usuario"));
        }
    }
    
    @GetMapping("/padres")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getPadres() {
        List<Usuario> padres = usuarioService.findByTipo(TipoUsuario.PADRE);
        return ResponseEntity.ok(padres);
    }
}
