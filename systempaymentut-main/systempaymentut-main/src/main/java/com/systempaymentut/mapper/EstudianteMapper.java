package com.systempaymentut.mapper;

import com.systempaymentut.model.dto.EstudianteDTO;
import com.systempaymentut.model.entity.Estudiante;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EstudianteMapper {
    Estudiante toEntity(EstudianteDTO dto);
    EstudianteDTO toDTO(Estudiante entity);
    void updateEntity(EstudianteDTO dto, @MappingTarget Estudiante entity);
} 