package com.training.service.impl;

import com.training.service.TaughtCourseService;
import com.training.domain.TaughtCourse;
import com.training.repository.TaughtCourseRepository;
import com.training.service.dto.TaughtCourseDTO;
import com.training.service.mapper.TaughtCourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TaughtCourse.
 */
@Service
@Transactional
public class TaughtCourseServiceImpl implements TaughtCourseService {

    private final Logger log = LoggerFactory.getLogger(TaughtCourseServiceImpl.class);

    private final TaughtCourseRepository taughtCourseRepository;

    private final TaughtCourseMapper taughtCourseMapper;

    public TaughtCourseServiceImpl(TaughtCourseRepository taughtCourseRepository, TaughtCourseMapper taughtCourseMapper) {
        this.taughtCourseRepository = taughtCourseRepository;
        this.taughtCourseMapper = taughtCourseMapper;
    }

    /**
     * Save a taughtCourse.
     *
     * @param taughtCourseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaughtCourseDTO save(TaughtCourseDTO taughtCourseDTO) {
        log.debug("Request to save TaughtCourse : {}", taughtCourseDTO);
        TaughtCourse taughtCourse = taughtCourseMapper.toEntity(taughtCourseDTO);
        taughtCourse = taughtCourseRepository.save(taughtCourse);
        return taughtCourseMapper.toDto(taughtCourse);
    }

    /**
     * Get all the taughtCourses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaughtCourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaughtCourses");
        return taughtCourseRepository.findAll(pageable)
            .map(taughtCourseMapper::toDto);
    }

    /**
     * Get one taughtCourse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaughtCourseDTO findOne(Long id) {
        log.debug("Request to get TaughtCourse : {}", id);
        TaughtCourse taughtCourse = taughtCourseRepository.findOne(id);
        return taughtCourseMapper.toDto(taughtCourse);
    }

    /**
     * Delete the taughtCourse by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaughtCourse : {}", id);
        taughtCourseRepository.delete(id);
    }
}
