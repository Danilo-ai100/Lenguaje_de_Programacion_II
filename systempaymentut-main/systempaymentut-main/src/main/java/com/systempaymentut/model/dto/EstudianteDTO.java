package com.systempaymentut.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private Long id;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El número de documento debe contener entre 8 y 15 dígitos")
    private String numeroDocumento;

    @NotBlank(message = "El código institucional es obligatorio")
    @Pattern(regexp = "^[0-9]{8,10}$", message = "El código institucional debe contener entre 8 y 10 dígitos")
    private String codigoInstitucional;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String correoElectronico;

    @NotBlank(message = "El programa académico es obligatorio")
    private String programaAcademico;
} 