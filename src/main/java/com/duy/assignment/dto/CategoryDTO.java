package com.duy.assignment.dto;

import com.duy.assignment.entity.AuditEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO extends AuditEntity {
    int categoryId;

    @NotBlank(message = "Category name is mandatory")
    String categoryName;
}
