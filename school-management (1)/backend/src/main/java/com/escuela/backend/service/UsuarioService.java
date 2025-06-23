package com.escuela.backend.service;

import com.escuela.backend.model.Usuario;
import com.escuela.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }
    
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public java.util.List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public java.util.List<Usuario> findByTipo(com.escuela.backend.model.TipoUsuario tipo) {
        return usuarioRepository.findByTipo(tipo);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByUsernameAndIdNot(String username, Long id) {
        return usuarioRepository.existsByUsername(username) && !usuarioRepository.findById(id).map(u -> u.getUsername().equals(username)).orElse(false);
    }

    public boolean existsByEmailAndIdNot(String email, Long id) {
        return usuarioRepository.existsByEmail(email) && !usuarioRepository.findById(id).map(u -> u.getEmail().equals(email)).orElse(false);
    }

    public Usuario createFromRequest(com.escuela.backend.dto.UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setNombre(request.getNombres()); // Usar setNombre
        usuario.setEmail(request.getEmail());
        usuario.setTipo(com.escuela.backend.model.TipoUsuario.valueOf(request.getTipo()));
        return usuario;
    }

    public Usuario updateFromRequest(Usuario usuario, com.escuela.backend.dto.UsuarioRequest request) {
        usuario.setUsername(request.getUsername());
        usuario.setNombre(request.getNombres()); // Usar setNombre
        usuario.setEmail(request.getEmail());
        usuario.setTipo(com.escuela.backend.model.TipoUsuario.valueOf(request.getTipo()));
        return usuario;
    }
}
