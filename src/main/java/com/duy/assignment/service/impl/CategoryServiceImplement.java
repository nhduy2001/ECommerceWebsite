package com.duy.assignment.service.impl;

import com.duy.assignment.dto.CategoryDTO;
import com.duy.assignment.entity.Category;
import com.duy.assignment.mapper.CategoryMapper;
import com.duy.assignment.repository.CategoryRepository;
import com.duy.assignment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImplement(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDTOs(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO findCategoryById(int id) {
        return categoryMapper.toDTO(categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Did not find category with id - " + id)));
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        categoryRepository.findById(categoryDTO.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Did not find category with id - " + categoryDTO.getCategoryId()));
        Category existCategory = category.toBuilder().build();
        categoryRepository.save(existCategory);
        return categoryMapper.toDTO(existCategory);
    }

    @Override
    public void deleteById(int id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new RuntimeException("Did not find category with id - " + id);
        }

        categoryRepository.deleteById(id);
    }


}
