package com.dkwondev.stackpedia_v2_api.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySimpleDTO {
    private Long id;
    private String name;
}
