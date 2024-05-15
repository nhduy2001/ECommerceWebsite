package com.duy.assignment.service;

import com.duy.assignment.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserByUsername(String username);

    User save(User user);

    User update(String username, User user);

    void  deleteByUsername(String username);
}
