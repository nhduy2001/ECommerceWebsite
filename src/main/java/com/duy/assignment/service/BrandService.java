package com.duy.assignment.service;

import com.duy.assignment.dto.BrandDTO;
import com.duy.assignment.entity.Brand;

import java.util.List;

public interface BrandService {
    List<BrandDTO> findAllBrands();
}
