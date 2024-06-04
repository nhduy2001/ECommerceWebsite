package com.duy.assignment.service.impl;

import com.duy.assignment.dto.CartDetailsDTO;
import com.duy.assignment.entity.Cart;
import com.duy.assignment.entity.CartDetails;
import com.duy.assignment.entity.User;
import com.duy.assignment.mapper.CartDetailsMapper;
import com.duy.assignment.repository.CartDetailsRepository;
import com.duy.assignment.repository.CartRepository;
import com.duy.assignment.repository.ProductRepository;
import com.duy.assignment.repository.UserRepository;
import com.duy.assignment.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailsServiceImplement implements CartDetailsService {
    private final CartDetailsRepository cartDetailsRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartDetailsMapper cartDetailsMapper;
    private final UserRepository userRepository;

    @Autowired
    public CartDetailsServiceImplement(CartDetailsRepository cartDetailsRepository,
                                       ProductRepository productRepository,
                                       CartDetailsMapper cartDetailsMapper,
                                       CartRepository cartRepository,
                                       UserRepository userRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
        this.productRepository = productRepository;
        this.cartDetailsMapper = cartDetailsMapper;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartDetailsDTO addToCart(CartDetailsDTO cartDetailsDTO, String username) {
        int colorId = 0;

        if (cartDetailsDTO.getColorId() == 0){
            colorId = productRepository.findById(cartDetailsDTO.getProductId()).get().getColors().get(0).getColorId();
        } else {
            colorId = cartDetailsDTO.getColorId();
        }

        Optional<Cart> existCart = cartRepository.findCartByUser_Username(username);
        Optional<User> existUser = userRepository.findUserByUsername(username);
        Cart cart;

        if (existCart.isEmpty() && existUser.isPresent()) {
            cart = new Cart();
            cart.setUser(existUser.get());
            cartRepository.save(cart);
        } else {
            cart = existCart.orElseThrow(() -> new RuntimeException("User does not have a cart"));
        }

        Optional<CartDetails> existCartDetail = cartDetailsRepository.findByCart_CartIdAndAndProduct_ProductIdAndAndColorId(cart.getCartId(), cartDetailsDTO.getProductId(), colorId);

        if (existCartDetail.isPresent()) {
            CartDetails cartDetails = existCartDetail.get();
            cartDetails.setQuantity(cartDetails.getQuantity() + 1);
            cartDetailsRepository.save(cartDetails);
            return cartDetailsMapper.toDTO(cartDetails);
        }

        CartDetails newCartDetails = new CartDetails();
        newCartDetails.setQuantity(cartDetailsDTO.getQuantity());
        newCartDetails.setProduct(productRepository.findById(cartDetailsDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found")));
        newCartDetails.setCart(cart);
        newCartDetails.setColorId(colorId);
        newCartDetails.setComplete(false);
        cartDetailsRepository.save(newCartDetails);
        return cartDetailsMapper.toDTO(newCartDetails);
    }

    @Override
    public List<CartDetailsDTO> getCart(String username) {
        Optional<Cart> existCart = cartRepository.findCartByUser_Username(username);
        if (existCart.isEmpty()) {
            return List.of();
        } else {
            List<CartDetails> existCartDetail = cartDetailsRepository.findAllByCart_CartId(existCart.get().getCartId());
            return cartDetailsMapper.toDTOs(existCartDetail);
        }
    }

    @Override
    public List<CartDetailsDTO> updateCart(List<CartDetailsDTO> cartDetailsDTOS) {
        List<CartDetailsDTO> updatedCartDetails = new ArrayList<>();

        for (CartDetailsDTO cartDetailDTO : cartDetailsDTOS) {
            Optional<CartDetails> existingCartDetails = cartDetailsRepository.findById(cartDetailDTO.getCartDetailId());

            if (existingCartDetails.isPresent()) {
                CartDetails cartDetails = existingCartDetails.get();
                cartDetails.setQuantity(cartDetailDTO.getQuantity());
                cartDetailsRepository.save(cartDetails);
                updatedCartDetails.add(cartDetailDTO);
            }
        }
        return updatedCartDetails;
    }
}
