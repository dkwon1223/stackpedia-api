package com.dkwondev.stackpedia_v2_api.controller;

import com.dkwondev.stackpedia_v2_api.model.dto.category.CategorySimpleDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.technology.TechnologyDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import com.dkwondev.stackpedia_v2_api.model.mapper.CategoryMapper;
import com.dkwondev.stackpedia_v2_api.model.mapper.TechnologyMapper;
import com.dkwondev.stackpedia_v2_api.service.TechnologyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/technology")
@AllArgsConstructor
public class TechnologyController {

    private final TechnologyMapper technologyMapper;
    private final CategoryMapper categoryMapper;
    private final TechnologyService technologyService;

    @PostMapping
    public ResponseEntity<TechnologyDTO> createTechnology(@RequestBody Technology technology) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.createTechnology(technology)), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TechnologyDTO>> getAllTechnologies() {
        return new ResponseEntity<>(technologyMapper.technologiesToListDTOs(technologyService.getAllTechnologies()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDTO> getTechnologyById(@PathVariable Long id) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.getTechnologyById(id)), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<TechnologyDTO> getTechnologyBySlug(@RequestParam String slug) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.getTechnologyBySlug(slug)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnologyDTO> updateTechnology(@PathVariable Long id, @RequestBody Technology technology) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.updateTechnology(id, technology)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{technologyId}/category/{categoryId}")
    public ResponseEntity<TechnologyDTO> addCategoryToTechnology(@PathVariable Long technologyId, @PathVariable Long categoryId) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.addCategoryToTechnology(technologyId, categoryId)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{technologyId}/category/{categoryId}")
    public ResponseEntity<TechnologyDTO> removeCategoryFromTechnology(@PathVariable Long technologyId, @PathVariable Long categoryId) {
        return new ResponseEntity<>(technologyMapper.technologyToDTO(technologyService.removeCategoryFromTechnology(technologyId, categoryId)), HttpStatus.OK);
    }

    @GetMapping("/technologiesByCategory/{categoryId}")
    public ResponseEntity<Set<TechnologyDTO>> getAllTechnologiesByCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(technologyMapper.technologiesToSetDTOs(technologyService.getTechnologiesByCategoryId(categoryId)), HttpStatus.OK);
    }

    @GetMapping("/categoriesByTechnology/{technologyId}")
    public ResponseEntity<Set<CategorySimpleDTO>> getAllCategoriesByTechnology(@PathVariable Long technologyId) {
        return new ResponseEntity<>(categoryMapper.categoriesToSimpleDTOs(technologyService.getCategoriesByTechnologyId(technologyId)), HttpStatus.OK);
    }

}
