package com.training.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.training.service.SectionContentService;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.util.HeaderUtil;
import com.training.web.rest.util.PaginationUtil;
import com.training.service.dto.SectionContentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SectionContent.
 */
@RestController
@RequestMapping("/api")
public class SectionContentResource {

    private final Logger log = LoggerFactory.getLogger(SectionContentResource.class);

    private static final String ENTITY_NAME = "sectionContent";

    private final SectionContentService sectionContentService;

    public SectionContentResource(SectionContentService sectionContentService) {
        this.sectionContentService = sectionContentService;
    }

    /**
     * POST  /section-contents : Create a new sectionContent.
     *
     * @param sectionContentDTO the sectionContentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionContentDTO, or with status 400 (Bad Request) if the sectionContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/section-contents")
    @Timed
    public ResponseEntity<SectionContentDTO> createSectionContent(@RequestBody SectionContentDTO sectionContentDTO) throws URISyntaxException {
        log.debug("REST request to save SectionContent : {}", sectionContentDTO);
        if (sectionContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new sectionContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionContentDTO result = sectionContentService.save(sectionContentDTO);
        return ResponseEntity.created(new URI("/api/section-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /section-contents : Updates an existing sectionContent.
     *
     * @param sectionContentDTO the sectionContentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionContentDTO,
     * or with status 400 (Bad Request) if the sectionContentDTO is not valid,
     * or with status 500 (Internal Server Error) if the sectionContentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/section-contents")
    @Timed
    public ResponseEntity<SectionContentDTO> updateSectionContent(@RequestBody SectionContentDTO sectionContentDTO) throws URISyntaxException {
        log.debug("REST request to update SectionContent : {}", sectionContentDTO);
        if (sectionContentDTO.getId() == null) {
            return createSectionContent(sectionContentDTO);
        }
        SectionContentDTO result = sectionContentService.save(sectionContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /section-contents : get all the sectionContents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sectionContents in body
     */
    @GetMapping("/section-contents")
    @Timed
    public ResponseEntity<List<SectionContentDTO>> getAllSectionContents(Pageable pageable) {
        log.debug("REST request to get a page of SectionContents");
        Page<SectionContentDTO> page = sectionContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/section-contents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /section-contents/:id : get the "id" sectionContent.
     *
     * @param id the id of the sectionContentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionContentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/section-contents/{id}")
    @Timed
    public ResponseEntity<SectionContentDTO> getSectionContent(@PathVariable Long id) {
        log.debug("REST request to get SectionContent : {}", id);
        SectionContentDTO sectionContentDTO = sectionContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionContentDTO));
    }

    /**
     * DELETE  /section-contents/:id : delete the "id" sectionContent.
     *
     * @param id the id of the sectionContentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/section-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteSectionContent(@PathVariable Long id) {
        log.debug("REST request to delete SectionContent : {}", id);
        sectionContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
