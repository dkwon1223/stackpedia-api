package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.category.CategoryDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import com.dkwondev.stackpedia_v2_api.model.mapper.CategoryMapper;
import com.dkwondev.stackpedia_v2_api.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryMapper.categoryToDTO(categoryService.createCategory(category)), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<List<CategoryDTO>> findAllCategories() {
        return new ResponseEntity<>(categoryMapper.categoriesToDTOs(categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<CategoryDTO> findCategoryBySlug(@RequestParam String slug) {
        return new ResponseEntity<>(categoryMapper.categoryToDTO(categoryService.getBySlug(slug)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryMapper.categoryToDTO(categoryService.getById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return new ResponseEntity<>(categoryMapper.categoryToDTO(categoryService.updateCategory(id, category)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
