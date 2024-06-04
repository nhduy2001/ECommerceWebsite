package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public UserDTO getUserByUserName(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }
}
