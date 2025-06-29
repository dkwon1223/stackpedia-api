package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Technology findBySlug(String slug);

    List<Technology> findAllByOrderByNameAsc();
}
