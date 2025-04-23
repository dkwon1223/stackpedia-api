package com.dkwondev.stackpedia_v2_api.model.dto.technology;

import com.dkwondev.stackpedia_v2_api.model.dto.category.CategoryDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.category.CategorySimpleDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String description;
    private String slug;
    private String websiteUrl;
    private String githubUrl;
    private String documentationUrl;
    private String logoUrl;
    private Set<CategorySimpleDTO> categories;
    private LocalDateTime updatedAt;
}
