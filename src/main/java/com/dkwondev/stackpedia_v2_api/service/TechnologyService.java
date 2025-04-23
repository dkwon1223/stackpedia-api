package com.dkwondev.stackpedia_v2_api.service;

import com.dkwondev.stackpedia_v2_api.model.entity.Technology;

import java.util.List;

public interface TechnologyService {

    List<Technology> getAllTechnologies();

    Technology getTechnologyById(Long id);

    Technology getTechnologyBySlug(String slug);

    Technology createTechnology(Technology technology);

    Technology updateTechnology(Long id, Technology technology);

    void deleteTechnology(Long id);
}
