package com.duy.assignment.service;

import com.duy.assignment.dto.JWTToken;
import com.duy.assignment.dto.RefreshToken;
import com.duy.assignment.dto.SignInDTO;
import com.duy.assignment.dto.UserDTO;

public interface AuthenticationService {
    UserDTO signUp(UserDTO userDTO);

    JWTToken signIn(SignInDTO signInDTO);

    JWTToken refreshToken(RefreshToken refreshToken);
}
