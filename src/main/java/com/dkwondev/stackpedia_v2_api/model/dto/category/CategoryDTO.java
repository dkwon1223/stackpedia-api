package com.dkwondev.stackpedia_v2_api.model.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String slug;
}
