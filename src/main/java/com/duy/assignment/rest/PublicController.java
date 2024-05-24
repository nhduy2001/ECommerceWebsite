package com.duy.assignment.rest;

import com.duy.assignment.dto.*;
import com.duy.assignment.service.AuthenticationService;
import com.duy.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private final AuthenticationService authenticationService;
    private final ProductService productService;

    @Autowired
    public PublicController(AuthenticationService authenticationService, ProductService productService) {
        this.authenticationService = authenticationService;
        this.productService = productService;
    }

    @PostMapping("/signUp")
    public UserDTO signUp(@RequestBody @Valid UserDTO userDTO) {
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

    @GetMapping("/products")
    public List<ProductDTO> getAllProductsByFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "ram", required = false) Integer ram,
                                                   @RequestParam(value = "screen_size", required = false) String screenSize,
                                                   @RequestParam(value = "storage", required = false) Integer storage,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir) {
        return productService.findAll(page, ram, screenSize, storage, sortDir);
    }

    @GetMapping("/search")
    public List<ProductDTO> getAllProductsBySearch(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir) {
        return productService.findAllProductsBySearch(keyword, page, sortDir);
    }
}
