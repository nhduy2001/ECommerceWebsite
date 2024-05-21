package com.duy.assignment.service.impl;

import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.ProductMapper;
import com.duy.assignment.repository.ProductRepository;
import com.duy.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImplement(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public List<ProductDTO> findAll(String keyword) {
        int pageSize = 10;

        Pageable pageable = PageRequest.of(1 - 1, pageSize);

        if (keyword != null) {
            return productMapper.toDTOs(productRepository.search(keyword));
        }

        return productMapper.toDTOs(productRepository.findAll());
    }

    @Override
    public ProductDTO findById(int id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Did not find product with id - " + id)));
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
                        new RuntimeException("Did not find product with id - " + productDTO.getProductId()));
        Product existProduct = product.toBuilder().build();
        productRepository.save(existProduct);
        return productMapper.toDTO(existProduct);
    }

    @Override
    public void deleteById(int id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new RuntimeException("Did not find product with id - " + id);
        }
        productRepository.deleteById(id);
    }
}
