package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.SectionContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SectionContent and its DTO SectionContentDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseSectionMapper.class})
public interface SectionContentMapper extends EntityMapper<SectionContentDTO, SectionContent> {

    @Mapping(source = "courseSection.id", target = "courseSectionId")
    SectionContentDTO toDto(SectionContent sectionContent);

    @Mapping(source = "courseSectionId", target = "courseSection")
    SectionContent toEntity(SectionContentDTO sectionContentDTO);

    default SectionContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SectionContent sectionContent = new SectionContent();
        sectionContent.setId(id);
        return sectionContent;
    }
}
