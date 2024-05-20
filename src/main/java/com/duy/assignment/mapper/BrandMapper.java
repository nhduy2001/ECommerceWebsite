package com.duy.assignment.mapper;

import com.duy.assignment.entity.Brand;
import com.duy.assignment.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandMapper(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand map(int brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand ID: " + brandId));
    }

    public int map(Brand brand) {
        return brand.getBrandId();
    }
}
