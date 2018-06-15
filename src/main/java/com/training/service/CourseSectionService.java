package com.training.service;

import com.training.service.dto.CourseSectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CourseSection.
 */
public interface CourseSectionService {

    /**
     * Save a courseSection.
     *
     * @param courseSectionDTO the entity to save
     * @return the persisted entity
     */
    CourseSectionDTO save(CourseSectionDTO courseSectionDTO);

    /**
     * Get all the courseSections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CourseSectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseSection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CourseSectionDTO findOne(Long id);

    /**
     * Delete the "id" courseSection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
