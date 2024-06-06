package com.duy.assignment.service.impl;

import com.duy.assignment.dto.CategoryDTO;
import com.duy.assignment.entity.Category;
import com.duy.assignment.entity.Product;
import com.duy.assignment.mapper.CategoryMapper;
import com.duy.assignment.repository.CategoryRepository;
import com.duy.assignment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final static String categoryNotFound = "Did not find category with id - ";

    @Autowired
    public CategoryServiceImplement(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return categoryMapper.toDTOs(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO findCategoryById(int id) {
        return categoryMapper.toDTO(categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(categoryNotFound + id)));
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        } else {
            Category category = categoryRepository.save(categoryMapper.toEntity(categoryDTO));
            return categoryMapper.toDTO(category);
        }
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        } else {
            Category category = categoryMapper.toEntity(categoryDTO);
            categoryRepository.findById(categoryDTO.getCategoryId())
                    .orElseThrow(() ->
                            new RuntimeException(categoryNotFound + categoryDTO.getCategoryId()));
            Category existCategory = category.toBuilder().build();
            categoryRepository.save(existCategory);
            return categoryMapper.toDTO(existCategory);
        }
    }

    @Override
    @Transactional
    public void deleteById(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        // Clear relationships with products
        for (Product product : category.getProducts()) {
            product.getCategories().remove(category);
        }

        category.getProducts().clear();

        // Delete the category
        categoryRepository.delete(category);
    }

}
