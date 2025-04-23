package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.category.CategoryDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.category.CategorySimpleDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> categoriesToDTOs(List<Category> categories);
    Set<CategorySimpleDTO> categoriesToSimpleDTOs(Set<Category> categories);
}
