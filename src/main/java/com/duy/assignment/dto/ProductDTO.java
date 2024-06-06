package com.duy.assignment.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    String name;

    @NotBlank
    String description;

    double averageRating;

    @NotBlank
    int price;

    double screenSize;
    int ram;
    int internalStorage;

    @NotBlank
    Set<Integer> categoryIds;

    @NotBlank
    List<ColorDTO> colorDTOs;

    boolean featured;

    @NotBlank
    int brandId;
    
    String createdAt;
    String lastModified;
}
