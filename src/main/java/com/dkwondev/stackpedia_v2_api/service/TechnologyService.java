package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TechnologyService {

    // BASIC CRUD

    List<Technology> getAllTechnologies();

    Technology getTechnologyById(Long id);

    Technology getTechnologyBySlug(String slug);

    Technology createTechnology(Technology technology);

    Technology updateTechnology(Long id, Technology technology);

    Technology patchTechnology(Long id, Map<String, Object> updates);

    void deleteTechnology(Long id);

    // RELATIONAL

    Technology addCategoryToTechnology(Long technologyId, Long categoryId);

    Technology removeCategoryFromTechnology(Long technologyId, Long categoryId);

    Set<Technology> getTechnologiesByCategoryId(Long categoryId);

    Set<Category> getCategoriesByTechnologyId(Long technologyId);
}
