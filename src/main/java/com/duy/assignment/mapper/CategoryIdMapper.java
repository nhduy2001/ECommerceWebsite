package com.duy.assignment.mapper;

import com.duy.assignment.entity.Category;
import com.duy.assignment.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategoryIdMapper {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryIdMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<Category> mapIdsToCategories(Set<Integer> categoryIds) {
        Set<Category> categories = new HashSet<>();
        for (Integer id : categoryIds) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
            categories.add(category);
        }
        return categories;
    }

    public Set<Integer> mapCategoriesToIds(Set<Category> categories) {
        Set<Integer> categoryIds = new HashSet<>();
        for (Category category : categories) {
            categoryIds.add(category.getCategoryId());
        }
        return categoryIds;
    }
}

