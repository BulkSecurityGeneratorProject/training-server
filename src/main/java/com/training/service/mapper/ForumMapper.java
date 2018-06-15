package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.ForumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Forum and its DTO ForumDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface ForumMapper extends EntityMapper<ForumDTO, Forum> {

    @Mapping(source = "course.id", target = "courseId")
    ForumDTO toDto(Forum forum);

    @Mapping(source = "courseId", target = "course")
    Forum toEntity(ForumDTO forumDTO);

    default Forum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Forum forum = new Forum();
        forum.setId(id);
        return forum;
    }
}
