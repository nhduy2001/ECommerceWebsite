package com.duy.assignment.repository;

import com.duy.assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
            + " OR p.brand.brandName LIKE %?1%")
    List<Product> search(String keyword);
}
