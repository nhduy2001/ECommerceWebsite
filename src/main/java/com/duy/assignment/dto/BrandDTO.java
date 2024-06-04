package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandDTO {
    int brandId;
    String brandName;
    String brandImage;
}
