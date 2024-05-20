package com.duy.assignment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    int productId;
    String name;
    String description;
    String image;
    double averageRating;
    int price;
    String color;
    double screenSize;
    int ram;
    int internalStorage;
    Set<Integer> categoryIds;
    int brandId;
}
