package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", uses = {CareerPathMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "careerPath.id", target = "careerPathId")
    CourseDTO toDto(Course course);

    @Mapping(source = "careerPathId", target = "careerPath")
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
