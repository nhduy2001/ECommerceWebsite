package com.duy.assignment.rest;

import com.duy.assignment.dto.CategoryDTO;
import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.service.CategoryService;
import com.duy.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/categories")
    public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

}
