package com.duy.assignment.service;

import com.duy.assignment.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll(String keyword);

    ProductDTO findById(int id);

    ProductDTO add(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void deleteById(int id);
}
