package com.duy.assignment.service.impl;

import com.duy.assignment.dto.ColorDTO;
import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Color;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.ColorMapper;
import com.duy.assignment.mapper.ProductMapper;
import com.duy.assignment.repository.ProductRepository;
import com.duy.assignment.service.ProductService;
import com.duy.assignment.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductServiceImplement implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ColorMapper colorMapper;
    private static final String productNotFound = "Did not find product with id - ";
    private static final int defaultPageSize = 10;

    @Autowired
    public ProductServiceImplement(ProductRepository productRepository, ProductMapper productMapper, ColorMapper colorMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.colorMapper = colorMapper;
    }

    @Override
    public List<ProductDTO> findAll(int pageNum, Integer ram, String screenSize, Integer storage, String sortDir) {
        Specification<Product> spec = Specification.where(null);
        Pageable pageable;
        if ("desc".equals(sortDir)) {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").descending());
        } else if (sortDir == null) {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize);
        } else {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").ascending());
        }

        spec = applyFilterConditions(spec, ram, screenSize, storage);

        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        return productMapper.toDTOs(productsPage.getContent());
    }

    @Override
    public List<ProductDTO> findAllProductsBySearch(String keyword, int pageNum, String sortDir) {
        Pageable pageable;
        Specification<Product> spec = ProductSpecification.searchProducts(keyword);

        if (sortDir != null && sortDir.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").ascending());
        }

        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        return productMapper.toDTOs(productsPage.getContent());
    }

    @Override
    public ProductDTO findById(int id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(productNotFound + id)));
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        handleColors(productDTO, product);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product existProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + productDTO.getProductId()));

        // Update basic fields
        productMapper.updateProductFromDTO(productDTO, existProduct);

        // Update colors
        existProduct.getColors().clear();
        handleColors(productDTO, existProduct);

        Product updatedProduct = productRepository.save(existProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteById(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Clear relationships with categories
        product.getCategories().forEach(category -> category.getProducts().remove(product));
        product.getCategories().clear();

        // Product colors will be deleted due to orphanRemoval = true
        productRepository.delete(product);
    }

    private void handleColors(ProductDTO productDTO, Product product) {
        if (productDTO.getColorDTOs() != null && !productDTO.getColorDTOs().isEmpty()) {
            for (ColorDTO colorDTO : productDTO.getColorDTOs()) {
                Color color = colorMapper.toEntity(colorDTO);
                color.setProduct(product);
                product.getColors().add(color);
            }
        }
    }

    private Specification<Product> applyFilterConditions(Specification<Product> spec, Integer ram, String screenSize, Integer storage) {
        if (ram != null) {
            spec = spec.and(ProductSpecification.ramEquals(ram));
        }
        if (screenSize != null && !screenSize.isEmpty()) {
            if (screenSize.equals("tren-6.5-inch")) {
                spec = spec.and(ProductSpecification.screenSizeGreaterThan(6.55));
            } else {
                spec = spec.and(ProductSpecification.screenSizeLessThan(6.55));
            }
        }
        if (storage != null) {
            spec = spec.and(ProductSpecification.storageEquals(storage));
        }
        return spec;
    }
}
