package com.enigma.library_management.service;

import com.enigma.library_management.dto.request.CategoryRequest;
import com.enigma.library_management.dto.response.CategoryResponse;
import com.enigma.library_management.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse getCategoryById(String id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(String id, CategoryRequest categoryRequest);
    void deleteCategory(String id);
    Category getOne(String id);
    List<CategoryResponse> getAll();
}
