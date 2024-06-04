package com.duy.assignment.mapper;

import com.duy.assignment.dto.OrderDTO;
import com.duy.assignment.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(source = "user.userId", target = "userId")
    OrderDTO ToDTO(Order order);

    @Mapping(source = "userId", target = "user.userId")
    Order toEntity(OrderDTO orderDTO);

    @Mapping(source = "user.userId", target = "userId")
    List<OrderDTO> toDTOs(List<Order> orders);
}
