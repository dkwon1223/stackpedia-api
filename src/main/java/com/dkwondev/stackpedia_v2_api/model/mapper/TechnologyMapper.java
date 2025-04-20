package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.TechnologyDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {
    TechnologyDTO technologyToDTO(Technology technology);
    Technology toEntity(TechnologyDTO technologyDTO);
}
