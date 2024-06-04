package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDTO {
    private int cartId;
    private int userId;
    private List<CartDetailsDTO> cartDetails;
}
