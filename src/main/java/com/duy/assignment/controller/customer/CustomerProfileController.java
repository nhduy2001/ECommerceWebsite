package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customer/user")
public class CustomerProfileController {
    private final UserService userService;

    @Autowired
    public CustomerProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserByUserName(Principal principal) {
        return ResponseEntity.ok(userService.findUserByUsername(principal.getName()));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }
}
