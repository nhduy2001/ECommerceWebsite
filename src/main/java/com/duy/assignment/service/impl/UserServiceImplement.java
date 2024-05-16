package com.duy.assignment.service.impl;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.UserMapper;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImplement(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }

    @Override
    public UserDTO findUserById(String uuid) {
        Optional<User> existUser = userRepository.findById(uuid);
        if (existUser.isEmpty()) {
            throw new RuntimeException("Did not find user with username - " + uuid);
        }
        return userMapper.toDTO(existUser.get());
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        Optional<User> existUser = userRepository.findById(user.getUserId());

        if (existUser.isEmpty()) {
            throw new RuntimeException("Did not find user with username - " + user.getUserId());
        }

        User updatedUser = existUser.get().toBuilder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
//        existUser.get().setUsername(user.getUsername());
//        existUser.get().setEmail(user.getEmail());
//        existUser.get().setPassword(user.getPassword());
//        existUser.get().setFullName(user.getFullName());
//        existUser.get().setPhoneNumber(user.getPhoneNumber());
//        existUser.get().setRole(user.getRole());
//        existUser.get().setEnabled(user.isEnabled());
        userRepository.save(updatedUser);
        return userMapper.toDTO(updatedUser);
    }


    @Override
    public void deleteById(String uuid) {
        Optional<User> user = userRepository.findById(uuid);

        if (user.isEmpty()) {
            throw new RuntimeException("Did not find user with username - " + uuid);
        }
        userRepository.deleteById(uuid);
    }

}
