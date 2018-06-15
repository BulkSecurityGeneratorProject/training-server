package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.CourseSectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CourseSection and its DTO CourseSectionDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CourseSectionMapper extends EntityMapper<CourseSectionDTO, CourseSection> {

    @Mapping(source = "course.id", target = "courseId")
    CourseSectionDTO toDto(CourseSection courseSection);

    @Mapping(source = "courseId", target = "course")
    CourseSection toEntity(CourseSectionDTO courseSectionDTO);

    default CourseSection fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        return courseSection;
    }
}
