package com.example.demo.dto;

import java.sql.Date;

public class MatriculaRequest {
    // Estudiante
    public String nombres;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String dniEstudiante;
    public Date fechaNacimiento;
    public String grado;
    public String seccion;

    // Apoderado
    public String nombresApoderado;
    public String apellidoPaternoApoderado;
    public String apellidoMaternoApoderado;
    public String dniApoderado;
    public String telefonoApoderado;
    public String emailApoderado;

    // Matrícula
    public Integer anioEscolar;
    public Date fechaMatricula;
    public String tipoMatricula;
    public String modalidad;
    public String documentos; // JSON string
}
