package com.duy.assignment.repository;

import com.duy.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    List<User> findAllByRole(String role);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
}
