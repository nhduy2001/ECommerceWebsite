package com.duy.assignment.dto;

import com.duy.assignment.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorDTO {
    int colorId;

    String colorName;

    String colorImage;
}
