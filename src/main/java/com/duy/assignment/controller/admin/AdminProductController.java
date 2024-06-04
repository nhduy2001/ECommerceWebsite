package com.duy.assignment.controller.admin;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {
    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts () {
        return productService.displayAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductsById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.add(productDTO);
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(productService.uploadImage(file));
    }
}
