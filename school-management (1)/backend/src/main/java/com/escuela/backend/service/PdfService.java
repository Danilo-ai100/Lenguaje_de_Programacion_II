package com.escuela.backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Chunk;
import com.escuela.backend.model.Estudiante;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(30, 60, 114));
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    private static final Font LABEL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);
    
    public byte[] generateStudentRegistrationPdf(Estudiante estudiante) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        
        document.open();
        
        // Encabezado institucional
        addInstitutionalHeader(document);
        
        // Título del documento
        addDocumentTitle(document);
        
        // Información del estudiante
        addStudentInfo(document, estudiante);
        
        // Información del apoderado
        addParentInfo(document, estudiante);
        
        // Información de traslado (si aplica)
        if (Boolean.TRUE.equals(estudiante.getEsTraslado())) {
            addTransferInfo(document, estudiante);
        }
        
        // Información médica y de emergencia
        addMedicalAndEmergencyInfo(document, estudiante);
        
        // Información académica y de costos
        addAcademicAndCostInfo(document, estudiante);
        
        // Declaración y firmas
        addDeclarationAndSignatures(document);
        
        // Pie de página
        addFooter(document);
        
        document.close();
        return baos.toByteArray();
    }
    
    private void addInstitutionalHeader(Document document) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 3, 1});
        headerTable.setSpacingAfter(20);
        
        // Logo (simulado)
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        logoCell.addElement(new Paragraph("🏫", new Font(Font.FontFamily.HELVETICA, 40)));
        
        // Información institucional
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        Paragraph institutionName = new Paragraph("I.E.P. DAYIVA SCHOOL", TITLE_FONT);
        institutionName.setAlignment(Element.ALIGN_CENTER);
        
        Paragraph subtitle = new Paragraph("Institución Educativa Privada", NORMAL_FONT);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        
        Paragraph location = new Paragraph("Juliaca - Puno - Perú", SMALL_FONT);
        location.setAlignment(Element.ALIGN_CENTER);
        location.setSpacingAfter(5);
        
        infoCell.addElement(institutionName);
        infoCell.addElement(subtitle);
        infoCell.addElement(location);
        
        // Espacio derecho
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        
        headerTable.addCell(logoCell);
        headerTable.addCell(infoCell);
        headerTable.addCell(rightCell);
        
        document.add(headerTable);
        
        // Línea separadora
        LineSeparator line = new LineSeparator();
        line.setLineColor(new BaseColor(30, 60, 114));
        line.setLineWidth(2);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }
    
    private void addDocumentTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("FICHA DE MATRÍCULA", HEADER_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);
        
        Paragraph academicYear = new Paragraph("AÑO ACADÉMICO 2024", NORMAL_FONT);
        academicYear.setAlignment(Element.ALIGN_CENTER);
        academicYear.setSpacingAfter(20);
        document.add(academicYear);
    }
    
    private void addStudentInfo(Document document, Estudiante estudiante) throws DocumentException {
        addSectionTitle(document, "I. INFORMACIÓN DEL ESTUDIANTE");
        
        PdfPTable table = createInfoTable();
        
        addTableRow(table, "Nombres:", estudiante.getNombres());
        addTableRow(table, "Apellidos:", estudiante.getApellidos());
        addTableRow(table, "DNI:", estudiante.getDni() != null ? estudiante.getDni() : "No especificado");
        addTableRow(table, "Fecha de Nacimiento:", 
            estudiante.getFechaNacimiento() != null ? 
            estudiante.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : 
            "No especificada");
        addTableRow(table, "Nivel Educativo:", getNivelDisplayName(estudiante.getNivelEducativo()));
        addTableRow(table, "Año Académico:", estudiante.getAnoAcademico() != null ? estudiante.getAnoAcademico() : "2024");
        addTableRow(table, "Fecha de Matrícula:", 
            estudiante.getFechaMatricula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addParentInfo(Document document, Estudiante estudiante) throws DocumentException {
        addSectionTitle(document, "II. INFORMACIÓN DEL APODERADO");
        
        PdfPTable table = createInfoTable();
        
        addTableRow(table, "Nombre del Apoderado:", 
            estudiante.getNombreApoderado() != null ? estudiante.getNombreApoderado() : "No especificado");
        addTableRow(table, "Teléfono:", 
            estudiante.getTelefonoApoderado() != null ? estudiante.getTelefonoApoderado() : "No especificado");
        addTableRow(table, "Email:", 
            estudiante.getEmailApoderado() != null ? estudiante.getEmailApoderado() : "No especificado");
        addTableRow(table, "Dirección:", 
            estudiante.getDireccion() != null ? estudiante.getDireccion() : "No especificada");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addTransferInfo(Document document, Estudiante estudiante) throws DocumentException {
        addSectionTitle(document, "III. INFORMACIÓN DE TRASLADO");
        
        PdfPTable table = createInfoTable();
        
        addTableRow(table, "Código Modular:", 
            estudiante.getCodigoModular() != null ? estudiante.getCodigoModular() : "No especificado");
        addTableRow(table, "Institución Anterior:", 
            estudiante.getInstitucionAnterior() != null ? estudiante.getInstitucionAnterior() : "No especificada");
        addTableRow(table, "Motivo del Traslado:", 
            estudiante.getMotivoTraslado() != null ? estudiante.getMotivoTraslado() : "No especificado");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addMedicalAndEmergencyInfo(Document document, Estudiante estudiante) throws DocumentException {
        addSectionTitle(document, "IV. INFORMACIÓN MÉDICA Y DE EMERGENCIA");
        
        PdfPTable table = createInfoTable();
        
        addTableRow(table, "Contacto de Emergencia:", 
            estudiante.getContactoEmergencia() != null ? estudiante.getContactoEmergencia() : "No especificado");
        addTableRow(table, "Teléfono de Emergencia:", 
            estudiante.getTelefonoEmergencia() != null ? estudiante.getTelefonoEmergencia() : "No especificado");
        addTableRow(table, "Información Médica:", 
            estudiante.getInformacionMedica() != null ? estudiante.getInformacionMedica() : "Ninguna");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addAcademicAndCostInfo(Document document, Estudiante estudiante) throws DocumentException {
        addSectionTitle(document, "V. INFORMACIÓN ACADÉMICA Y ECONÓMICA");
        
        PdfPTable table = createInfoTable();
        
        addTableRow(table, "Nivel Educativo:", getNivelDisplayName(estudiante.getNivelEducativo()));
        addTableRow(table, "Pensión Mensual:", "S/ " + String.format("%.2f", estudiante.getPensionMensual()));
        addTableRow(table, "Estado de Pago:", getEstadoDisplayName(estudiante.getEstadoPago()));
        addTableRow(table, "Es Traslado:", estudiante.getEsTraslado() ? "Sí" : "No");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addDeclarationAndSignatures(Document document) throws DocumentException {
        addSectionTitle(document, "VI. DECLARACIÓN JURADA");
        
        Paragraph declaration = new Paragraph(
            "Declaro bajo juramento que la información proporcionada en esta ficha de matrícula es " +
            "veraz y completa. Me comprometo a cumplir con las normas, reglamentos y disposiciones " +
            "de la I.E.P. DAYIVA SCHOOL, así como con las obligaciones económicas contraídas.",
            NORMAL_FONT);
        declaration.setAlignment(Element.ALIGN_JUSTIFIED);
        declaration.setSpacingAfter(30);
        document.add(declaration);
        
        // Tabla de firmas
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingAfter(20);
        
        PdfPCell leftSignature = new PdfPCell();
        leftSignature.setBorder(Rectangle.NO_BORDER);
        leftSignature.setHorizontalAlignment(Element.ALIGN_CENTER);
        leftSignature.setPaddingTop(20);
        leftSignature.addElement(new Paragraph("_____________________________", NORMAL_FONT));
        leftSignature.addElement(new Paragraph("Firma del Apoderado", SMALL_FONT));
        leftSignature.addElement(new Paragraph("DNI: _______________", SMALL_FONT));
        
        PdfPCell rightSignature = new PdfPCell();
        rightSignature.setBorder(Rectangle.NO_BORDER);
        rightSignature.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightSignature.setPaddingTop(20);
        rightSignature.addElement(new Paragraph("_____________________________", NORMAL_FONT));
        rightSignature.addElement(new Paragraph("Director(a)", SMALL_FONT));
        rightSignature.addElement(new Paragraph("I.E.P. DAYIVA SCHOOL", SMALL_FONT));
        
        signatureTable.addCell(leftSignature);
        signatureTable.addCell(rightSignature);
        
        document.add(signatureTable);
        
        // Fecha
        Paragraph date = new Paragraph("Juliaca, _____ de _____________ de 2024", NORMAL_FONT);
        date.setAlignment(Element.ALIGN_CENTER);
        document.add(date);
    }
    
    private void addFooter(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        
        Paragraph footer = new Paragraph(
            "Este documento es de uso exclusivo de la I.E.P. DAYIVA SCHOOL y forma parte del expediente académico del estudiante.",
            SMALL_FONT);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.getFont().setColor(BaseColor.GRAY);
        document.add(footer);
    }
    
    // Métodos auxiliares
    private void addSectionTitle(Document document, String title) throws DocumentException {
        Paragraph sectionTitle = new Paragraph(title, HEADER_FONT);
        sectionTitle.setSpacingBefore(10);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);
    }
    
    private PdfPTable createInfoTable() {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);
        try {
            table.setWidths(new float[]{2.5f, 4f});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return table;
    }
    
    private void addTableRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, LABEL_FONT));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(8);
        labelCell.setPaddingTop(5);
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        labelCell.setBackgroundColor(new BaseColor(248, 249, 250));
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "No especificado", NORMAL_FONT));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPaddingBottom(8);
        valueCell.setPaddingTop(5);
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
    
    private String getNivelDisplayName(com.escuela.backend.model.NivelEducativo nivel) {
        return nivel.getDescripcion();
    }
    
    private String getEstadoDisplayName(com.escuela.backend.model.EstadoPago estado) {
        switch (estado) {
            case AL_DIA: return "Al día";
            case PENDIENTE: return "Pendiente";
            case VENCIDO: return "Vencido";
            default: return estado.name();
        }
    }
}
