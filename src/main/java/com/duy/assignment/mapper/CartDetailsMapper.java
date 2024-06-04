package com.duy.assignment.mapper;

import com.duy.assignment.dto.CartDetailsDTO;
import com.duy.assignment.entity.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartDetailsMapper {
    @Mapping(source = "cart.cartId", target = "cartId")
    @Mapping(source = "product.productId", target = "productId")
    CartDetailsDTO toDTO(CartDetails cartDetails);

    @Mapping(source = "cartId", target = "cart.cartId")
    @Mapping(source = "productId", target = "product.productId")
    CartDetails toEntity(CartDetailsDTO cartDetailsDTO);

    @Mapping(source = "cart.cartId", target = "cartId")
    @Mapping(source = "product.productId", target = "productId")
    List<CartDetailsDTO> toDTOs (List<CartDetails> cartDetails);
}
