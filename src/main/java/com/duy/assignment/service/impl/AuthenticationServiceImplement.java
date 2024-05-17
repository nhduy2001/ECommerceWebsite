package com.duy.assignment.service.impl;

import com.duy.assignment.dto.JWTToken;
import com.duy.assignment.dto.RefreshToken;
import com.duy.assignment.dto.SignInDTO;
import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.UserMapper;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.AuthenticationService;
import com.duy.assignment.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplement implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public UserDTO signUp(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public JWTToken signIn(SignInDTO signInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.username(), signInDTO.password()));
        var user = userRepository.findUserByUsername(signInDTO.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTToken jwtToken = new JWTToken();
        jwtToken.setToken(jwt);
        jwtToken.setRefreshToken(refreshToken);
        return jwtToken;
    }

    @Override
    public JWTToken refreshToken(RefreshToken refreshToken) {
        String username = jwtService.exactUserName(refreshToken.getToken());
        User user = userRepository.findUserByUsername(username).orElseThrow();
        if (jwtService.isTokenValid(refreshToken.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JWTToken jwtToken = new JWTToken();
            jwtToken.setToken(jwt);
            jwtToken.setRefreshToken(refreshToken.getToken());
            return jwtToken;
        }
        return null;
    }


}
