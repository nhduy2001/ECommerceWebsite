package com.duy.assignment.repository;

import com.duy.assignment.entity.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {
    Optional<CartDetails> findByCart_CartIdAndAndProduct_ProductIdAndAndColorId(int cartId, int productId, int colorId);
    List<CartDetails> findAllByCart_CartId(int CartId);
    void deleteByIsCompleteTrue();

}
