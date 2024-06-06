package com.duy.assignment.controller.admin;

import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminAccountController {
    private final UserService userService;

    @Autowired
    public AdminAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUserFromAdmin() {
        return ResponseEntity.ok(userService.findAllUsersFromAdmin());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUserById(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.findUserById(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        userService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }
}
