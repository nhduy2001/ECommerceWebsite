package com.duy.assignment.service;

import com.duy.assignment.dto.CartDetailsDTO;

import java.util.List;

public interface CartDetailsService {
    CartDetailsDTO addToCart(CartDetailsDTO cartDetailsDTO, String username);
    List<CartDetailsDTO> getCart(String username);
    List<CartDetailsDTO> updateCart(List<CartDetailsDTO> cartDetailsDTOS);
}
