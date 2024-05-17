package com.duy.assignment.rest;

import com.duy.assignment.dto.JWTToken;
import com.duy.assignment.dto.RefreshToken;
import com.duy.assignment.dto.SignInDTO;
import com.duy.assignment.dto.UserDTO;
import com.duy.assignment.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signUp")
    public UserDTO signUp(@RequestBody UserDTO userDTO) {
        return authenticationService.signUp(userDTO);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTToken> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTToken> refresh(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
