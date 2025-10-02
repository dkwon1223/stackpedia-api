package com.dkwondev.stackpedia_v2_api.model.dto.stack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StackDTO {
    private Long id;
    private String name;
    private String description;
    private String slug;
}
