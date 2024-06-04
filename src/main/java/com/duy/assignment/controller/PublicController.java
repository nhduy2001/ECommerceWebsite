package com.duy.assignment.controller;

import com.duy.assignment.dto.*;
import com.duy.assignment.service.AuthenticationService;
import com.duy.assignment.service.FeedbackService;
import com.duy.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final FeedbackService feedbackService;

    @Autowired
    public PublicController(AuthenticationService authenticationService,
                            ProductService productService,
                            FeedbackService feedbackService) {
        this.authenticationService = authenticationService;
        this.productService = productService;
        this.feedbackService = feedbackService;
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

    @GetMapping("/products/featured")
    public List<ProductDTO> getAllFeaturedProducts() {
        return productService.findFeaturedProducts();
    }

    @GetMapping("/products")
    public Page<ProductDTO> getAllProductsByFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "ram", required = false) Integer ram,
                                                   @RequestParam(value = "screen_size", required = false) String screenSize,
                                                   @RequestParam(value = "storage", required = false) Integer storage,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir,
                                                   @RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "name", required = false) String name) {
        return productService.findAll(page, ram, screenSize, storage, sortDir, keyword, name);
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProductsById(@PathVariable int id) {
        return productService.findById(id);
    }

    @GetMapping("/products/by/{name}")
    public List<ProductDTO> getProductsByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("/feedback/{id}")
    public List<FeedbackDTO> getAllFeedbacks(@PathVariable int id) {
        return feedbackService.getAllFeedbacks(id);
    }

}
