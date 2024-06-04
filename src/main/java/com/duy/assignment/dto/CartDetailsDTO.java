package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDetailsDTO {
    int cartDetailId;
    int quantity;
    boolean isComplete;
    int productId;
    int cartId;
    int colorId;
}
