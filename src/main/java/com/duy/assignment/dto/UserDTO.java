package com.duy.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String userId;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 25, message = "username must be between 3 and 25 characters")
    String username;

    @NotBlank(message = "Password is mandatory")
    String password;

    @NotBlank(message = "Full name is mandatory")
    @Size(min = 5, max = 80, message = "Full name must be between 5 and 80 characters")
    String fullName;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 11)
    String phoneNumber;

    String email;

    @NotBlank(message = "Address is mandatory")
    String address;

    String role;
    boolean confirmed;
    String createdAt;
    String lastModified;
}
