package com.duy.assignment.service;

import com.duy.assignment.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO findCategoryById(int id);

    CategoryDTO add(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    void deleteById(int id);
}
