package com.systempaymentut.model.dto;

import com.systempaymentut.model.enums.PagoStatus;
import com.systempaymentut.model.enums.TypePago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;

    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    private BigDecimal valor;

    @NotNull(message = "El tipo de pago es obligatorio")
    private TypePago tipoPago;

    private PagoStatus estado;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
} 