package com.example.demo.service;

import com.example.demo.dto.MatriculaRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Date;

@Service
public class MatriculaService {
    @Autowired
    private ApoderadoRepository apoderadoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String generarPasswordAleatoria(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static class MatriculaResult {
        public final Matricula matricula;
        public final String passwordGenerada;
        public MatriculaResult(Matricula matricula, String passwordGenerada) {
            this.matricula = matricula;
            this.passwordGenerada = passwordGenerada;
        }
    }

    @Transactional
    public MatriculaResult registrarMatricula(MatriculaRequest req) {
        // Buscar o crear usuario/apoderado
        Apoderado apoderado = apoderadoRepository.findByDni(req.dniApoderado).orElse(null);
        String passwordGenerada = null;
        if (apoderado == null) {
            apoderado = new Apoderado();
            apoderado.setNombres(req.nombresApoderado);
            apoderado.setApellidoPaterno(req.apellidoPaternoApoderado);
            apoderado.setApellidoMaterno(req.apellidoMaternoApoderado);
            apoderado.setDni(req.dniApoderado);
            apoderado.setTelefono(req.telefonoApoderado);
            apoderado.setEmail(req.emailApoderado);
            // Crear usuario si no existe
            Usuario usuario = usuarioRepository.findByUsername(req.dniApoderado).orElse(null);
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setUsername(req.dniApoderado);
                passwordGenerada = generarPasswordAleatoria(8);
                usuario.setPasswordHash(passwordEncoder.encode(passwordGenerada));
                usuario.setRol(Usuario.Rol.APODERADO);
                usuario.setEstado(true);
                usuarioRepository.save(usuario);
            }
            apoderado.setUsuario(usuario);
            apoderadoRepository.save(apoderado);
        }
        // Crear estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setNombres(req.nombres);
        estudiante.setApellidoPaterno(req.apellidoPaterno);
        estudiante.setApellidoMaterno(req.apellidoMaterno);
        estudiante.setDni(req.dniEstudiante);
        estudiante.setFechaNacimiento(req.fechaNacimiento);
        estudiante.setGrado(req.grado);
        estudiante.setSeccion(req.seccion);
        estudiante.setApoderado(apoderado);
        estudianteRepository.save(estudiante);
        // Crear matrícula
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setAnioEscolar(req.anioEscolar);
        matricula.setFechaMatricula(req.fechaMatricula != null ? req.fechaMatricula : new Date(System.currentTimeMillis()));
        matricula.setTipoMatricula(req.tipoMatricula);
        matricula.setModalidad(req.modalidad);
        matricula.setEstado("EN PROCESO");
        matricula.setDocumentos(req.documentos);
        matriculaRepository.save(matricula);
        return new MatriculaResult(matricula, passwordGenerada);
    }
}
