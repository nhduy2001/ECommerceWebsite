package com.duy.assignment.rest;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{uuid}")
    public UserDTO getUserByUuid(@PathVariable String uuid) {
        return userService.findUserById(uuid);
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping("/users/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        userService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }
}
