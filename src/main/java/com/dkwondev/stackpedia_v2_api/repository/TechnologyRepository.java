package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Technology findBySlug(String slug);
}
