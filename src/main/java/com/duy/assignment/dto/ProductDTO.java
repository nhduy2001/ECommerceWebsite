package com.duy.assignment.dto;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Product name is mandatory")
    String name;

    @NotBlank(message = "Product description is mandatory")
    String description;

    double averageRating;

    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cannot be 0")
    int price;

    @NotNull(message = "Screen Size is mandatory")
    @Min(value = 1, message = "Screen Size cannot be 0")
    double screenSize;

    @NotNull(message = "RAM is mandatory")
    @Min(value = 1, message = "RAM cannot be 0")
    int ram;

    @NotNull(message = "Storage is mandatory")
    @Min(value = 1, message = "Storage cannot be 0")
    int internalStorage;

    @NotEmpty(message = "Please choose category")
    Set<Integer> categoryIds;

    @NotEmpty(message = "Please add color")
    List<ColorDTO> colorDTOs;

    boolean featured;

    @NotNull(message = "Please choose brand")
    int brandId;

    String createdAt;
    String lastModified;
}
