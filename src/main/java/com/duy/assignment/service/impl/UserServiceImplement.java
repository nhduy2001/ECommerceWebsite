package com.duy.assignment.service.impl;

import com.duy.assignment.entity.User;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> existUser = userRepository.findUserByUsername(username);
        User user;
        if (existUser.isPresent()) {
            user = existUser.get();
        } else {
            throw new RuntimeException("Did not find user with username - " + username);
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(String username, User user) {
        Optional<User> existUser = userRepository.findUserByUsername(username);

        if (existUser.isEmpty()) {
            throw new RuntimeException("Did not find user with username - " + username);
        }

        existUser.get().setUsername(user.getUsername());
        existUser.get().setEmail(user.getEmail());
        existUser.get().setPassword(user.getPassword());
        existUser.get().setFullName(user.getFullName());
        existUser.get().setPhoneNumber(user.getPhoneNumber());
        existUser.get().setRole(user.getRole());
        existUser.get().setEnabled(user.isEnabled());
        return userRepository.save(existUser.get());
    }


    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

}
