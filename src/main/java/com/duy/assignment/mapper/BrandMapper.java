package com.duy.assignment.mapper;

import com.duy.assignment.dto.BrandDTO;
import com.duy.assignment.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {
    BrandDTO toDTO(Brand brand);
    Brand toEntity(BrandDTO brandDTO);
    List<BrandDTO> toDTOs(List<Brand> brands);
}
