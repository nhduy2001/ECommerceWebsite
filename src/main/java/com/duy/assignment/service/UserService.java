package com.duy.assignment.service;

import com.duy.assignment.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findUserById(String uuid);

    UserDTO findUserByUsername(String username);

    UserDTO update(UserDTO userDTO);

    void deleteById(String uuid);

    String findRole(String username);

    List<UserDTO> findAllUsersFromAdmin();
}
