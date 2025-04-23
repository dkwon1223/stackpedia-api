package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import com.dkwondev.stackpedia_v2_api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getBySlug(String slug) {
        Category category = categoryRepository.findCategoryBySlug(slug);
        if (category == null) {
            throw new EntityNotFoundException("Category with slug: " + slug + " not found");
        }
        return category;
    }

    @Override
    public Category getById(Long id) {
        return unwrap(categoryRepository.findById(id));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
            .map(existingCategory -> {
                existingCategory.setName(category.getName());
                existingCategory.setDescription(category.getDescription());
                existingCategory.setSlug(category.getSlug());
                return categoryRepository.save(existingCategory);
            })
            .orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " does not exist."));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public static Category unwrap(Optional<Category> category) {
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new EntityNotFoundException("Category not found.");
        }
    }
}
