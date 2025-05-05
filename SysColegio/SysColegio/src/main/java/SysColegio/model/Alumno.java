package SysColegio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "col_alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;
    @Column(name= "apellido",nullable = false,length = 120)
    private String apellido;
    @Column(name = "fecha_nacimiento",nullable = false)
    private Date fecha_nacimiento;
    @Column(name= "dni",nullable = false,length = 20)
    private String dni;
    @Column(name= "direccion",nullable = false,length = 300)
    private String direccion;
    @Column(name= "telefono",nullable = false,length = 20)
    private String telefono;





}