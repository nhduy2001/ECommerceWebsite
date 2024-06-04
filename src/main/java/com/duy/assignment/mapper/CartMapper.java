package com.duy.assignment.mapper;

import com.duy.assignment.dto.CartDTO;
import com.duy.assignment.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    @Mapping(source = "user.userId", target = "userId")
    CartDTO toDTO(Cart cart);

    @Mapping(source = "userId", target = "user.userId")
    Cart toEntity(CartDTO cartDTO);
}
