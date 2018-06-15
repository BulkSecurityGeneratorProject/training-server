package com.training.service.impl;

import com.training.service.CareerPathService;
import com.training.domain.CareerPath;
import com.training.repository.CareerPathRepository;
import com.training.service.dto.CareerPathDTO;
import com.training.service.mapper.CareerPathMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CareerPath.
 */
@Service
@Transactional
public class CareerPathServiceImpl implements CareerPathService {

    private final Logger log = LoggerFactory.getLogger(CareerPathServiceImpl.class);

    private final CareerPathRepository careerPathRepository;

    private final CareerPathMapper careerPathMapper;

    public CareerPathServiceImpl(CareerPathRepository careerPathRepository, CareerPathMapper careerPathMapper) {
        this.careerPathRepository = careerPathRepository;
        this.careerPathMapper = careerPathMapper;
    }

    /**
     * Save a careerPath.
     *
     * @param careerPathDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CareerPathDTO save(CareerPathDTO careerPathDTO) {
        log.debug("Request to save CareerPath : {}", careerPathDTO);
        CareerPath careerPath = careerPathMapper.toEntity(careerPathDTO);
        careerPath = careerPathRepository.save(careerPath);
        return careerPathMapper.toDto(careerPath);
    }

    /**
     * Get all the careerPaths.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CareerPathDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CareerPaths");
        return careerPathRepository.findAll(pageable)
            .map(careerPathMapper::toDto);
    }

    /**
     * Get one careerPath by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CareerPathDTO findOne(Long id) {
        log.debug("Request to get CareerPath : {}", id);
        CareerPath careerPath = careerPathRepository.findOne(id);
        return careerPathMapper.toDto(careerPath);
    }

    /**
     * Delete the careerPath by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CareerPath : {}", id);
        careerPathRepository.delete(id);
    }
}
