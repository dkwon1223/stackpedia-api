package com.dkwondev.stackpedia_v2_api.model.mapper;

import com.dkwondev.stackpedia_v2_api.model.dto.stack.StackDTO;
import com.dkwondev.stackpedia_v2_api.model.entity.Stack;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StackMapper {
    StackDTO stackToStackDTO(Stack stack);
    Stack stackDTOToEntity(StackDTO stackDTO);

    List<StackDTO> stacksToListDTOs(List<Stack> stacks);
}
