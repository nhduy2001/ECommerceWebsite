package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
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
    boolean featured;
    int brandId;
    String createdAt;
    String lastModified;
}
