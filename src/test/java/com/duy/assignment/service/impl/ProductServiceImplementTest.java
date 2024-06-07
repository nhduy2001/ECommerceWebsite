package com.duy.assignment.service.impl;

import com.duy.assignment.dto.ColorDTO;
import com.duy.assignment.dto.ProductDTO;
import com.duy.assignment.entity.Category;
import com.duy.assignment.entity.Color;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.ProductMapper;
import com.duy.assignment.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImplement productService;

    @Test
    void findAll_shouldReturnPagedProductDTOs_whenProductsExist() {
        // Given
        int pageNum = 1;
        Integer ram = null;
        String screenSize = null;
        Integer storage = null;
        String sortDir = "asc";
        String keyword = "phone";
        String name = "Samsung";

        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Page<Product> productsPage = new PageImpl<>(productList);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productsPage);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        // When
        Page<ProductDTO> result = productService.findAll(pageNum, ram, screenSize, storage, sortDir, keyword, name);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productDTO, result.getContent().get(0));

        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(productMapper).toDTO(any(Product.class));
    }

    @Test
    void findAll_shouldReturnPagedProductDTOs_whenSortDirIsDesc() {
        // Given
        int pageNum = 1;
        Integer ram = null;
        String screenSize = null;
        Integer storage = null;
        String sortDir = "desc";
        String keyword = null;
        String name = null;

        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Page<Product> productsPage = new PageImpl<>(productList);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productsPage);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        // When
        Page<ProductDTO> result = productService.findAll(pageNum, ram, screenSize, storage, sortDir, keyword, name);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productDTO, result.getContent().get(0));

        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(productMapper).toDTO(any(Product.class));
    }

    @Test
    void findAll_shouldReturnPagedProductDTOs_whenSortDirIsNull() {
        // Given
        int pageNum = 1;
        Integer ram = null;
        String screenSize = null;
        Integer storage = null;
        String sortDir = null;
        String keyword = null;
        String name = null;

        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Page<Product> productsPage = new PageImpl<>(productList);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(productsPage);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        // When
        Page<ProductDTO> result = productService.findAll(pageNum, ram, screenSize, storage, sortDir, keyword, name);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productDTO, result.getContent().get(0));

        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(productMapper).toDTO(any(Product.class));
    }

    @Test
    void findById_shouldReturnProductDTO_whenProductExists() {
        // Given
        int productId = 1;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.findById(productId);

        // Then
        assertNotNull(result);
        assertEquals(productDTO, result);

        verify(productRepository).findById(productId);
        verify(productMapper).toDTO(product);
    }

    @Test
    void findById_shouldThrowException_whenProductDoesNotExist() {
        // Given
        int productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.findById(productId);
        });

        assertEquals("Did not find product with id - " + productId, exception.getMessage());

        verify(productRepository).findById(productId);
        verify(productMapper, never()).toDTO(any());
    }

    @Test
    void findByName() {
        //Given
        String name = "Duy";
        List<Product> productList = new ArrayList<>();
        when(productRepository.findProductsByName(name)).thenReturn(productList);

        List<ProductDTO> productDTOList = new ArrayList<>();
        when(productMapper.toDTOs(productList)).thenReturn(productDTOList);

        //When
        List<ProductDTO> result = productService.findByName(name);

        //Then
        assertSame(result, productDTOList);
    }

    @Test
    void add() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        Product product = mock(Product.class);
        Product savedProduct = new Product();
        ProductDTO savedProductDTO = new ProductDTO();

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(product.getColors()).thenReturn(new ArrayList<>());
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(savedProductDTO);

        // When
        ProductDTO result = productService.add(productDTO);

        // Then
        assertNotNull(result);
        assertEquals(savedProductDTO, result);

        verify(productMapper).toEntity(productDTO);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(savedProduct);
    }

    @Test
    void update_shouldReturnUpdatedProductDTO_whenProductExistsAndFeaturedCountIsWithinLimit() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setFeatured(false);

        Product existProduct = mock(Product.class);
        Product updatedProduct = new Product();
        ProductDTO updatedProductDTO = new ProductDTO();

        when(productRepository.countByFeaturedTrue()).thenReturn(4);
        when(productRepository.findById(productDTO.getProductId())).thenReturn(Optional.of(existProduct));
        when(productRepository.save(existProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedProductDTO);

        // When
        ProductDTO result = productService.update(productDTO);

        // Then
        assertNotNull(result);
        assertEquals(updatedProductDTO, result);

        verify(productRepository).countByFeaturedTrue();
        verify(productRepository).findById(productDTO.getProductId());
        verify(productMapper).updateProductFromDTO(productDTO, existProduct);
        verify(productRepository).save(existProduct);
        verify(productMapper).toDTO(updatedProduct);
    }

    @Test
    void update_shouldThrowException_whenProductDoesNotExist() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setFeatured(false);

        when(productRepository.findById(productDTO.getProductId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.update(productDTO);
        });

        assertEquals("Did not find product with id - "  + productDTO.getProductId(), exception.getMessage());

        verify(productRepository).findById(productDTO.getProductId());
        verify(productMapper, never()).updateProductFromDTO(any(), any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void update_shouldThrowException_whenFeaturedProductsCountExceedsLimit() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setFeatured(true);

        when(productRepository.countByFeaturedTrue()).thenReturn(5);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.update(productDTO);
        });

        assertEquals("You can only have up to 5 featured products.", exception.getMessage());

        verify(productRepository).countByFeaturedTrue();
        verify(productRepository, never()).findById(anyInt());
        verify(productMapper, never()).updateProductFromDTO(any(), any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteById_shouldDeleteProduct_whenProductExists() {
        // Given
        int productId = 1;
        Product product = new Product();
        product.setProductId(productId);

        Category category1 = new Category();
        Category category2 = new Category();
        Set<Category> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);
        product.setCategories(categories);

        Color color1 = new Color();
        Color color2 = new Color();
        color1.setColorImage("image1.png");
        color2.setColorImage("image2.png");
        List<Color> colors = new ArrayList<>();
        colors.add(color1);
        colors.add(color2);
        product.setColors(colors);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        productService.deleteById(productId);

        // Then
        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);

        for (Category category : categories) {
            assertFalse(category.getProducts().contains(product));
        }

        assertTrue(product.getCategories().isEmpty());
    }

    @Test
    void deleteById_shouldThrowException_whenProductDoesNotExist() {
        // Given
        int productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteById(productId);
        });

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository).findById(productId);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void displayAll_shouldReturnFormattedProductDTOs_whenProductsExist() {
        // Given
        Product product1 = new Product();
        product1.setCreatedAt(LocalDateTime.of(2024, 6, 1, 10, 0, 0));
        product1.setLastModified(LocalDateTime.of(2024, 6, 2, 10, 0, 0));
        Product product2 = new Product();
        product2.setCreatedAt(LocalDateTime.of(2024, 6, 3, 11, 0, 0));
        product2.setLastModified(LocalDateTime.of(2024, 6, 4, 11, 0, 0));

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO1);
        productDTOList.add(productDTO2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toDTOs(productList)).thenReturn(productDTOList);

        // When
        List<ProductDTO> result = productService.displayAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

        assertEquals(product1.getCreatedAt().format(newFormatter), result.get(0).getCreatedAt());
        assertEquals(product1.getLastModified().format(newFormatter), result.get(0).getLastModified());
        assertEquals(product2.getCreatedAt().format(newFormatter), result.get(1).getCreatedAt());
        assertEquals(product2.getLastModified().format(newFormatter), result.get(1).getLastModified());

        verify(productRepository).findAll();
        verify(productMapper).toDTOs(productList);
    }

    @Test
    void displayAll_shouldReturnEmptyList_whenNoProductsExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toDTOs(productList)).thenReturn(productDTOList);

        // When
        List<ProductDTO> result = productService.displayAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productRepository).findAll();
        verify(productMapper).toDTOs(productList);
    }

    @Test
    void findFeaturedProducts_shouldReturnListOfFeaturedProductDTOs() {
        // Given
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        ProductDTO productDTO1 = new ProductDTO();
        ProductDTO productDTO2 = new ProductDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO1);
        productDTOList.add(productDTO2);

        when(productRepository.findAllByFeaturedTrue()).thenReturn(productList);
        when(productMapper.toDTOs(productList)).thenReturn(productDTOList);

        // When
        List<ProductDTO> result = productService.findFeaturedProducts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(productDTOList, result);

        verify(productRepository).findAllByFeaturedTrue();
        verify(productMapper).toDTOs(productList);
    }
}