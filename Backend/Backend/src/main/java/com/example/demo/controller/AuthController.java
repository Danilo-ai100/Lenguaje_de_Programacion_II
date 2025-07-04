package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Optional<Usuario> userOpt = usuarioService.findByUsername(username);
        if (userOpt.isPresent() && usuarioService.checkPassword(password, userOpt.get().getPasswordHash()) && userOpt.get().getEstado()) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", userOpt.get().getUsername());
            response.put("rol", userOpt.get().getRol().name());
            response.put("estado", userOpt.get().getEstado());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas o usuario inactivo");
        }
    }

    // Crear cuenta admin
    @PostMapping("/crear-admin")
    public ResponseEntity<?> crearAdmin(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        if (username == null || !username.endsWith("@gmail.com")) {
            return ResponseEntity.badRequest().body("El usuario debe ser un correo Gmail válido");
        }
        if (usuarioService.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        String password = usuarioService.generarPasswordAleatoria(8);
        Usuario admin = new Usuario();
        admin.setUsername(username);
        admin.setPasswordHash(usuarioService.encodePassword(password));
        admin.setRol(Usuario.Rol.ADMIN);
        admin.setEstado(true);
        usuarioService.save(admin);
        Map<String, String> resp = new HashMap<>();
        resp.put("username", username);
        resp.put("password", password);
        return ResponseEntity.ok(resp);
    }
}
