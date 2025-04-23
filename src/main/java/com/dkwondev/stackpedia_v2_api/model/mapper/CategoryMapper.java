package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.category.CategoryDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
