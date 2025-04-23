package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getBySlug(String slug);

    Category getById(Long id);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
