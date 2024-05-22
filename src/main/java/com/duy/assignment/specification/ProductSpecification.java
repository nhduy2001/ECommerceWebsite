package com.duy.assignment.specification;

import com.duy.assignment.entity.Product;
import org.springframework.data.jpa.domain.Specification;

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
}
