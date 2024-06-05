package com.duy.assignment.controller.admin;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminAccountController {
    private final UserService userService;

    @Autowired
    public AdminAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUserFromAdmin() {
        return userService.findAllUsersFromAdmin();
    }

    @GetMapping("/{uuid}")
    public UserDTO getUserById(@PathVariable String uuid) {
        return userService.findUserById(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        userService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }
}
