package com.example.demo.config;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
// import org.springframework.beans.factory.annotation.Autowired; // Eliminado: no se usa
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class AdminUserInitializer {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAdminUser(UsuarioRepository usuarioRepository) {
        return args -> {
            if (!usuarioRepository.findByUsername("admin").isPresent()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRol(Usuario.Rol.ADMIN);
                admin.setEstado(true);
                usuarioRepository.save(admin);
            }
        };
    }
}
