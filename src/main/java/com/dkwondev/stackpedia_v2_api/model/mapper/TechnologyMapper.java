package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.technology.TechnologyDTO;
import com.dkwondev.stackpedia_v2_api.model.dto.technology.TechnologySimpleDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Technology;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {
    TechnologyDTO technologyToDTO(Technology technology);
    Technology toEntity(TechnologyDTO technologyDTO);

    List<TechnologyDTO> technologiesToListDTOs(List<Technology> technologies);
    Set<TechnologyDTO> technologiesToSetDTOs(Set<Technology> technologies);
    Set<TechnologySimpleDTO> technologiesToSimpleDTOs(Set<Technology> technologies);
}
