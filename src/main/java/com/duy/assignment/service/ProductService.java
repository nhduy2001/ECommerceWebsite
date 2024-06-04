package com.duy.assignment.service;

import com.duy.assignment.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> findAll(int pageNum, Integer ram, String screenSize, Integer storage, String sortDir, String keyword, String name);

    ProductDTO findById(int id);

    List<ProductDTO> findByName(String name);

    ProductDTO add(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    void deleteById(int id);

    List<ProductDTO> displayAll();

    String uploadImage(MultipartFile file);

    List<ProductDTO> findFeaturedProducts();
}
