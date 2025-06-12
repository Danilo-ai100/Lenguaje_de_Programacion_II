package com.systempaymentut.mapper;

import com.systempaymentut.model.dto.PagoDTO;
import com.systempaymentut.model.entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PagoMapper {
    @Mapping(target = "estudiante", ignore = true)
    Pago toEntity(PagoDTO dto);
    
    @Mapping(target = "estudianteId", source = "estudiante.id")
    PagoDTO toDTO(Pago entity);
    
    void updateEntity(PagoDTO dto, @MappingTarget Pago entity);
} 