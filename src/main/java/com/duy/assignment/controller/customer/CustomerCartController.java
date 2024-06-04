package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.CartDetailsDTO;
import com.duy.assignment.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CartDetailsDTO addToCart(@RequestBody CartDetailsDTO cartDetailsDTO, Principal principal) {
        return cartDetailsService.addToCart(cartDetailsDTO, principal.getName());
    }

    @GetMapping
    public List<CartDetailsDTO> getCart(Principal principal) {
        return cartDetailsService.getCart(principal.getName());
    }

    @PutMapping
    public List<CartDetailsDTO> updateCart(@RequestBody List<CartDetailsDTO>  cartDetailsDTOS) {
        return cartDetailsService.updateCart(cartDetailsDTOS);
    }
}
