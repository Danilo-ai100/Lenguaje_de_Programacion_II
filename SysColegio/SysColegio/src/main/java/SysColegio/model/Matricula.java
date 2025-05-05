package SysColegio.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "col_matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name = "id_grado", nullable = false)
    private Grado grado;

    @Column(name = "fecha_matricula", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMatricula;
}
