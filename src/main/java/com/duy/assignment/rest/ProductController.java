package com.duy.assignment.rest;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(@Param("keyword") String keyword) {
        return productService.findAll(keyword);
    }

    @GetMapping("/products/{id}")
    public ProductDTO getAllProducts(@PathVariable int id) {
        return productService.findById(id);
    }

    @PutMapping("/products")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }
}
