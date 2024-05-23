package com.duy.assignment.service;

import com.duy.assignment.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll(int pageNum, Integer ram, String screenSize, Integer storage, String sortDir);

    List<ProductDTO> findAllProductsBySearch(String keyword, int pageNum, String sortDir);

    ProductDTO findById(int id);

    ProductDTO add(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void deleteById(int id);
}
