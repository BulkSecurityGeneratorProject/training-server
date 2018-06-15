package com.training.service;

import com.training.service.dto.SectionContentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SectionContent.
 */
public interface SectionContentService {

    /**
     * Save a sectionContent.
     *
     * @param sectionContentDTO the entity to save
     * @return the persisted entity
     */
    SectionContentDTO save(SectionContentDTO sectionContentDTO);

    /**
     * Get all the sectionContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SectionContentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sectionContent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SectionContentDTO findOne(Long id);

    /**
     * Delete the "id" sectionContent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
