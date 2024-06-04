package com.duy.assignment.dto;

import com.duy.assignment.entity.AuditEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO extends AuditEntity {
    int categoryId;
    String categoryName;
}
