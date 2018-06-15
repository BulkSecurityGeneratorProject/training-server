package com.training.service.impl;

import com.training.service.ForumService;
import com.training.domain.Forum;
import com.training.repository.ForumRepository;
import com.training.service.dto.ForumDTO;
import com.training.service.mapper.ForumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Forum.
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService {

    private final Logger log = LoggerFactory.getLogger(ForumServiceImpl.class);

    private final ForumRepository forumRepository;

    private final ForumMapper forumMapper;

    public ForumServiceImpl(ForumRepository forumRepository, ForumMapper forumMapper) {
        this.forumRepository = forumRepository;
        this.forumMapper = forumMapper;
    }

    /**
     * Save a forum.
     *
     * @param forumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ForumDTO save(ForumDTO forumDTO) {
        log.debug("Request to save Forum : {}", forumDTO);
        Forum forum = forumMapper.toEntity(forumDTO);
        forum = forumRepository.save(forum);
        return forumMapper.toDto(forum);
    }

    /**
     * Get all the forums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ForumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Forums");
        return forumRepository.findAll(pageable)
            .map(forumMapper::toDto);
    }

    /**
     * Get one forum by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ForumDTO findOne(Long id) {
        log.debug("Request to get Forum : {}", id);
        Forum forum = forumRepository.findOne(id);
        return forumMapper.toDto(forum);
    }

    /**
     * Delete the forum by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Forum : {}", id);
        forumRepository.delete(id);
    }
}
