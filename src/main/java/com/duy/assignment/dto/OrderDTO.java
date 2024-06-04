package com.duy.assignment.dto;

import com.duy.assignment.entity.enumType.OrderPayType;
import com.duy.assignment.entity.enumType.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    int orderId;
    String userId;
    String fullName;
    String phoneNumber;
    String email;
    String address;
    int totalPrice;
    OrderStatus status;
    OrderPayType payType;
    boolean isPay;
}
