package com.training.service;

import com.training.service.dto.TaughtCourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TaughtCourse.
 */
public interface TaughtCourseService {

    /**
     * Save a taughtCourse.
     *
     * @param taughtCourseDTO the entity to save
     * @return the persisted entity
     */
    TaughtCourseDTO save(TaughtCourseDTO taughtCourseDTO);

    /**
     * Get all the taughtCourses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaughtCourseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" taughtCourse.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TaughtCourseDTO findOne(Long id);

    /**
     * Delete the "id" taughtCourse.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
