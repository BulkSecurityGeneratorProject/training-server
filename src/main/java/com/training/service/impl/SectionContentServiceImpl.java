package com.training.service.impl;

import com.training.service.SectionContentService;
import com.training.domain.SectionContent;
import com.training.repository.SectionContentRepository;
import com.training.service.dto.SectionContentDTO;
import com.training.service.mapper.SectionContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SectionContent.
 */
@Service
@Transactional
public class SectionContentServiceImpl implements SectionContentService {

    private final Logger log = LoggerFactory.getLogger(SectionContentServiceImpl.class);

    private final SectionContentRepository sectionContentRepository;

    private final SectionContentMapper sectionContentMapper;

    public SectionContentServiceImpl(SectionContentRepository sectionContentRepository, SectionContentMapper sectionContentMapper) {
        this.sectionContentRepository = sectionContentRepository;
        this.sectionContentMapper = sectionContentMapper;
    }

    /**
     * Save a sectionContent.
     *
     * @param sectionContentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SectionContentDTO save(SectionContentDTO sectionContentDTO) {
        log.debug("Request to save SectionContent : {}", sectionContentDTO);
        SectionContent sectionContent = sectionContentMapper.toEntity(sectionContentDTO);
        sectionContent = sectionContentRepository.save(sectionContent);
        return sectionContentMapper.toDto(sectionContent);
    }

    /**
     * Get all the sectionContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectionContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SectionContents");
        return sectionContentRepository.findAll(pageable)
            .map(sectionContentMapper::toDto);
    }

    /**
     * Get one sectionContent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SectionContentDTO findOne(Long id) {
        log.debug("Request to get SectionContent : {}", id);
        SectionContent sectionContent = sectionContentRepository.findOne(id);
        return sectionContentMapper.toDto(sectionContent);
    }

    /**
     * Delete the sectionContent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SectionContent : {}", id);
        sectionContentRepository.delete(id);
    }
}
