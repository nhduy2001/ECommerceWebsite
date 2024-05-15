package com.duy.assignment.rest;

import com.duy.assignment.entity.User;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/users/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.update(username, user);
    }

    @DeleteMapping("/users/{username}")
    public String deleteUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new RuntimeException("Did not find user with username - " + username);
        }

        userService.deleteByUsername(username);
        return "Deleted";
    }
}
