package com.duy.assignment.service.impl;

import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.UserMapper;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return userMapper.toDTOs(userRepository.findAll());
    }

    @Override
    public UserDTO findUserById(String uuid) {
        return userMapper.toDTO(userRepository.findById(uuid)
                .orElseThrow(()->
                        new RuntimeException("Did not find user with uuid - " + uuid)));

    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.findById(user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Did not find user with uuid - " + user.getUserId()));

        User existUser = user.toBuilder().build();

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
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findUserByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
