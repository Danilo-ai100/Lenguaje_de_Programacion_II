package com.escuela.backend.controller;

import com.escuela.backend.dto.PagoRequest;
import com.escuela.backend.model.Pago;
import com.escuela.backend.model.Estudiante;
import com.escuela.backend.model.Usuario;
import com.escuela.backend.service.PagoService;
import com.escuela.backend.service.EstudianteService;
import com.escuela.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "http://localhost:4200")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Pago>> getAllPagos() {
        System.out.println("💰 Obteniendo todos los pagos");
        List<Pago> pagos = pagoService.findAll();
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/mis-pagos")
    @PreAuthorize("hasRole('PADRE')")
    public ResponseEntity<?> getMisPagos(Authentication authentication) {
        try {
            String username = authentication.getName();
            System.out.println("👨‍👩‍👧‍👦 Obteniendo pagos para padre: " + username);
            
            Usuario padre = usuarioService.findByUsername(username);
            if (padre == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            
            List<Pago> pagos = pagoService.findByPadre(padre);
            return ResponseEntity.ok(pagos);
            
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo pagos del padre: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error obteniendo pagos"));
        }
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<?> getPagosByEstudiante(@PathVariable Long estudianteId, Authentication authentication) {
        try {
            String username = authentication.getName();
            Usuario usuario = usuarioService.findByUsername(username);
            
            Estudiante estudiante = estudianteService.findById(estudianteId);
            if (estudiante == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Si es padre, verificar que el estudiante sea su hijo
            if (usuario.getTipo().name().equals("PADRE")) {
                if (!estudiante.getPadre().getId().equals(usuario.getId())) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "No tiene permisos para ver los pagos de este estudiante"));
                }
            }
            
            List<Pago> pagos = pagoService.findByEstudiante(estudiante);
            return ResponseEntity.ok(pagos);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error obteniendo pagos del estudiante"));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createPago(@Valid @RequestBody PagoRequest request, Authentication authentication) {
        try {
            String username = authentication.getName();
            Usuario usuario = usuarioService.findByUsername(username);
            
            System.out.println("💳 Registrando pago para estudiante ID: " + request.getEstudianteId());
            
            Estudiante estudiante = estudianteService.findById(request.getEstudianteId());
            if (estudiante == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Estudiante no encontrado"));
            }
            
            // Si es padre, verificar que el estudiante sea su hijo
            if (usuario.getTipo().name().equals("PADRE")) {
                if (!estudiante.getPadre().getId().equals(usuario.getId())) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "No puede registrar pagos para este estudiante"));
                }
            }
            
            Pago pago = pagoService.createFromRequest(request, estudiante);
            Pago savedPago = pagoService.save(pago);
            
            // Actualizar estado del estudiante
            estudianteService.actualizarEstadoPago(estudiante);
            
            System.out.println("✅ Pago registrado: " + savedPago.getId());
            
            return ResponseEntity.ok(Map.of(
                "message", "Pago registrado exitosamente",
                "pago", savedPago
            ));
            
        } catch (Exception e) {
            System.out.println("❌ Error registrando pago: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error registrando pago: " + e.getMessage()));
        }
    }
    
    @PostMapping("/generar-mensualidades/{estudianteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generarMensualidades(@PathVariable Long estudianteId) {
        try {
            System.out.println("📅 Generando mensualidades para estudiante ID: " + estudianteId);
            
            Estudiante estudiante = estudianteService.findById(estudianteId);
            if (estudiante == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Estudiante no encontrado"));
            }
            
            List<Pago> mensualidades = pagoService.generarMensualidades(estudiante);
            
            System.out.println("✅ Mensualidades generadas: " + mensualidades.size());
            
            return ResponseEntity.ok(Map.of(
                "message", "Mensualidades generadas exitosamente",
                "mensualidades", mensualidades
            ));
            
        } catch (Exception e) {
            System.out.println("❌ Error generando mensualidades: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error generando mensualidades: " + e.getMessage()));
        }
    }
    
    @GetMapping("/estadisticas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEstadisticasPagos() {
        Map<String, Object> estadisticas = pagoService.obtenerEstadisticasPagos();
        return ResponseEntity.ok(estadisticas);
    }
}
