package com.duy.assignment.service.impl;

import com.duy.assignment.dto.BrandDTO;
import com.duy.assignment.mapper.BrandMapper;
import com.duy.assignment.repository.BrandRepository;
import com.duy.assignment.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImplement implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandServiceImplement(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandDTO> findAllBrands() {
        return brandMapper.toDTOs(brandRepository.findAll());
    }
}
