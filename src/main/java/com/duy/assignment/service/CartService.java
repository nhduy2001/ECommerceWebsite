package com.duy.assignment.service;

import com.duy.assignment.dto.CartDTO;

public interface CartService {
    CartDTO createOrGetCart(String username);
}
