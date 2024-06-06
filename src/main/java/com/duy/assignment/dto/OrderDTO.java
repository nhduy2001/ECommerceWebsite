package com.duy.assignment.dto;

import com.duy.assignment.entity.enumType.OrderPayType;
import com.duy.assignment.entity.enumType.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    int orderId;
    String userId;

    @NotBlank(message = "Full name is mandatory")
    @Size(min = 5, max = 80, message = "Full name must be between 5 and 80 characters")
    String fullName;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 11, message = "Phone must be 10 or 11 numbers")
    String phoneNumber;

    String email;

    @NotBlank(message = "Address is mandatory")
    String address;

    int totalPrice;
    OrderStatus status;
    OrderPayType payType;
    boolean isPay;
    List<OrderDetailsDTO> orderDetailsDTOs;
}
