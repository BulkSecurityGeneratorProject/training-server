package com.training.service;

import com.training.service.dto.CareerPathDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CareerPath.
 */
public interface CareerPathService {

    /**
     * Save a careerPath.
     *
     * @param careerPathDTO the entity to save
     * @return the persisted entity
     */
    CareerPathDTO save(CareerPathDTO careerPathDTO);

    /**
     * Get all the careerPaths.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CareerPathDTO> findAll(Pageable pageable);

    /**
     * Get the "id" careerPath.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CareerPathDTO findOne(Long id);

    /**
     * Delete the "id" careerPath.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
