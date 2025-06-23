package com.escuela.backend.controller;

import com.escuela.backend.dto.LoginRequest;
import com.escuela.backend.dto.LoginResponse;
import com.escuela.backend.model.Usuario;
import com.escuela.backend.service.UsuarioService;
import com.escuela.backend.config.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("🔐 Intento de login: " + loginRequest.getUsername());
            
            Usuario usuario = usuarioService.findByUsername(loginRequest.getUsername());
            
            if (usuario == null) {
                System.out.println("❌ Usuario no encontrado: " + loginRequest.getUsername());
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            
            if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                System.out.println("❌ Contraseña incorrecta para: " + loginRequest.getUsername());
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Contraseña incorrecta"));
            }
            
            if (!usuario.getActivo()) {
                System.out.println("❌ Usuario inactivo: " + loginRequest.getUsername());
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Usuario inactivo"));
            }
            
            // Generar token JWT
            String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getTipo().getRole());
            
            LoginResponse response = new LoginResponse(
                token,
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTipo()
            );
            
            System.out.println("✅ Login exitoso: " + usuario.getUsername() + " - Tipo: " + usuario.getTipo());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("💥 Error en login: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error interno del servidor"));
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Token no válido"));
            }
            
            String token = authHeader.substring(7);
            
            if (!jwtUtil.isTokenValid(token)) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Token expirado o inválido"));
            }
            
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("username", username);
            response.put("role", role);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error validando token"));
        }
    }
}
