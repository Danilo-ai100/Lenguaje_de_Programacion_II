package SysColegio.model;


import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "col_grado")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    private Long idGrado;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre; // Ej: "3ro de Primaria", "5to de Secundaria"

    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel; // Ej: "Primaria", "Secundaria"
}
