package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.ProblemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Problem and its DTO ProblemDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ForumMapper.class})
public interface ProblemMapper extends EntityMapper<ProblemDTO, Problem> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "forum.id", target = "forumId")
    ProblemDTO toDto(Problem problem);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "forumId", target = "forum")
    Problem toEntity(ProblemDTO problemDTO);

    default Problem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Problem problem = new Problem();
        problem.setId(id);
        return problem;
    }
}
