package com.duy.assignment.rest;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProductsByFilter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "ram", required = false) Integer ram,
                                                   @RequestParam(value = "screen_size", required = false) String screenSize,
                                                   @RequestParam(value = "storage", required = false) Integer storage,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir) {
        return productService.findAll(page, ram, screenSize, storage, sortDir);
    }

    @GetMapping("/search")
    public List<ProductDTO> getAllProductsBySearch(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(name = "sortDir", required = false) String sortDir) {
        return productService.findAllProductsBySearch(keyword, page, sortDir);
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.add(productDTO);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductsById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PutMapping
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
