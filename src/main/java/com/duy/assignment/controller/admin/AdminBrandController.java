package com.duy.assignment.controller.admin;

import com.duy.assignment.dto.BrandDTO;
import com.duy.assignment.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/brands")
public class AdminBrandController {
    private final BrandService brandService;

    @Autowired
    public AdminBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(brandService.findAllBrands());
    }
}
