package com.duy.assignment.service.impl;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.ProductMapper;
import com.duy.assignment.repository.ProductRepository;
import com.duy.assignment.service.ProductService;
import com.duy.assignment.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String productNotFound = "Did not find product with id - ";
    private static final int defaultPageSize = 10;

    @Autowired
    public ProductServiceImplement(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public List<ProductDTO> findAll(int pageNum, String ram, String screenSize, String storage) {
        Specification<Product> spec = Specification.where(null);

        Pageable pageable = PageRequest.of(pageNum - 1, defaultPageSize);

        spec = applyFilterConditions(spec, ram, screenSize, storage);

        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        return productMapper.toDTOs(productsPage.getContent());
    }

    @Override
    public List<ProductDTO> findAllProductsBySearch(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, defaultPageSize);

        Page<Product> productsPage;

        if (keyword != null && !keyword.isEmpty()) {
            productsPage = productRepository.search(keyword, pageable);
        } else {
            productsPage = productRepository.findAll(pageable);
        }

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
        Product product = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        productRepository.findById(productDTO.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(productNotFound + productDTO.getProductId()));
        Product existProduct = product.toBuilder().build();
        productRepository.save(existProduct);
        return productMapper.toDTO(existProduct);
    }

    @Override
    public void deleteById(int id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new RuntimeException(productNotFound + id);
        }
        productRepository.deleteById(id);
    }

    private double convertInchesType(String screenSize) {
        if (screenSize.equals("tren-6.5-inch")) {
            return 6.6;
        }
        return 6.5;
    }

    private int convertRamType(String ram) {
        int ramConverted = 0;
        switch (ram) {
            case "4":
                ramConverted = 4;
                break;
            case "6":
                ramConverted = 6;
                break;

            case "8":
                ramConverted = 8;
                break;

            case "12":
                ramConverted = 12;
                break;
        }
        return ramConverted;
    }

    private int convertStorageType(String storage) {
        int storageConverted = 0;
        switch (storage) {
            case "64":
                storageConverted = 64;
                break;
            case "128":
                storageConverted = 128;
                break;

            case "256":
                storageConverted = 256;
                break;

            default:
                storageConverted = 512;
                break;
        }
        return storageConverted;
    }

    private Specification<Product> applyFilterConditions(Specification<Product> spec, String ram, String screenSize, String storage) {
        if (ram != null && !ram.isEmpty()) {
            spec = spec.and(ProductSpecification.ramEquals(convertRamType(ram)));
        }
        if (screenSize != null && !screenSize.isEmpty()) {
            double screenSizeValue = convertInchesType(screenSize);
            if (screenSizeValue > 6.5) {
                spec = spec.and(ProductSpecification.screenSizeGreaterThan(6.4));
            } else {
                spec = spec.and(ProductSpecification.screenSizeLessThan(6.6));
            }
        }
        if (storage != null && !storage.isEmpty()) {
            spec = spec.and(ProductSpecification.storageEquals(convertStorageType(storage)));
        }
        return spec;
    }
}
