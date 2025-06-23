package com.escuela.backend.controller;

import com.escuela.backend.model.Estudiante;
import com.escuela.backend.service.EstudianteService;
import com.escuela.backend.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
@CrossOrigin(origins = "http://localhost:4200")
public class PdfController {
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping("/estudiante/{id}")
    public ResponseEntity<byte[]> generateStudentPdf(@PathVariable Long id) {
        try {
            System.out.println("📄 Generando PDF para estudiante ID: " + id);
            
            Estudiante estudiante = estudianteService.findById(id);
            if (estudiante == null) {
                return ResponseEntity.notFound().build();
            }
            
            byte[] pdfBytes = pdfService.generateStudentRegistrationPdf(estudiante);
            
            String filename = "Matricula_" + 
                estudiante.getNombres().replaceAll("\\s+", "_") + "_" + 
                estudiante.getApellidos().replaceAll("\\s+", "_") + ".pdf";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            
            System.out.println("✅ PDF generado exitosamente: " + filename);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            System.out.println("❌ Error generando PDF: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
