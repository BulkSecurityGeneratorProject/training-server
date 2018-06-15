package com.training.service;

import com.training.service.dto.ForumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Forum.
 */
public interface ForumService {

    /**
     * Save a forum.
     *
     * @param forumDTO the entity to save
     * @return the persisted entity
     */
    ForumDTO save(ForumDTO forumDTO);

    /**
     * Get all the forums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ForumDTO> findAll(Pageable pageable);

    /**
     * Get the "id" forum.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ForumDTO findOne(Long id);

    /**
     * Delete the "id" forum.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
