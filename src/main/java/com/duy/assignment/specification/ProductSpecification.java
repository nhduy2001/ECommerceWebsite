package com.duy.assignment.specification;

import com.duy.assignment.entity.Category;
import com.duy.assignment.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> screenSizeGreaterThan(double value) {
        return (root, query, builder) -> builder.greaterThan(root.get("screenSize"), value);
    }

    public static Specification<Product> screenSizeLessThan(double value) {
        return (root, query, builder) -> builder.lessThan(root.get("screenSize"), value);
    }

    public static Specification<Product> ramEquals(int value) {
        return (root, query, builder) -> builder.equal(root.get("ram"), value);
    }

    public static Specification<Product> storageEquals(int value) {
        return (root, query, builder) -> builder.equal(root.get("internalStorage"), value);
    }

    public static Specification<Product> searchProducts(String keyword) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                String likeKeyword = "%" + keyword + "%";

                // Tìm kiếm theo tên sản phẩm
                predicates.add(builder.like(root.get("name"), likeKeyword));

                // Join với bảng categories và tìm kiếm theo tên category
                Join<Product, Category> categoryJoin = root.join("categories", JoinType.LEFT);
                predicates.add(builder.like(categoryJoin.get("categoryName"), likeKeyword));

                // (Optional) Tìm kiếm theo tên brand
                predicates.add(builder.like(root.get("brand").get("brandName"), likeKeyword));
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
