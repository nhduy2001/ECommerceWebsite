package com.duy.assignment.service;

import com.duy.assignment.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();

    UserDTO findUserById(String uuid);

    UserDTO update(UserDTO userDTO);

    void deleteById(String uuid);

    UserDetailsService userDetailsService();
}
