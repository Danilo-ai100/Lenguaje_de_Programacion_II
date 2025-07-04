package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminInfoController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/celular")
    public ResponseEntity<String> getCelularAdmin() {
        // Puedes cambiar el username "admin" si usas otro
        Optional<Usuario> admin = usuarioRepository.findByUsername("admin");
        // Aquí puedes devolver un número fijo o, si tienes un campo celular en Usuario, devolverlo
        String celular = admin.map(u -> "+51 951 646 408").orElse("No disponible");
        return ResponseEntity.ok(celular);
    }
}
