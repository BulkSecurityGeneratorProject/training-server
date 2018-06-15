package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.TaughtCourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaughtCourse and its DTO TaughtCourseDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, UserMapper.class})
public interface TaughtCourseMapper extends EntityMapper<TaughtCourseDTO, TaughtCourse> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "user.id", target = "userId")
    TaughtCourseDTO toDto(TaughtCourse taughtCourse);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "userId", target = "user")
    TaughtCourse toEntity(TaughtCourseDTO taughtCourseDTO);

    default TaughtCourse fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaughtCourse taughtCourse = new TaughtCourse();
        taughtCourse.setId(id);
        return taughtCourse;
    }
}
