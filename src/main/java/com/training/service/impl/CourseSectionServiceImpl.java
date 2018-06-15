package com.training.service.impl;

import com.training.service.CourseSectionService;
import com.training.domain.CourseSection;
import com.training.repository.CourseSectionRepository;
import com.training.service.dto.CourseSectionDTO;
import com.training.service.mapper.CourseSectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CourseSection.
 */
@Service
@Transactional
public class CourseSectionServiceImpl implements CourseSectionService {

    private final Logger log = LoggerFactory.getLogger(CourseSectionServiceImpl.class);

    private final CourseSectionRepository courseSectionRepository;

    private final CourseSectionMapper courseSectionMapper;

    public CourseSectionServiceImpl(CourseSectionRepository courseSectionRepository, CourseSectionMapper courseSectionMapper) {
        this.courseSectionRepository = courseSectionRepository;
        this.courseSectionMapper = courseSectionMapper;
    }

    /**
     * Save a courseSection.
     *
     * @param courseSectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourseSectionDTO save(CourseSectionDTO courseSectionDTO) {
        log.debug("Request to save CourseSection : {}", courseSectionDTO);
        CourseSection courseSection = courseSectionMapper.toEntity(courseSectionDTO);
        courseSection = courseSectionRepository.save(courseSection);
        return courseSectionMapper.toDto(courseSection);
    }

    /**
     * Get all the courseSections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseSectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSections");
        return courseSectionRepository.findAll(pageable)
            .map(courseSectionMapper::toDto);
    }

    /**
     * Get one courseSection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CourseSectionDTO findOne(Long id) {
        log.debug("Request to get CourseSection : {}", id);
        CourseSection courseSection = courseSectionRepository.findOne(id);
        return courseSectionMapper.toDto(courseSection);
    }

    /**
     * Delete the courseSection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSection : {}", id);
        courseSectionRepository.delete(id);
    }
}
