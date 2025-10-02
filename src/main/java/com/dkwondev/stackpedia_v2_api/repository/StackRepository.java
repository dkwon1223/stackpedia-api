package com.dkwondev.stackpedia_v2_api.repository;

import com.dkwondev.stackpedia_v2_api.model.entity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackRepository extends JpaRepository<Stack, Long> {

    List<Stack> findAllByOrderByNameAsc();

    Stack findBySlug(String slug);

}
