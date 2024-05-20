package com.duy.assignment.mapper;

import com.duy.assignment.dto.CategoryDTO;
import com.duy.assignment.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDTO toDTO(Category entity);
    Category toEntity(CategoryDTO dto);
    List<CategoryDTO> toDTOs(List<Category> categories);
    List<Category> toEntities(List<CategoryDTO> categoryDTOS);


}
