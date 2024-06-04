package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDTO {
    private int feedbackId;
    private double rating;
    private String comment;
    private String username;
    private int productId;
}
