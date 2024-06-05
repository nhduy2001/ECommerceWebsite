package com.duy.assignment.service.impl;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.UserMapper;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationContext applicationContext;

    @Autowired
    public UserServiceImplement(UserRepository userRepository,
                                UserMapper userMapper,
                                ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.applicationContext = applicationContext;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public UserDTO findUserById(String uuid) {
        return userMapper.toDTO(userRepository.findById(uuid)
                .orElseThrow(()->
                        new RuntimeException("Did not find user with uuid - " + uuid)));

    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return userMapper.toDTO(userRepository.findUserByUsername(username)
                .orElseThrow(()->
                        new RuntimeException("Did not find user with username - " + username)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.findById(user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Did not find user with uuid - " + user.getUserId()));

        User existUser = user.toBuilder().build();
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        existUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(existUser);
        return userMapper.toDTO(existUser);
    }


    @Override
    public void deleteById(String uuid) {
        Optional<User> user = userRepository.findById(uuid);

        if (user.isEmpty()) {
            throw new RuntimeException("Did not find user with uuid - " + uuid);
        }
        userRepository.deleteById(uuid);
    }

    @Override
    public String findRole(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow();
        return user.getRole();
    }

    @Override
    public List<UserDTO> findAllUsersFromAdmin() {
        List<User> userList = userRepository.findAllByRole("user");
        List<UserDTO> userDTOS = userMapper.toDTOs(userList);

        // New format for date
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

        for (int i = 0; i < userDTOS.size(); i++) {
            LocalDateTime createdAt = userList.get(i).getCreatedAt();
            LocalDateTime lastModified = userList.get(i).getLastModified();

            // Format createdAt and lastModified
            String formattedCreatedAt = createdAt.format(newFormatter);
            String formattedLastModified = lastModified.format(newFormatter);

            userDTOS.get(i).setCreatedAt(formattedCreatedAt);
            userDTOS.get(i).setLastModified(formattedLastModified);
        }
        return userDTOS;
    }

}
