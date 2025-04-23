package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryBySlug(String slug);
}
