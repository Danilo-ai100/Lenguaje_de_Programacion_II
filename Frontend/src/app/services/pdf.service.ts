import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';

@Injectable({ providedIn: 'root' })
export class PdfService {
  generarMatriculaPDF(data: any) {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text('FORMULARIO DE MATRÍCULA', 105, 18, { align: 'center' });
    doc.setFontSize(12);
    let y = 30;
    // Datos del Estudiante
    doc.setFillColor(230, 240, 255);
    doc.rect(10, y - 6, 190, 10, 'F');
    doc.setFont('helvetica', 'bold');
    doc.text('Datos del Estudiante', 15, y);
    doc.setFont('helvetica', 'normal');
    y += 10;
    doc.text('Nombre completo:', 15, y); doc.text(`${data.nombreCompleto || ''}`, 65, y);
    y += 8;
    doc.text('Apellido paterno:', 15, y); doc.text(`${data.apellidoPaterno || ''}`, 65, y);
    y += 8;
    doc.text('Apellido materno:', 15, y); doc.text(`${data.apellidoMaterno || ''}`, 65, y);
    y += 8;
    doc.text('DNI:', 15, y); doc.text(`${data.dniEstudiante || ''}`, 65, y);
    y += 8;
    doc.text('Año académico:', 15, y); doc.text(`${data.anioAcademico || ''}`, 65, y);
    y += 8;
    doc.text('Grado:', 15, y); doc.text(`${data.grado || ''}`, 65, y);
    y += 8;
    doc.text('Sección:', 15, y); doc.text(`${data.seccion || ''}`, 65, y);
    y += 8;
    doc.text('Fecha de nacimiento:', 15, y); doc.text(`${data.fechaNacimiento || ''}`, 65, y);
    y += 12;
    // Datos del Apoderado
    doc.setFillColor(230, 240, 255);
    doc.rect(10, y - 6, 190, 10, 'F');
    doc.setFont('helvetica', 'bold');
    doc.text('Datos del Apoderado', 15, y);
    doc.setFont('helvetica', 'normal');
    y += 10;
    doc.text('Nombre completo:', 15, y); doc.text(`${data.nombreCompletoApoderado || ''}`, 65, y);
    y += 8;
    doc.text('Apellidos completos:', 15, y); doc.text(`${data.apellidosCompletosApoderado || ''}`, 65, y);
    y += 8;
    doc.text('DNI:', 15, y); doc.text(`${data.dniApoderado || ''}`, 65, y);
    y += 8;
    doc.text('Celular:', 15, y); doc.text(`${data.celularApoderado || ''}`, 65, y);
    y += 12;
    // Contacto de Emergencia
    doc.setFillColor(230, 240, 255);
    doc.rect(10, y - 6, 190, 10, 'F');
    doc.setFont('helvetica', 'bold');
    doc.text('Contacto de Emergencia', 15, y);
    doc.setFont('helvetica', 'normal');
    y += 10;
    doc.text('Nombre:', 15, y); doc.text(`${data.nombreContactoEmergencia || ''}`, 65, y);
    y += 8;
    doc.text('Celular:', 15, y); doc.text(`${data.celularContactoEmergencia || ''}`, 65, y);
    y += 12;
    // Institución de procedencia (si traslado)
    if (data.traslado) {
      doc.setFillColor(230, 240, 255);
      doc.rect(10, y - 6, 190, 10, 'F');
      doc.setFont('helvetica', 'bold');
      doc.text('Institución de Procedencia', 15, y);
      doc.setFont('helvetica', 'normal');
      y += 10;
      doc.text('Nombre:', 15, y); doc.text(`${data.institucionProcedencia || ''}`, 65, y);
      y += 8;
      doc.text('Nivel:', 15, y); doc.text(`${data.nivelProcedencia || ''}`, 65, y);
      y += 8;
      doc.text('Código modular:', 15, y); doc.text(`${data.codigoModular || ''}`, 65, y);
      y += 12;
    }
    // Pie de página
    doc.setFontSize(10);
    doc.text('Fecha de generación: ' + new Date().toLocaleDateString(), 15, 285);
    doc.save('matricula.pdf');
  }
}
