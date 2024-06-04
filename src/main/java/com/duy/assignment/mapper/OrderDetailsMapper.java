package com.duy.assignment.mapper;

import com.duy.assignment.dto.OrderDetailsDTO;
import com.duy.assignment.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailsMapper {
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "order.orderId", target = "orderId")
    OrderDetailsDTO toDTO(OrderDetails orderDetails);

    @Mapping(source = "productId", target = "product.productId")
    @Mapping(source = "orderId", target = "order.orderId")
    OrderDetails toEntity(OrderDetailsDTO orderDetailsDTO);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "order.orderId", target = "orderId")
    List<OrderDetailsDTO> toDTOs(List<OrderDetails> orderDetails);
}
