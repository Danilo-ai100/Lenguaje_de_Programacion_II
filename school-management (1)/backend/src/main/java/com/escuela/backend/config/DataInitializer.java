package com.escuela.backend.config;

import com.escuela.backend.model.*;
import com.escuela.backend.repository.UsuarioRepository;
import com.escuela.backend.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🎨 ===== INICIALIZANDO DATOS ===== 🎨");
        
        crearUsuarios();
        crearEstudiantes();
        
        System.out.println("✨ ¡DATOS CREADOS EXITOSAMENTE! ✨");
    }
    
    private void crearUsuarios() {
        // Admin
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador Principal");
            admin.setEmail("admin@escuela.com");
            admin.setTipo(TipoUsuario.ADMIN);
            admin.setActivo(true); // Activar usuario admin
            usuarioRepository.save(admin);
            System.out.println("\uD83D\uDC68‍\uD83C\uDFEB Usuario admin creado");
        }
        
        // Padre 1
        if (!usuarioRepository.existsByUsername("padre1")) {
            Usuario padre1 = new Usuario();
            padre1.setUsername("padre1");
            padre1.setPassword(passwordEncoder.encode("padre123"));
            padre1.setNombre("María García");
            padre1.setEmail("maria.garcia@email.com");
            padre1.setTipo(TipoUsuario.PADRE);
            usuarioRepository.save(padre1);
            System.out.println("👨‍👩‍👧‍👦 Usuario padre1 creado");
        }
    }
    
    private void crearEstudiantes() {
        if (estudianteRepository.count() == 0) {
            // Estudiante 1
            Estudiante estudiante1 = new Estudiante();
            estudiante1.setNombres("Ana");
            estudiante1.setApellidos("García Pérez");
            estudiante1.setDni("12345678");
            estudiante1.setFechaNacimiento(LocalDate.of(2020, 3, 15));
            estudiante1.setNivelEducativo(NivelEducativo.INICIAL_4_ANOS);
            estudiante1.setNombreApoderado("María García");
            estudiante1.setTelefonoApoderado("987654321");
            estudiante1.setEmailApoderado("maria.garcia@email.com");
            estudiante1.setDireccion("Av. Los Olivos 123, Lima");
            estudiante1.setEstadoPago(EstadoPago.AL_DIA);
            estudiante1.setAnoAcademico("2024");
            estudianteRepository.save(estudiante1);
            System.out.println("🌟 Ana García creada");
            
            // Estudiante 2
            Estudiante estudiante2 = new Estudiante();
            estudiante2.setNombres("Carlos");
            estudiante2.setApellidos("López Mendoza");
            estudiante2.setDni("87654321");
            estudiante2.setFechaNacimiento(LocalDate.of(2018, 7, 22));
            estudiante2.setNivelEducativo(NivelEducativo.PRIMARIO_PRIMER_ANO);
            estudiante2.setNombreApoderado("Juan López");
            estudiante2.setTelefonoApoderado("912345678");
            estudiante2.setEmailApoderado("juan.lopez@email.com");
            estudiante2.setDireccion("Jr. Las Flores 456, Lima");
            estudiante2.setEstadoPago(EstadoPago.PENDIENTE);
            estudiante2.setAnoAcademico("2024");
            estudianteRepository.save(estudiante2);
            System.out.println("📚 Carlos López creado");
        }
    }
}
