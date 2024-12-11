package com.enigma.library_management.service.impl;

import com.enigma.library_management.dto.request.CategoryRequest;
import com.enigma.library_management.dto.response.CategoryResponse;
import com.enigma.library_management.entity.Category;
import com.enigma.library_management.repository.CategoryRepository;
import com.enigma.library_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = getOne(id);

        return toCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();

        categoryRepository.saveAndFlush(category);

        return toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest categoryRequest) {
        Category category = getOne(id);

        category.setId(id);
        category.setName(categoryRequest.getName());

        categoryRepository.saveAndFlush(category);

        return toCategoryResponse(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = getOne(id);

        categoryRepository.delete(category);
    }

    @Override
    public Category getOne(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    private CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(String.valueOf(category.getId()));
        categoryResponse.setName(category.getName());

        return categoryResponse;
    }
}
