package com.duy.assignment.service.impl;

import com.duy.assignment.dto.CartDTO;
import com.duy.assignment.entity.Cart;
import com.duy.assignment.mapper.CartMapper;
import com.duy.assignment.repository.CartRepository;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImplement implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImplement(CartRepository cartRepository, UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO createOrGetCart(String username) {
        Optional<Cart> existCart = cartRepository.findCartByUser_Username(username);
        if (existCart.isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(userRepository.findUserByUsername(username).orElse(null));
            cartRepository.save(cart);
            return cartMapper.toDTO(cart);
        }

        return cartMapper.toDTO(existCart.get());
    }
}
