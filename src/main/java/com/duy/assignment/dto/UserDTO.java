package com.duy.assignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String userId;
    String username;
    String password;
    String fullName;
    String phoneNumber;
    String email;
    String role;
    boolean confirmed;
}
