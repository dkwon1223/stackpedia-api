package com.dkwondev.stackpedia_v2_api.model.dto.technology;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
