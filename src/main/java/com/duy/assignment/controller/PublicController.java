package com.duy.assignment.controller;

import com.duy.assignment.dto.*;
import com.duy.assignment.service.AuthenticationService;
import com.duy.assignment.service.FeedbackService;
import com.duy.assignment.service.ProductService;
import com.duy.assignment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final FeedbackService feedbackService;
    private final UserService userService;

    @Autowired
    public PublicController(AuthenticationService authenticationService,
                            ProductService productService,
                            FeedbackService feedbackService,
                            UserService userService) {
        this.authenticationService = authenticationService;
        this.productService = productService;
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(authenticationService.signUp(userDTO));
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTToken> refresh(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @GetMapping("/products/featured")
    public ResponseEntity<?> getAllFeaturedProducts() {
        return ResponseEntity.ok(productService.findFeaturedProducts());
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProductsByFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "ram", required = false) Integer ram,
                                                   @RequestParam(value = "screen_size", required = false) String screenSize,
                                                   @RequestParam(value = "storage", required = false) Integer storage,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir,
                                                   @RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "name", required = false) String name) {
        return ResponseEntity.ok(productService.findAll(page, ram, screenSize, storage, sortDir, keyword, name));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable int id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/products/by/{name}")
    public ResponseEntity<?> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getAllFeedbacks(@PathVariable int id) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks(id));
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findRole(username));
    }

}
