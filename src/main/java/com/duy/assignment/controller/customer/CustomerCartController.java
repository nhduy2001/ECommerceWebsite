package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.CartDetailsDTO;
import com.duy.assignment.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/cart")
public class CustomerCartController {
    private final CartDetailsService cartDetailsService;

    @Autowired
    public CustomerCartController(CartDetailsService cartDetailsService) {
        this.cartDetailsService = cartDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartDetailsDTO cartDetailsDTO, Principal principal) {
        return ResponseEntity.ok(cartDetailsService.addToCart(cartDetailsDTO, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        return ResponseEntity.ok(cartDetailsService.getCart(principal.getName()));
    }

    @PutMapping
    public ResponseEntity<?> updateCart(@RequestBody List<CartDetailsDTO>  cartDetailsDTOS) {
        return ResponseEntity.ok(cartDetailsService.updateCart(cartDetailsDTOS));
    }
}
