package com.duy.assignment.service.impl;

import com.duy.assignment.dto.CategoryDTO;
import com.duy.assignment.entity.Category;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.CategoryMapper;
import com.duy.assignment.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplementTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImplement categoryService;

    @Test
    void findAllCategories() {
        //Given
        List<Category> categoryList = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        when(categoryMapper.toDTOs(categoryList)).thenReturn(categoryDTOS);

        //When
        List<CategoryDTO> result = categoryService.findAllCategories();

        //Then
        assertSame(result, categoryDTOS);
    }

    @Test
    void findCategoryById() {
        //Given
        int id = 1;
        Category category = new Category();
        Optional<Category> optionalCategory = Optional.of(category);
        when(categoryRepository.findById(1)).thenReturn(optionalCategory);

        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        //When
        CategoryDTO result = categoryService.findCategoryById(id);

        //Then
        assertSame(result, categoryDTO);
    }

    @Test
    void findCategoryById_shouldThrowException_whenCategoryNotFound() {
        // Given
        int id = 1;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.findCategoryById(id);
        });

        assertEquals("Did not find category with id - " + id, exception.getMessage());
    }

    @Test
    void add_shouldAddCategory_whenCategoryNameDoesNotExist() {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("New Category");
        Category category = new Category();
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(false);
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        // When
        CategoryDTO result = categoryService.add(categoryDTO);

        // Then
        assertSame(categoryDTO, result);
    }

    @Test
    void add_shouldThrowException_whenCategoryNameAlreadyExists() {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Existing Category");
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.add(categoryDTO);
        });

        assertEquals("Category name already exists", exception.getMessage());
    }

    @Test
    void update_shouldThrowException_whenCategoryNameAlreadyExists() {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1);
        categoryDTO.setCategoryName("Existing Category");
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.update(categoryDTO);
        });

        assertEquals("Category name already exists", exception.getMessage());
    }

    @Test
    void update_shouldThrowException_whenCategoryNotFound() {
        // Given
        int id = 1;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(id);
        categoryDTO.setCategoryName("New Category");
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(false);
        when(categoryRepository.findById(categoryDTO.getCategoryId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.update(categoryDTO);
        });

        assertEquals("Did not find category with id - " + id, exception.getMessage());
    }

    @Test
    void update_shouldUpdateCategory_whenCategoryNameDoesNotExistAndCategoryFound() {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1);
        categoryDTO.setCategoryName("New Category");

        Category category = new Category();
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(false);
        when(categoryRepository.findById(categoryDTO.getCategoryId())).thenReturn(Optional.of(category));
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        // When
        CategoryDTO result = categoryService.update(categoryDTO);

        // Then
        assertSame(categoryDTO, result);
    }

    @Test
    void deleteById_shouldThrowException_whenCategoryNotFound() {
        // Given
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteById(categoryId);
        });

        assertEquals("Category not found: " + categoryId, exception.getMessage());
    }

    @Test
    void deleteById_shouldDeleteCategory_whenCategoryFound() {
        // Given
        int categoryId = 1;
        Category category = new Category();
        category.setCategoryId(categoryId);

        Product product1 = new Product();
        Product product2 = new Product();
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        // Initialize categories set for each product
        product1.setCategories(new HashSet<>());
        product2.setCategories(new HashSet<>());


        category.setProducts(products);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        categoryService.deleteById(categoryId);

        // Then
        assertTrue(category.getProducts().isEmpty());

        for (Product product : products) {
            assertNotNull(product.getCategories()); // Ensure categories is not null
            assertFalse(product.getCategories().contains(category));
        }
    }
}