package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.LanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Language and its DTO LanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {ForumMapper.class, CourseMapper.class})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {

    @Mapping(source = "forum.id", target = "forumId")
    @Mapping(source = "course.id", target = "courseId")
    LanguageDTO toDto(Language language);

    @Mapping(source = "forumId", target = "forum")
    @Mapping(source = "courseId", target = "course")
    Language toEntity(LanguageDTO languageDTO);

    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
