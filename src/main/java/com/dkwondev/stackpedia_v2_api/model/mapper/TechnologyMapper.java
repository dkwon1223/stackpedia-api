package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.TechnologyDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {
    TechnologyDTO technologyToDTO(Technology technology);
    List<TechnologyDTO> technologiesToDTOs(List<Technology> technologies);
    Technology toEntity(TechnologyDTO technologyDTO);
}
