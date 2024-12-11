package com.enigma.library_management.controller;

import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.dto.request.CategoryRequest;
import com.enigma.library_management.dto.response.CategoryResponse;
import com.enigma.library_management.service.CategoryService;
import com.enigma.library_management.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.CATEGORY_API)
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_CATEGORY, categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_CATEGORY, null);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<CategoryResponse> categoryResponses = categoryService.getAll();

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_CATEGORY, categoryResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryRequest);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_CATEGORY, categoryResponse);
    }
}
