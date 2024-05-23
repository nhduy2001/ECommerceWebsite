package com.duy.assignment.mapper;

import com.duy.assignment.dto.ColorDTO;
import com.duy.assignment.entity.Color;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    ColorDTO toDTO(Color entity);

    Color toEntity(ColorDTO dto);

    List<ColorDTO> toDTOs(List<Color> entities);

    List<Color> toEntities(List<ColorDTO> dtos);

    void updateColorFromDTO(ColorDTO dto, @MappingTarget Color entity);
}
