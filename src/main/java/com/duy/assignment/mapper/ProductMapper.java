package com.duy.assignment.mapper;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {BrandIdMapper.class, CategoryIdMapper.class, ColorMapper.class})
public interface ProductMapper {
    @Mapping(source = "brand.brandId", target = "brandId")
    @Mapping(source = "categories", target = "categoryIds")
    @Mapping(source = "colors", target = "colorDTOs")
    ProductDTO toDTO(Product entity);

    @Mapping(target = "brand", source = "brandId")
    @Mapping(source = "categoryIds", target = "categories")
    @Mapping(source = "colorDTOs", target = "colors")
    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDTOs(List<Product> products);

    List<Product> toEntities(List<ProductDTO> productDTOS);

    @Mapping(target = "productId", ignore = true)
    void updateProductFromDTO(ProductDTO dto, @MappingTarget Product entity);
}
