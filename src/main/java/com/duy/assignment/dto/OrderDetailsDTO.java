package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsDTO {
    int orderDetailId;
    int productId;
    int orderId;
    int colorId;
    int quantity;
}
