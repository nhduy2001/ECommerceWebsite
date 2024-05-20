package com.duy.assignment.mapper;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {BrandMapper.class, CategoryIdMapper.class})
public interface ProductMapper {
    @Mapping(source = "brand.brandId", target = "brandId")
    @Mapping(source = "categories", target = "categoryIds")
    ProductDTO toDTO(Product entity);

    @Mapping(target = "brand", source = "brandId")
    @Mapping(source = "categoryIds", target = "categories")
    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDTOs(List<Product> products);

    List<Product> toEntities(List<ProductDTO> productDTOS);
}
