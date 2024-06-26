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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImplement implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ColorMapper colorMapper;
    private static final String productNotFound = "Did not find product with id - ";
    private static final int defaultPageSize = 10;
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ProductServiceImplement(ProductRepository productRepository, ProductMapper productMapper, ColorMapper colorMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.colorMapper = colorMapper;
    }

    @Override
    public Page<ProductDTO> findAll(int pageNum, Integer ram, String screenSize, Integer storage, String sortDir, String keyword, String name) {
        Specification<Product> spec = Specification.where(null);
        Pageable pageable;
        if ("desc".equals(sortDir)) {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").descending());
        } else if (sortDir == null) {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize);
        } else {
            pageable = PageRequest.of(pageNum - 1, defaultPageSize, Sort.by("price").ascending());
        }

        spec = applyFilterConditions(spec, ram, screenSize, storage, keyword, name);

        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        return productsPage.map(productMapper::toDTO);
    }

    @Override
    public ProductDTO findById(int id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(productNotFound + id)));
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        List<Product> productList = productRepository.findProductsByName(name);
        return productMapper.toDTOs(productList);
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.getColors().clear();
        handleColors(productDTO, product);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {

        int featuredProductsCount = productRepository.countByFeaturedTrue();

        if (productDTO.isFeatured() && featuredProductsCount >= 5) {
            throw new RuntimeException("You can only have up to 5 featured products.");
        }

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

        for (int i = 0; i < product.getColors().size(); i++) {
            deleteImage(product.getColors().get(i).getColorImage());
        }

        // Product colors will be deleted due to orphanRemoval = true
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> displayAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = productMapper.toDTOs(productList);

        // New format for date
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

        for (int i = 0; i < productDTOList.size(); i++) {
            LocalDateTime createdAt = productList.get(i).getCreatedAt();
            LocalDateTime lastModified = productList.get(i).getLastModified();

            // Format createdAt and lastModified
            String formattedCreatedAt = createdAt.format(newFormatter);
            String formattedLastModified = lastModified.format(newFormatter);

            productDTOList.get(i).setCreatedAt(formattedCreatedAt);
            productDTOList.get(i).setLastModified(formattedLastModified);
        }
        return productDTOList;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String newFilename = UUID.randomUUID() + "_" + originalFilename;
            Path path = Paths.get(uploadPath, newFilename);
            Files.copy(file.getInputStream(), path);

            return newFilename;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed";
        }
    }

    @Override
    public List<ProductDTO> findFeaturedProducts() {
        return productMapper.toDTOs(productRepository.findAllByFeaturedTrue());
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

    private Specification<Product> applyFilterConditions(Specification<Product> spec, Integer ram, String screenSize, Integer storage, String keyword, String name) {
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
        if (keyword != null) {
            spec = spec.and(ProductSpecification.searchProducts(keyword));
        }
        if (name != null) {
            spec = spec.and(ProductSpecification.getExactProducts(name));
        }
        return spec;
    }

    private void deleteImage(String imageName) {
        File imageFile = new File(uploadPath+"/"+imageName);
        if (imageFile.exists()) {
            if (imageFile.delete()) {
                System.out.println("Delete Completely");
            } else {
                System.out.println("Cannot Delete");
            }
        } else {
            System.out.println("Image is not exist");
        }
    }
}
