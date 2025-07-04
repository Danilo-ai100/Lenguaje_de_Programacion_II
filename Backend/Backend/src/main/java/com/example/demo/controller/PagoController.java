package com.example.demo.controller;

import com.example.demo.model.Pago;
import com.example.demo.model.Cobranza;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.CobranzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private CobranzaRepository cobranzaRepository;

    // Endpoint para servir archivos de comprobante
    @GetMapping("/archivo/{filename:.+}")
    public ResponseEntity<Resource> getArchivo(@PathVariable String filename) {
        try {
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = "application/octet-stream";
            if (filename.endsWith(".png")) contentType = "image/png";
            else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) contentType = "image/jpeg";
            else if (filename.endsWith(".pdf")) contentType = "application/pdf";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPago(@PathVariable Long id) {
        Optional<Pago> pago = pagoRepository.findById(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cobranza/{cobranzaId}")
    public List<Pago> listarPagosPorCobranza(@PathVariable Long cobranzaId) {
        Cobranza cobranza = cobranzaRepository.findById(cobranzaId).orElse(null);
        if (cobranza == null) return List.of();
        return pagoRepository.findByCobranza(cobranza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPago(@PathVariable Long id, @RequestBody Pago data) {
        Optional<Pago> pagoOpt = pagoRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setFechaPago(data.getFechaPago());
            pago.setMontoPagado(data.getMontoPagado());
            pago.setComprobanteUrl(data.getComprobanteUrl());
            pago.setEstadoValidacion(data.getEstadoValidacion());
            pagoRepository.save(pago);
            return ResponseEntity.ok(pago);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/subir-comprobante/{pagoId}")
    public ResponseEntity<?> subirComprobante(@PathVariable Long pagoId, @RequestParam("file") MultipartFile file) throws IOException {
        Optional<Pago> pagoOpt = pagoRepository.findById(pagoId);
        if (pagoOpt.isEmpty()) return ResponseEntity.notFound().build();
        Pago pago = pagoOpt.get();
        // Usar ruta absoluta y fija para la carpeta uploads en la raíz del proyecto
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        pago.setComprobanteUrl(filePath);
        pago.setEstadoValidacion(Pago.EstadoValidacion.PENDIENTE);
        pagoRepository.save(pago);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Pago>> pagosPendientes() {
        return ResponseEntity.ok(pagoRepository.findByEstadoValidacion(Pago.EstadoValidacion.PENDIENTE));
    }

    @PostMapping("/validar/{pagoId}")
    public ResponseEntity<?> validarPago(@PathVariable Long pagoId, @RequestParam boolean valido) {
        Optional<Pago> pagoOpt = pagoRepository.findById(pagoId);
        if (pagoOpt.isEmpty()) return ResponseEntity.notFound().build();
        Pago pago = pagoOpt.get();
        if (valido) {
            pago.setEstadoValidacion(Pago.EstadoValidacion.VALIDADO);
        } else {
            pago.setEstadoValidacion(Pago.EstadoValidacion.RECHAZADO);
        }
        pagoRepository.save(pago);
        return ResponseEntity.ok().build();
    }
}
