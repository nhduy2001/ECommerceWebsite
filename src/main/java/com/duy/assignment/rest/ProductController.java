package com.duy.assignment.rest;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductDTO> getAllProductsByFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "ram", required = false) String ram,
                                                   @RequestParam(value = "screen_size", required = false) String screenSize,
                                                   @RequestParam(value = "storage", required = false) String storage) {
        return productService.findAll(page, ram, screenSize, storage);
    }

    @GetMapping("/search")
    public List<ProductDTO> getAllProductsBySearch(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "page", defaultValue = "1") int page) {
        return productService.findAllProductsBySearch(keyword, page);
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.add(productDTO);
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
