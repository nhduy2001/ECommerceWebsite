package com.duy.assignment.repository;

import com.duy.assignment.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p JOIN p.categories c"
            + " WHERE p.name LIKE %?1%"
            + " OR p.brand.brandName LIKE %?1%"
            + " OR c.categoryName LIKE %?1%")
    Page<Product> search(String keyword, Pageable pageable);
}
