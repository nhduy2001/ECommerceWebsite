package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    int productId;
    String name;
    String description;
    double averageRating;
    int price;
    double screenSize;
    int ram;
    int internalStorage;
    Set<Integer> categoryIds;
    List<ColorDTO> colorDTOs;
    int brandId;
}
